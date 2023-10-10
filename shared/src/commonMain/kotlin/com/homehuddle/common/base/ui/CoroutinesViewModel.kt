package com.homehuddle.common.base.ui

import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

abstract class CoroutinesViewModel<S : State, I : Intent, E : SingleEvent> : ViewModel() {

    private val initialState: S by lazy { initialState() }

    private val _viewState: MutableStateFlow<S> by lazy { MutableStateFlow(initialState) }
    val viewState: StateFlow<S> by lazy { _viewState }

    private val _intent: MutableSharedFlow<I> = MutableSharedFlow()

    private val _singleEvent: Channel<E> = Channel()
    val singleEvent: Flow<E> = _singleEvent.receiveAsFlow()

    private val longRunningJobs: HashMap<I, Job> = hashMapOf()

    init {
        subscribeToIntents()
    }

    fun sendIntent(intent: I) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    protected fun triggerSingleEvent(singleEvent: E) {
        viewModelScope.launch { _singleEvent.send(singleEvent) }
    }

    protected abstract fun initialState(): S

    protected abstract suspend fun performSideEffects(intent: I, state: S): I?

    protected abstract fun reduce(intent: I, prevState: S): S

    protected open fun afterStateSet(intent: I, newState: S) {
        // NoOp
    }

    protected fun observeFlow(
        taskTriggerIntent: I,
        isUnique: Boolean = true,
        taskStartedByIntent: suspend () -> Unit
    ) {
        when {
            taskTriggerIntent in longRunningJobs.keys &&
                    !(longRunningJobs[taskTriggerIntent]?.isCompleted ?: true) &&
                    isUnique -> {
                return
            }
            !isUnique -> {
                if (taskTriggerIntent in longRunningJobs.keys) longRunningJobs[taskTriggerIntent]?.cancel()
            }
        }
        val task = viewModelScope.launch {
            taskStartedByIntent()
        }
        longRunningJobs[taskTriggerIntent] = task
    }

    private fun reduceInternal(intent: I, prevState: S) {
        val newState = reduce(intent, prevState)
        setState { newState }
        afterStateSet(intent, newState)
    }

    protected fun setState(newState: S.() -> S) {
        _viewState.value = viewState.value.newState()
    }

    private fun subscribeToIntents() {
        viewModelScope.launch {
            _intent.collect {
                reduceInternal(it, _viewState.value)
                launch {
                    performSideEffects(it, _viewState.value)?.let { newIntent ->
                        sendIntent(
                            newIntent
                        )
                    }
                }
            }
        }
    }
}