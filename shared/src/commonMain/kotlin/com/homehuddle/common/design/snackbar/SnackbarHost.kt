package com.homehuddle.common.design.snackbar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.RecomposeScope
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.AccessibilityManager
import androidx.compose.ui.platform.LocalAccessibilityManager
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import kotlin.coroutines.resume
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * State of the [SnackbarHost], controls the queue and the current [Snackbar] being
 * shown inside the [SnackbarHost].
 *
 * This state usually lives as a part of a [ScaffoldState] and provided to the [SnackbarHost]
 * automatically, but can be decoupled from it and live separately when desired.
 */
@Stable
class SnackbarHostState {

	/**
	 * Only one [Snackbar] can be shown at a time.
	 * Since a suspending Mutex is a fair queue, this manages our message queue
	 * and we don't have to maintain one.
	 */
	private val mutex = Mutex()

	/**
	 * The current [SnackbarData] being shown by the [SnackbarHost], of `null` if none.
	 */
	var currentSnackbarData by mutableStateOf<SnackbarData?>(null)
		private set

	/**
	 * Shows or queues to be shown a [Snackbar] at the bottom of the [Scaffold] at
	 * which this state is attached and suspends until snackbar is disappeared.
	 *
	 * [SnackbarHostState] guarantees to show at most one snackbar at a time. If this function is
	 * called while another snackbar is already visible, it will be suspended until this snack
	 * bar is shown and subsequently addressed. If the caller is cancelled, the snackbar will be
	 * removed from display and/or the queue to be displayed.
	 *
	 * All of this allows for granular control over the snackbar queue from within:
	 *
	 * To change the Snackbar appearance, change it in 'snackbarHost' on the [Scaffold].
	 *
	 * @param message text to be shown in the Snackbar
	 * @param actionLabel optional action label to show as button in the Snackbar
	 * @param duration duration to control how long snackbar will be shown in [SnackbarHost], either
	 * [SnackbarDuration.Short], [SnackbarDuration.Long] or [SnackbarDuration.Indefinite]
	 *
	 * @return [SnackbarResult.ActionPerformed] if option action has been clicked or
	 * [SnackbarResult.Dismissed] if snackbar has been dismissed via timeout or by the user
	 */
	suspend fun showSnackbar(
		message: String,
		description: String? = null,
		isError: Boolean,
		actionLabel: String? = null,
	): SnackbarResult = mutex.withLock {
		val duration: SnackbarDuration = when {
			isError || !description.isNullOrEmpty() -> SnackbarDuration.Long
			else -> SnackbarDuration.Short
		}
		try {
			return suspendCancellableCoroutine { continuation ->
				currentSnackbarData = SnackbarDataImpl(
					message,
					description,
					actionLabel,
					duration,
					isError,
					continuation
				)
			}
		} finally {
			currentSnackbarData = null
		}
	}

	@Stable
	private class SnackbarDataImpl(
		override val title: String,
		override val description: String?,
		override val actionLabel: String?,
		override val duration: SnackbarDuration,
		override val isError: Boolean,
		private val continuation: CancellableContinuation<SnackbarResult>,
	) : SnackbarData {

		override fun performAction() {
			if (continuation.isActive) continuation.resume(SnackbarResult.ActionPerformed)
		}

		override fun dismiss() {
			if (continuation.isActive) continuation.resume(SnackbarResult.Dismissed)
		}
	}
}

/**
 * Host for [Snackbar]s to be used in [Scaffold] to properly show,
 * hide and dismiss items based on material specification and the [hostState].
 *
 * This component with default parameters comes build-in with [Scaffold], if you need to show a
 * default [Snackbar], use use [ScaffoldState.snackbarHostState] and
 * [SnackbarHostState.showSnackbar].
 *
 * If you want to customize appearance of the [Snackbar],
 * you can pass your own version as a child of the [SnackbarHost] to the [Scaffold]:
 *
 * @param hostState state of this component to read and show [Snackbar]s accordingly
 * @param modifier optional modifier for this component
 * @param snackbar the instance of the [Snackbar] to be shown at the appropriate time with
 * appearance based on the [SnackbarData] provided as a param
 */
@Composable
internal fun SnackbarHost(
	hostState: SnackbarHostState,
	modifier: Modifier = Modifier,
	snackbar: @Composable (SnackbarData) -> Unit = { Snackbar(it) },
) {
	val currentSnackbarData = hostState.currentSnackbarData
	val accessibilityManager = LocalAccessibilityManager.current
	LaunchedEffect(currentSnackbarData) {
		if (currentSnackbarData != null) {
			val duration = currentSnackbarData.duration.toMillis(
				currentSnackbarData.actionLabel != null,
				accessibilityManager
			)
			delay(duration)
			currentSnackbarData.dismiss()
		}
	}
	SlideInSlideOut(
		current = hostState.currentSnackbarData,
		modifier = modifier,
		content = snackbar
	)
}

/**
 * Interface to represent one particular [Snackbar] as a piece of the [SnackbarHostState]
 *
 * @property title text to be shown in the [Snackbar]
 * @property description optional description text in the [Snackbar]

}]]
 * @property actionLabel optional action label to show as button in the Snackbar
 * @property duration duration of the snackbar
 */
