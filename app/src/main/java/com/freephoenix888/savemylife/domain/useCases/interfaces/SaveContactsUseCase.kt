package com.freephoenix888.savemylife.domain.useCases.interfaces

import com.freephoenix888.savemylife.ui.states.ContactsItemUiState

interface SaveContactsUseCase {
    suspend operator fun invoke(vararg contacts: ContactsItemUiState) : List<Long>
}