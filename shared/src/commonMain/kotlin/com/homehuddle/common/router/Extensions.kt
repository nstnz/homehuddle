package com.homehuddle.common.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import moe.tlaster.precompose.lifecycle.Lifecycle
import moe.tlaster.precompose.lifecycle.LifecycleObserver
import moe.tlaster.precompose.lifecycle.LocalLifecycleOwner
import org.kodein.di.bindings.UnboundedScope

@Composable
internal fun OnLifecycleEvent(scope: UnboundedScope, onEvent: (event: Lifecycle.State) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        lateinit var observer: LifecycleObserver
        observer = object : LifecycleObserver {
            override fun onStateChanged(state: Lifecycle.State) {
                eventHandler.value(state)

                if (state == Lifecycle.State.Destroyed) {
                    lifecycle.removeObserver(observer)
                    scope.close()
                }
            }
        }

        lifecycle.addObserver(observer)
        onDispose {
        }
    }
}