package com.freephoenix888.savemylife.di.modules

import com.freephoenix888.savemylife.data.sources.LocationDataStoreLocalDataSource
import com.freephoenix888.savemylife.data.sources.MessageDataStoreLocalDataSource
import com.freephoenix888.savemylife.data.sources.PhoneNumberRoomLocalDataSource
import com.freephoenix888.savemylife.data.sources.SaveMyLifeDataStoreLocalDataSource
import com.freephoenix888.savemylife.data.sources.interfaces.LocationLocalDataSource
import com.freephoenix888.savemylife.data.sources.interfaces.MessageLocalDataSource
import com.freephoenix888.savemylife.data.sources.interfaces.PhoneNumberLocalDataSource
import com.freephoenix888.savemylife.data.sources.interfaces.SaveMyLifeLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {
    @Binds
    abstract fun bindPhoneNumberLocalDataSource(emergencyPhoneNumberLocalDataSource: PhoneNumberRoomLocalDataSource): PhoneNumberLocalDataSource

    @Binds
    abstract fun bindMessageLocalStorage(messageDataStoreLocalDataSource: MessageDataStoreLocalDataSource): MessageLocalDataSource

    @Binds
    abstract fun bindLocationSettingLocalDataSource(locationSettingDataStoreLocalDataSource: LocationDataStoreLocalDataSource): LocationLocalDataSource

    @Binds
    abstract fun bindMainServiceSettingLocalDataSource(mainServiceSettingSharedPreferencesLocalDataSource: SaveMyLifeDataStoreLocalDataSource): SaveMyLifeLocalDataSource

}