package com.freephoenix888.savemylife.navigation

/*sealed class NavigationDirections() {
    val name = this::class.simpleName
    sealed class HomeRoute() : NavigationDirections() {
        object HomeDestination : HomeRoute()
        sealed class Settings() : HomeRoute() {
            object PhoneNumbersSettings : Settings()
            sealed class MessageSettings : Settings() {
                object MessageSettings
            }
            object LocationSharingSettings : Settings()
        }
    }



    sealed class MessageSettingsScreen() {
        object MessageTemplateSettings : NavigationDirections(direction = "message_template_settings")
        object MessageSendingIntervalSettings :
            NavigationDirections(direction = "message_sending_interval_settings")

        object MessageCommandsSettings : NavigationDirections(direction = "message_commands_settings")
    }
}*/





enum class NavigationDestination{
    Home,
    Settings,
    PhoneNumbersSettings,
    MessageSettings,
    MessageTemplateSettings,
    MessageSendingIntervalSettings,
    MessageCommandsSettings,
    LocationSharingSettings,
    DangerModeActivationConfirmation
}

/*
enum class NavigationRoute(val destinations: List<NavigationDestination>) {
    Home(destinations = listOf(NavigationDestination.Home)),
    Settings(destinations = listOf(NavigationDestination.PhoneNumbersSettings, NavigationDestination.MessageSettings, NavigationDestination.LocationSharingSettings)),
    MessageSettings(destinations = listOf(NavigationDestination.MessageTemplateSettings, NavigationDestination.MessageSendingIntervalSettings, NavigationDestination.MessageCommandsSettings))
}
*/

/*
enum class Route {
    ;
    enum class Home{
        HomeRoute,
        HomeRoot;
        enum class Settings {
            SettingsRoute,
            SettingsRoot,
            PhoneNumbersSettings,
            LocationSharingSettings;
            enum class MessageSettings{
                MessageSettingsRoute,
                MessageSettingsRoot,
                MessageTemplateSettings,
                MessageSendingIntervalSettings,
                MessageCommandsSettings
            }
        }
    }
}
*/

//object Route {
//    const val Root = "Root"
//    object NestedRoot {
//        const val Home = "Home"
//        object NestedHome {
//            const val Settings = "Settings"
//            object NestedSettings {
//                const val PhoneNumbersSettings = "PhoneNumbersSettings"
//                const val MessageSettings = "MessageSettings"
//                object NestedMessageSettings {
//                    const val MessageTemplateSettings = "MessageTemplateSettings"
//                    const val MessageSendingIntervalSettings = "MessageSendingIntervalSettings"
//                    const val MessageCommandsSettings = "MessageCommandsSettings"
//                }
//                const val LocationSharingSettings = "LocationSharingSettings"
//            }
//        }
//    }
//}