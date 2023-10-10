package com.homehuddle.common.design.input.internal

sealed class TextInputState {
	object Default : TextInputState()
	data class Error(val error: String?) : TextInputState()
}