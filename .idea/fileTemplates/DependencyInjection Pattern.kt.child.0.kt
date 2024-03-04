#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

#end
#parse("File Header.java")
/**
 * ViewModel Module are modules that are responsible for injection of classes
 * related to ViewModels on ${NAME} Module
 * @author: @telkomdev-abdul
 * @since: ${DAY} ${MONTH_NAME_SHORT} ${YEAR}
 */
interface ViewModelModule {
    fun provide${NAME}ViewModel(): Array<Module> {
        return arrayOf()
    }
}