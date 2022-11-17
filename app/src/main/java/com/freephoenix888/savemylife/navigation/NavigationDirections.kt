package com.freephoenix888.savemylife.navigation

//sealed class NavigationDirections(val direction: String) {
//    sealed class Home() {
//        object Settings : NavigationDirections(direction = "settings")
//        sealed class NestedSettings() {
//            object PhoneNumbersSettings : NavigationDirections(direction = "phone_numbers_settings")
//            object MessageSettings : NavigationDirections(direction = "message_settings")
//            object LocationSharingSettings : NavigationDirections(direction = "location_sharing_settings")
//        }
//    }
//
//
//
//    sealed class MessageSettingsScreen() {
//        object MessageTemplateSettings : NavigationDirections(direction = "message_template_settings")
//        object MessageSendingIntervalSettings :
//            NavigationDirections(direction = "message_sending_interval_settings")
//
//        object MessageCommandsSettings : NavigationDirections(direction = "message_commands_settings")
//    }
//}


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