interface SnackbarData {

	val title: String
	val description: String?
	val actionLabel: String?
	val isError: Boolean
	val duration: SnackbarDuration

	/**
	 * Function to be called when Snackbar action has been performed to notify the listeners
	 */
	fun performAction()

	/**
	 * Function to be called when Snackbar is dismissed either by timeout or by the user
	 */
	fun dismiss()
}

/**
 * Possible results of the [SnackbarHostState.showSnackbar] call
 */
enum class SnackbarResult {

	/**
	 * [Snackbar] that is shown has been dismissed either by timeout of by user
	 */
	Dismissed,

	/**
	 * Action on the [Snackbar] has been clicked before the time out passed
	 */
	ActionPerformed,
}

/**
 * Possible durations of the [Snackbar] in [SnackbarHost]
 */
sealed class SnackbarDuration(val millis: kotlin.Long) {

	/**
	 * Show the Snackbar for a short period of time
	 */
	object Short : SnackbarDuration(SHORT_DELAY)

	/**
	 * Show the Snackbar for a long period of time
	 */
	object Long : SnackbarDuration(LONG_DELAY)

	/**
	 * Show the Snackbar indefinitely until explicitly dismissed or action is clicked
	 */
	object Indefinite : SnackbarDuration(kotlin.Long.MAX_VALUE)

	companion object {

		private const val SHORT_DELAY = 2000L
		private const val LONG_DELAY = 6000L
	}
}

internal fun SnackbarDuration.toMillis(
	hasAction: Boolean,
	accessibilityManager: AccessibilityManager?,
): Long {
	val original = this.millis
	if (accessibilityManager == null) {
		return original
	}
	return accessibilityManager.calculateRecommendedTimeoutMillis(
		original,
		containsIcons = true,
		containsText = true,
		containsControls = hasAction
	)
}

@Composable
private fun SlideInSlideOut(
	current: SnackbarData?,
	modifier: Modifier = Modifier,
	content: @Composable (SnackbarData) -> Unit,
) {
	val state = remember { SlideInSlideOutState<SnackbarData?>() }
	if (current != state.current) {
		state.current = current
		val keys = state.items.map { it.key }.toMutableList()
		if (!keys.contains(current)) {
			keys.add(current)
		}
		state.items.clear()
		keys.filterNotNull().mapTo(state.items) { key ->
			SlideInSlideOutAnimationItem(key) { children ->
				val isVisible = key == current
				val duration = if (isVisible) SnackbarSlideInMillis else SnackbarSlideOutMillis
				val delay = SnackbarSlideOutMillis + SnackbarInBetweenDelayMillis
				val animationDelay = if (isVisible && keys.filterNotNull().size != 1) delay else 0
				val transactionY = animatedTranslationY(
					animation = tween(
						easing = LinearEasing,
						delayMillis = animationDelay,
						durationMillis = duration
					),
					visible = isVisible,
					onAnimationFinish = {
						if (key != state.current) {
							state.items.removeAll { it.key == key }
							state.scope?.invalidate()
						}
					}
				)

				Box(
					Modifier
						.graphicsLayer(
							translationY = transactionY.value,
						)
						.pointerInput(Unit) {
							detectVerticalDragGestures(
								onDragStart = {
									key.dismiss()
								},
								onVerticalDrag = { _, _ -> }
							)
						}
						.semantics {
							liveRegion = LiveRegionMode.Polite
							dismiss { key.dismiss(); true }
						}
				) {
					children()
				}
			}
		}
	}
	Box(modifier) {
		state.scope = currentRecomposeScope
		state.items.forEach { (item, transition) ->
			key(item) {
				transition {
					requireNotNull(item)
					content(item)
				}
			}
		}
	}
}

private class SlideInSlideOutState<T> {

	var current: Any? = Any()
	var items = mutableListOf<SlideInSlideOutAnimationItem<T>>()
	var scope: RecomposeScope? = null
}

private data class SlideInSlideOutAnimationItem<T>(
	val key: T,
	val transition: SlideInSlideOutTransition,
)

private typealias SlideInSlideOutTransition = @Composable (content: @Composable () -> Unit) -> Unit

@Composable
private fun animatedTranslationY(
	animation: AnimationSpec<Float>,
	visible: Boolean,
	onAnimationFinish: () -> Unit,
): State<Float> {
	val translationY = remember { Animatable(if (!visible) 0f else SnackbarInitialOffset) }
	LaunchedEffect(visible) {
		translationY.animateTo(
			if (visible) 0f else SnackbarInitialOffset,
			animationSpec = animation
		)
		onAnimationFinish()
	}
	return translationY.asState()
}

private const val SnackbarInitialOffset = -400f
private const val SnackbarSlideInMillis = 200
private const val SnackbarSlideOutMillis = 120
private const val SnackbarInBetweenDelayMillis = 0

@Composable
internal fun rememberSnackBarHostState() = remember { SnackbarHostState() }