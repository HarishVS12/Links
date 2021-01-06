package com.linksofficial.links.di

import com.linksofficial.links.viewmodel.EditProfileVM
import com.linksofficial.links.viewmodel.LinkActivityVM
import com.linksofficial.links.viewmodel.LoginVM
import com.linksofficial.links.viewmodel.MyAccountVM
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
}