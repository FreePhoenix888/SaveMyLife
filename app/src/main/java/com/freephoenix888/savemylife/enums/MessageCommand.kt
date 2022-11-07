package com.freephoenix888.savemylife.enums

enum class MessageCommand {
    LOCATION
}

fun getMessageCommandDescription(messageCommand: MessageCommand): String {
    return when (messageCommand) {
        MessageCommand.LOCATION -> "Current location"
    }
}