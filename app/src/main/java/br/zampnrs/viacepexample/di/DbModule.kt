package br.zampnrs.viacepexample.di

import androidx.room.Room
import br.zampnrs.viacepexample.data.AppDatabase
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "ContactDataBase"
        ).build()
    }

    single {
        get<AppDatabase>().contactDao()
    }
}