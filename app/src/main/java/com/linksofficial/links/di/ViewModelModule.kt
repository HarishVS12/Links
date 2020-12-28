package com.linksofficial.links.di

import com.linksofficial.links.viewmodel.OnboardingVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        OnboardingVM(get())
    }
}