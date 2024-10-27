package com.plfdev.crypto_tracker.cryptos.presenter.coin_detail.chart

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

data class ChartStyle(
    val chartLineColor: Color,
    val unselectedColor: Color,
    val selectedColor: Color,
    //This is the grid lines horizontal and vertical
    val helperLinesThicknessPx: Float,

    val axisLinesThicknessPx: Float,
    val labelFontSize: TextUnit,
    //Spacing between the markers
    val minYLabelSpacing: Dp,
    val verticalPadding: Dp,
    val horizontalPadding: Dp,
    val xAxisLabelSpacing: Dp,
)
