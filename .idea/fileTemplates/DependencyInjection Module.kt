#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

import android.content.Context
import androidx.startup.Initializer
import org.koin.core.module.Module

#end
#parse("File Header.java")

class ${NAME}Module : Initializer<Array<Module>> {
    override fun create(context: Context): Array<Module> {
        return arrayOf()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}