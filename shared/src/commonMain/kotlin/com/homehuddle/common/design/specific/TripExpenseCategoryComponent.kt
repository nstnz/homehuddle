package com.homehuddle.common.design.specific

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Attractions
import androidx.compose.material.icons.rounded.CardGiftcard
import androidx.compose.material.icons.rounded.DirectionsBoat
import androidx.compose.material.icons.rounded.DirectionsBus
import androidx.compose.material.icons.rounded.DriveEta
import androidx.compose.material.icons.rounded.Fastfood
import androidx.compose.material.icons.rounded.Flight
import androidx.compose.material.icons.rounded.Hotel
import androidx.compose.material.icons.rounded.Museum
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.rounded.Train
import androidx.compose.material.icons.rounded.Tram
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import com.homehuddle.common.base.domain.general.model.TripExpenseCategory
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkDisabled
import com.homehuddle.common.design.theme.textLightDefault

@Composable
internal fun TripExpenseCategoryComponent(
    category: TripExpenseCategory,
    selected: Boolean,
    size: Dp,
    onClick: (TripExpenseCategory) -> Unit = {}
) {
    val color = if (selected) {
        when (category) {
            TripExpenseCategory.Plane -> Color(0xff8D55E9)
            TripExpenseCategory.Train -> Color(0xffEC6C5A)
            TripExpenseCategory.Bus -> Color(0xff6E8EFF)
            TripExpenseCategory.Car -> Color(0xff4CC082)
            TripExpenseCategory.Boat -> Color(0xffCDDC4C)
            TripExpenseCategory.Hotel -> Color(0xff4AAF57)
            TripExpenseCategory.Museum -> Color(0xffE350F0)
            TripExpenseCategory.Other -> Color(0xff495ECA)
            TripExpenseCategory.Entertaining -> Color(0xff513174)
            TripExpenseCategory.Grocery -> Color(0xffFFA45A)
            TripExpenseCategory.Shop -> Color(0xffFFD600)
            TripExpenseCategory.Fastfood -> Color(0xffFF981F)
            TripExpenseCategory.Restaurant -> Color(0xff2F68D7)
            TripExpenseCategory.Souvenir -> Color(0xff9E8F4D)
            TripExpenseCategory.Tram -> Color(0xff50555C)
        }
    } else {
        AppTheme.colors.textDarkDisabled().copy(alpha = 0.5f)
    }

    val icon = when (category) {
        TripExpenseCategory.Plane -> Icons.Rounded.Flight
        TripExpenseCategory.Train -> Icons.Rounded.Train
        TripExpenseCategory.Bus -> Icons.Rounded.DirectionsBus
        TripExpenseCategory.Tram -> Icons.Rounded.Tram
        TripExpenseCategory.Car -> Icons.Rounded.DriveEta
        TripExpenseCategory.Hotel -> Icons.Rounded.Hotel
        TripExpenseCategory.Grocery -> Icons.Rounded.ShoppingCart
        TripExpenseCategory.Museum -> Icons.Rounded.Museum
        TripExpenseCategory.Other -> Icons.Rounded.Payments
        TripExpenseCategory.Entertaining -> Icons.Rounded.Attractions
        TripExpenseCategory.Shop -> Icons.Rounded.ShoppingBag
        TripExpenseCategory.Restaurant -> Icons.Rounded.Restaurant
        TripExpenseCategory.Souvenir -> Icons.Rounded.CardGiftcard
        TripExpenseCategory.Boat -> Icons.Rounded.DirectionsBoat
        TripExpenseCategory.Fastfood -> Icons.Rounded.Fastfood
    }

    Box(
        Modifier
            .size(size)
            .noEffectsClickable { onClick(category) }
            .background(color, RoundedCornerShape(50)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(AppTheme.colors.textLightDefault()),
            modifier = Modifier.size(size * 0.65f)
        )
    }
}