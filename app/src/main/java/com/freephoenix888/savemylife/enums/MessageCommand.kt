package com.freephoenix888.savemylife.enums

enum class MessageCommand {
    LOCATION
}

val messageCommandDescriptionMap = mapOf(
    MessageCommand.LOCATION to "Current location",
)

val messageCommandsAndDescriptionsString = messageCommandDescriptionMap.entries.joinToString { entry: Map.Entry<MessageCommand, String> ->
    "/${entry.key.name.lowercase()} - ${entry.value}"
}

fun getMessageCommandDescription(messageCommand: MessageCommand): String {
    return when (messageCommand) {
        MessageCommand.LOCATION -> "Current location"
    }
}