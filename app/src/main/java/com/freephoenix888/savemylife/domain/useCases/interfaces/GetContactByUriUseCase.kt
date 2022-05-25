package com.freephoenix888.savemylife.domain.useCases.interfaces

import android.net.Uri
import com.freephoenix888.savemylife.ui.states.ContactsItemUiState

interface GetContactByUriUseCase {
    operator fun invoke(uri: Uri): ContactsItemUiState
}