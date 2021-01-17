package com.linksofficial.links.di

import com.linksofficial.links.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        LinkActivityVM(get())
    }
    viewModel {
        LoginVM(get())
    }
    viewModel {
        EditProfileVM(get())
    }
    viewModel {
        MyAccountVM(get())
    }
    viewModel {
        SelectTagVM()
    }
}