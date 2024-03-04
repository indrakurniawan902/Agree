#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

import androidx.annotation.WorkerThread
import com.devbase.data.source.db.DbService
import com.devbase.data.source.web.WebService
import com.devbase.data.utilities.rx.operators.flowableApiError
import io.reactivex.Flowable

#end
#parse("File Header.java")
/**
 * ${NAME} Data Store are ${NAME} Repository Implementation
 * on Data Layer
 * @author: @telkomdev-abdul
 * @since: ${DAY} ${MONTH_NAME_SHORT} ${YEAR}
 */
@WorkerThread
class ${NAME}DataStore(
    override val dbService: DbService? = null,
    override val webService: WebService
) : ${NAME}Repository {
}