package com.homehuddle.common.base.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.StateFlow
import moe.tlaster.precompose.flow.flowWithLifecycle
import moe.tlaster.precompose.lifecycle.LocalLifecycleOwner

@Composable
internal fun <T> StateFlow<T>.collectAsStateLifecycleAware(): State<T> {
    val lifecycleOwner = LocalLifecycleOwner.current

    val lifecycleAwareFlow = remember(this, lifecycleOwner) {
        flowWithLifecycle(lifecycleOwner.lifecycle)
    }

    return lifecycleAwareFlow.collectAsState(value)
}