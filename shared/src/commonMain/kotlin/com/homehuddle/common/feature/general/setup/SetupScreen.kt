package com.homehuddle.common.feature.general.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.homehuddle.common.base.domain.general.model.CountryModel
import com.homehuddle.common.base.domain.general.model.CurrencyModel
import com.homehuddle.common.design.button.PrimaryButtonComponent
import com.homehuddle.common.design.scaffold.GradientScaffold
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.specific.CountriesChipsComponent
import com.homehuddle.common.design.specific.SelectCountryBottomSheet
import com.homehuddle.common.design.specific.SelectCurrencyBottomSheet
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.background2
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkBorder
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled
import com.homehuddle.common.design.theme.textDarkSecondary
import com.homehuddle.common.design.theme.textLightDefault
import com.homehuddle.common.design.topbar.DefaultNavComponent

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun SetupScreen(
    state: SetupScreenState,
    bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    ),
    onSaveClick: () -> Unit = {},
    onCurrencyClick: () -> Unit = {},
    onChangeCurrency: (CurrencyModel) -> Unit = {},
    onSelectCountry: (CountryModel) -> Unit = {},
    onDeleteCountry: (CountryModel) -> Unit = {},
    onAddCountry: () -> Unit = {},
) {
    GradientScaffold(
        topBar = {
            DefaultNavComponent(
                showBackButton = false,
                elementsColor = AppTheme.colors.textLightDefault(),
                title = "Setup your profile"
            )
        },
        bottomSheetState = bottomSheetState,
        bottomSheet = {
            when (state.bottomSheet) {
                BottomSheetType.SelectCountry -> SelectCountryBottomSheet(
                    title = "Select country",
                    countries = state.userModel?.allCountries.orEmpty(),
                    onSelect = onSelectCountry
                )
                is BottomSheetType.SelectCurrency -> SelectCurrencyBottomSheet(
                    title = "Select currency",
                    currencies = state.bottomSheet.currencies,
                    selected = state.bottomSheet.selected,
                    onSelect = onChangeCurrency
                )
                null -> {}
            }
        }
    ) {
        Column(
            Modifier.fillMaxSize()
                .background(AppTheme.colors.background2(), AppTheme.shapes.x4_5_top)
                .padding(AppTheme.indents.x3)
        ) {
            Column(Modifier.fillMaxWidth().weight(1f)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Select your default currency",
                    style = AppTheme.typography.body3,
                    color = AppTheme.colors.textDarkDisabled()
                )
                SpacerComponent { x1_5 }
                Row(
                    Modifier
                        .noEffectsClickable { onCurrencyClick() }
                        .background(AppTheme.colors.textDarkBorder(), RoundedCornerShape(50))
                        .padding(horizontal = AppTheme.indents.x2, vertical = AppTheme.indents.x1)
                ) {
                    Text(
                        text = state.selectedCurrency?.code.orEmpty(),
                        style = AppTheme.typography.body3Bold,
                        color = AppTheme.colors.textDarkDefault()
                    )
                    SpacerComponent { x1 }
                    Text(
                        text = state.selectedCurrency?.name.orEmpty(),
                        style = AppTheme.typography.body3,
                        color = AppTheme.colors.textDarkSecondary()
                    )
                }
                SpacerComponent { x4 }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Countries you have been in",
                    style = AppTheme.typography.body3,
                    color = AppTheme.colors.textDarkDisabled()
                )
                SpacerComponent { x1_5 }
                CountriesChipsComponent(
                    state.selectedCountries, onAddCountry, onDeleteCountry
                )
            }
            PrimaryButtonComponent("Save", onSaveClick)
        }
    }
}