#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

#end
#parse("File Header.java")
/**
 * ${NAME} Module are modules that are responsible for injection of classes
 * related to Services, Repository and UseCase
 * @author: @telkomdev-abdul
 * @since: ${DAY} ${MONTH_NAME_SHORT} ${YEAR}
 */
interface ${NAME}Module: ViewModelModule {
    fun provide${NAME}Module(): Array<Module> {
        return arrayOf(
            provide${NAME}Service(),
            provide${NAME}Repository(),
            provide${NAME}UseCase(),
            *provide${NAME}ViewModel()
        )
    }
    
    fun provide${NAME}Service(): Module {
        return module {
        }
    }
    
    fun provide${NAME}Repository(): Module {
        return module {
        }
    }
    
    fun provide${NAME}UseCase(): Module {
        return module {
        }
    }
}