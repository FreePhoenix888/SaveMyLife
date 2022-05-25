package com.freephoenix888.savemylife.domain.useCases.interfaces

import com.freephoenix888.savemylife.ui.states.ContactsItemUiState

interface DeleteContactsUseCase {
    suspend operator fun invoke(vararg contacts: ContactsItemUiState): Int
}