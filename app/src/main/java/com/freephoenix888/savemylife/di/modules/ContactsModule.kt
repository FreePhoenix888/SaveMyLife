package com.freephoenix888.savemylife.di.modules

import com.freephoenix888.savemylife.domain.useCases.*
import com.freephoenix888.savemylife.domain.useCases.interfaces.*
import com.freephoenix888.savemylife.ui.viewModels.ContactsViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@ViewModelScoped
@InstallIn(ContactsViewModel::class)
abstract class ContactsModule {

    @Binds
    abstract fun bindDeleteContactsUseCase(useCase: DeleteLocalContactsUseCase): DeleteLocalContactsUseCase

    @Binds
    abstract fun bindGetContactByUriUseCase(useCase: GetLocalContactByUriUseCase): GetContactByUriUseCase

    @Binds
    abstract fun bindGetContactPhoneNumbersUseCase(useCase: GetContactPhoneNumbersByIdUseCase): GetContactPhoneNumbersUseCase

    @Binds
    abstract fun bindGetDangerModeStateUseCase(useCase: GetLocalDangerModeStateUseCase): GetDangerModeStateUseCase

    @Binds
    abstract fun bindSaveContactsUseCase(useCase: SaveLocalContactsUseCase): SaveContactsUseCase

    @Binds
    abstract fun bindSetDangerModeStateUseCase(useCase: SetLocalDangerModeStateUseCase): SetDangerModeStateUseCase

    @Binds
    abstract fun bindSwitchDangerModeStateUseCase(useCase: SwitchLocalDangerModeStateUseCase): SwitchDangerModeStateUseCase

    @Binds
    abstract fun bindSwitchLocationSharingStateUseCase(useCase: SwitchLocalLocationSharingStateUseCase): SwitchLocationSharingStateUseCase
}