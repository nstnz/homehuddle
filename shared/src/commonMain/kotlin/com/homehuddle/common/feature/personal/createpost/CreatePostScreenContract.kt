package com.homehuddle.common.feature.personal.createpost

import androidx.compose.ui.text.input.TextFieldValue
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.ui.Intent
import com.homehuddle.common.base.ui.SingleEvent
import com.homehuddle.common.base.ui.State

internal data class CreatePostScreenState(
    val name: TextFieldValue = TextFieldValue(""),
    val description: TextFieldValue = TextFieldValue(""),
    val dateStart: String? = null,
    val timestampStart: Long? = null,
    val dateEnd: String? = null,
    val timestampEnd: Long? = null,
    val fromDateSelected: Boolean? = true,
    val trip: TripModel? = null
) : State

internal sealed interface CreatePostScreenIntent : Intent {
    data class Update(val model: TripPostModel?) : CreatePostScreenIntent
    data class OnChangeName(val text: TextFieldValue) : CreatePostScreenIntent
    data class OnChangeDescription(val text: TextFieldValue) : CreatePostScreenIntent
    data class OnFromDateSelected(val date: Long?) : CreatePostScreenIntent
    data class OnToDateSelected(val date: Long?) : CreatePostScreenIntent
    object OnSaveClick : CreatePostScreenIntent
    object GoBack : CreatePostScreenIntent
    object OnResume : CreatePostScreenIntent
}

internal sealed interface CreatePostScreenSingleEvent : SingleEvent {

    object ShowError : CreatePostScreenSingleEvent
}

internal enum class ScreenMode {
    Edit,
    Post,
    Expenses,
    Points
}