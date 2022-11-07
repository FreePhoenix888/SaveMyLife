package com.freephoenix888.savemylife.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class MessagePosition {
    LEFT,
    RIGHT
}

data class Message (
    val body: String,
    val position: MessagePosition,
    val time: String
)

@Composable
fun cardShape(message: Message): Shape {
    val roundedCorners = RoundedCornerShape(16.dp)
    return when (message.position) {
        MessagePosition.RIGHT -> roundedCorners.copy(bottomEnd = CornerSize(0))
        else -> roundedCorners.copy(bottomStart = CornerSize(0))
    }
}

@Composable
fun MessageCard(message: Message) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = when(message.position) {
            MessagePosition.RIGHT -> Alignment.End
            else -> Alignment.Start
        },
    ) {
        Card(
            modifier = Modifier.widthIn(max = 340.dp),
            shape = cardShape(message),
            colors = CardDefaults.cardColors(
                containerColor = when(message.position) {
                    MessagePosition.RIGHT -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.secondary
                }
            ),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = message.body,
                color = when (message.position){
                    MessagePosition.RIGHT -> MaterialTheme.colorScheme.onPrimary
                    else -> MaterialTheme.colorScheme.onSecondary
                },
            )
        }
        Text( // 4
            text = message.time,
            fontSize = 12.sp,
        )
    }
}