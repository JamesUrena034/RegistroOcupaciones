package com.example.registroocupaciones.di

import android.content.Context
import androidx.room.Room
import com.example.registroocupaciones.data.database.OcupacionDb
import com.example.registroocupaciones.data.local.dao.EmpleadoDao
import com.example.registroocupaciones.data.ocupaciones.local.dao.HoraExtraDao
import com.example.registroocupaciones.data.repository.EmpleadoRepositoryImpl
import com.example.registroocupaciones.data.ocupaciones.local.dao.OcupacionDao
import com.example.registroocupaciones.data.ocupaciones.repository.HoraExtraRepositoryImpl
import com.example.registroocupaciones.data.ocupaciones.repository.OcupacionRepositoryImpl
import com.example.registroocupaciones.domain.empleadoos.repository.EmpleadoRepository
import com.example.registroocupaciones.domain.horaextra.repository.HoraExtraRepository
import com.example.registroocupaciones.domain.ocupaciones.repository.OcupacionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideOcupacionDb(@ApplicationContext appContext: Context): OcupacionDb.OcupacionDB {
        return Room.databaseBuilder(
            appContext,
            OcupacionDb.OcupacionDB::class.java,
            "Ocupacion.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideOcupacionDao(ocupacionDb: OcupacionDb.OcupacionDB): OcupacionDao {
        return ocupacionDb.ocupacionesDao()
    }

    @Provides
    @Singleton
    fun provideOcupacionRepository(ocupacionDao: OcupacionDao): OcupacionRepository {
        return OcupacionRepositoryImpl(ocupacionDao)
    }

    @Provides
    @Singleton
    fun provideEmpleadoDao(ocupacionDb: OcupacionDb.OcupacionDB): EmpleadoDao {
        return ocupacionDb.empleadoDao()
    }

    @Provides
    @Singleton
    fun provideEmpleadoRepository(empleadoDao: EmpleadoDao): EmpleadoRepository {
        return EmpleadoRepositoryImpl(empleadoDao)
    }

    @Provides
    @Singleton
    fun provideHoraExtraDao(ocupacionDb: OcupacionDb.OcupacionDB): HoraExtraDao {
        return ocupacionDb.horaExtraDao()
    }

    @Provides
    @Singleton
    fun provideHoraExtraRepository(horaExtraDao: HoraExtraDao): HoraExtraRepository {
        return HoraExtraRepositoryImpl(horaExtraDao)
    }
}