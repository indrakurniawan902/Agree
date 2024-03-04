package com.agree.ecosystem.agreepedia.di.modules.article

import com.agree.ecosystem.agreepedia.presentation.menu.detail.DetailViewModel
import com.agree.ecosystem.agreepedia.presentation.menu.home.AgreepediaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * ViewModel Module are modules that are responsible for injection of classes
 * related to ViewModels on Article Module
 * @author: @telkomdev-abdul
 * @since: 26 Oct 2022
 */
interface ViewModelModule {
    fun provideArticleViewModel(): Array<Module> {
        return arrayOf(
            provideArticle(),
            provideDetailArticle()
        )
    }

    fun provideArticle(): Module {
        return module {
            viewModel {
                AgreepediaViewModel(get())
            }
        }
    }

    fun provideDetailArticle(): Module {
        return module {
            viewModel {
                DetailViewModel(get())
            }
        }
    }
}
