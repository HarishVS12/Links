package com.linksofficial.links.di

import android.app.Application
import com.linksofficial.links.viewmodel.*
import org.koin.android.ext.koin.androidContext
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
        AddPostVM(androidContext() as Application,get())
    }

    viewModel {
        PostVisibilityVM()
    }

    viewModel {
        MyLinkVM(get())
    }

    viewModel{
        FeedVM()
    }
}