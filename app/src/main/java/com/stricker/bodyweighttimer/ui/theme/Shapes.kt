package com.stricker.bodyweighttimer.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp


object Shapes {

    val ShapeSmall = RoundedCornerShape(percent = 100)

    object Button {

        object Segmented {

            val Left =
                RoundedCornerShape(
                    topStartPercent = 100,
                    topEndPercent = 0,
                    bottomEndPercent = 0,
                    bottomStartPercent = 100,
                )

            val Right =
                RoundedCornerShape(
                    topStartPercent = 0,
                    topEndPercent = 100,
                    bottomEndPercent = 100,
                    bottomStartPercent = 0,
                )
        }

        object Toggle {
            object Segmented {

                val Right =
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 12.dp,
                        bottomEnd = 12.dp,
                        bottomStart = 0.dp
                    )

                val Left =
                    RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 0.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 12.dp
                    )

            }

        }
    }
}

