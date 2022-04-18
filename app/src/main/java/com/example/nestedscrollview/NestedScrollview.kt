package com.example.nestedscrollview

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun VerticalNestedScrollView(
    modifier: Modifier=Modifier,
    state: NestedScrollViewState,
    orientation:Orientation,
    header:@Composable ()-> Unit={},
    content:@Composable ()-> Unit={},
){

}