package com.homehuddle.common.design.theme

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.job

/*
* This is a helper function which helps solve errors with a requestFocus
* https://swooapp.atlassian.net/browse/UGC-897 like this
* it happen because requestFocus must happen after the composition
* SideEffect according to docs must solve this problem, but actually it don't and
* makes things even worse
* */
internal fun CoroutineScope.invokeOnCompletion(block: () -> Unit) {
	this.coroutineContext.job.invokeOnCompletion {
		try {
			block()
		} catch (e: IllegalStateException) {
			//ignore this
		}
	}
}