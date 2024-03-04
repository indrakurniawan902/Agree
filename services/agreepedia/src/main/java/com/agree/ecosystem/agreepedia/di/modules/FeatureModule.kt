package com.agree.ecosystem.agreepedia.di.modules

import com.agree.ecosystem.agreepedia.di.modules.article.ArticleModule
import org.koin.core.module.Module

interface FeatureModule :
    ArticleModule {
    fun provideFeatureModule(): Array<Module> {
        return arrayOf(
            *provideArticleModule()
        )
    }
}
