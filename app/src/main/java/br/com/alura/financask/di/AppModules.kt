package br.com.alura.financask.di

import androidx.room.Room
import br.com.alura.financask.dao.TransacaoDAO
import br.com.alura.financask.database.AppDatabase
import br.com.alura.financask.repository.DatabaseDataSource
import br.com.alura.financask.repository.TransacaoRepository
import br.com.alura.financask.ui.viewmodel.TransacaoViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val NOME_DB = "app_database.db"

val dbModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
                get(),
                AppDatabase::class.java,
                NOME_DB
        ).build()
    }
}
val daoModule = module {
    single<TransacaoDAO> {
        get<AppDatabase>().transactionDAO
    }
}

val repositoryModule = module {
    single<TransacaoRepository> {
        DatabaseDataSource(get())
    }
}

val viewModelModule = module {
    viewModel<TransacaoViewModel> {
        TransacaoViewModel(get())
    }
}
val appModules = listOf(
        dbModule,
        daoModule,
        repositoryModule,
        viewModelModule

)