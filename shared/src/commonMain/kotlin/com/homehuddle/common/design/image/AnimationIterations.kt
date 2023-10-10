package com.homehuddle.common.design.image

sealed interface AnimationIterations {

	data class FixedCount(val times: Int) : AnimationIterations

	object Forever : AnimationIterations
}