package com.homehuddle.common.design.specific

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.homehuddle.common.base.domain.general.model.TripExpenseModel
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.domain.general.model.UserModel
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled

@Composable
internal fun TripExpensesCardComponent(
    trip: TripModel,
    tripPost: TripPostModel,
    showSocialHeader: Boolean,
    onClick: (TripPostModel) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .noEffectsClickable { onClick(tripPost) }
            .padding(bottom = AppTheme.indents.x2)
    ) {
        if (showSocialHeader) {
            TripPostUserSummaryComponent(
                trip = trip,
                tripPost = tripPost
            )
            SpacerComponent { x2 }
        }
        tripPost.expenses.forEach {
            ExpenseComponent(model = it, userModel = trip.user)
            SpacerComponent { x2 }
        }

        TripSocialComponent(
            12, 124, canSubscribe = false, canLike = true,
            paddingTop = AppTheme.indents.x1
        )
    }
}

@Composable
internal fun TripExpensesCardComponent(
    expenses: List<TripExpenseModel>,
    userModel: UserModel?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(bottom = AppTheme.indents.x2)
    ) {
        expenses.forEach {
            ExpenseComponent(model = it, userModel = userModel)
            SpacerComponent { x3 }
        }
    }
}

@Composable
internal fun ExpenseComponent(
    model: TripExpenseModel,
    userModel: UserModel?
) {
    Row(Modifier.fillMaxWidth()) {
        TripExpenseCategoryComponent(
            category = model.category,
            selected = true,
            size = AppTheme.indents.x6,
        )
        SpacerComponent { x2 }
        Column(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = model.description.takeIf { it.isNotEmpty() } ?: "New expense",
                    style = AppTheme.typography.body2,
                    color = AppTheme.colors.textDarkDefault(),
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                SpacerComponent { x1 }
                Text(
                    text = model.formattedSum,
                    style = AppTheme.typography.body2Bold,
                    color = AppTheme.colors.textDarkDefault(),
                )
            }
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = model.category.name + ", " + model.date.orEmpty(),
                    style = AppTheme.typography.body3,
                    color = AppTheme.colors.textDarkDisabled(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))
                userModel?.currency?.let {
                    Text(
                        text = model.getFormattedConvertedSum(userModel.currency),
                        style = AppTheme.typography.body3,
                        color = AppTheme.colors.textDarkDisabled(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}