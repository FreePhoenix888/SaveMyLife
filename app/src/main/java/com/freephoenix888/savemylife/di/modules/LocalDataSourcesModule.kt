package com.freephoenix888.savemylife.di.modules

import com.freephoenix888.savemylife.data.sources.*
import com.freephoenix888.savemylife.data.sources.interfaces.*
//import com.freephoenix888.savemylife.data.sources.EmergencyMessageDataStoreLocalDataSource
//import com.freephoenix888.savemylife.data.sources.LocationSettingDataStoreLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourcesModule {
    @Binds
    abstract fun bindContactLocalStorage(emergencyContactRoomLocalDataSource: EmergencyContactRoomLocalDataSource): ContactLocalDataSource

    @Binds
    abstract fun bindEmergencyContactPhoneNumberLocalDataSource(emergencyContactPhoneNumberLocalDataSource: EmergencyContactPhoneNumberRoomLocalDataSource): EmergencyContactPhoneNumberLocalDataSource

    @Binds
    abstract fun bindEmergencyMessageLocalStorage(emergencyMessageDataStoreLocalDataSource: EmergencyMessageDataStoreLocalDataSource): EmergencyMessageLocalDataSource

    @Binds
    abstract fun bindLocationSettingLocalDataSource(locationSettingDataStoreLocalDataSource: LocationDataStoreLocalDataSource): LocationLocalDataSource

    @Binds
    abstract fun bindMainServiceSettingLocalDataSource(mainServiceSettingSharedPreferencesLocalDataSource: SaveMyLifeDataStoreLocalDataSource): SaveMyLifeLocalDataSource

}