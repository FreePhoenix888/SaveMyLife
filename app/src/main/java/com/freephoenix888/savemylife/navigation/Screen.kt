package com.freephoenix888.savemylife.navigation

sealed class Screen(val route: String) {
    object Home : Screen(route = "home_screen")
    object Settings : Screen(route = "settings")
    object PhoneNumbersSettings : Screen(route = "phone_numbers_settings")
    object MessageSettings : Screen(route = "message_settings")
    object MessageTemplateSettings : Screen(route = "message_template_settings")
    object MessageSendingIntervalSettings : Screen(route = "message_sending_interval_settings")
    object MessageCommandsSettings : Screen(route = "message_commands_settings")
    object LocationSharingSettings : Screen(route = "location_sharing_settings")
}