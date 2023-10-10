package com.homehuddle.common.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import moe.tlaster.precompose.lifecycle.Lifecycle
import moe.tlaster.precompose.lifecycle.LifecycleObserver
import moe.tlaster.precompose.lifecycle.LocalLifecycleOwner
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import org.kodein.di.bindings.UnboundedScope

internal fun Navigator.navigate(routes: Routes, options: NavOptions? = null, vararg args: Any?) {
    this.navigate(
        routes.name + "?$Arg1=${args.getOrNull(0) ?: ""}" +
                "&$Arg2=${args.getOrNull(1) ?: ""}" +
                "&$Arg3=${args.getOrNull(2) ?: ""}",
        options
    )
}

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