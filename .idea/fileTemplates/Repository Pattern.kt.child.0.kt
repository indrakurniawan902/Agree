#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

import com.devbase.data.source.DevRepository
import io.reactivex.Flowable

#end
#parse("File Header.java")
/**
 * ${NAME} Repository are ${NAME} Data Contract Abstraction
 * on Data Layer
 * @author: @telkomdev-abdul
 * @since: ${DAY} ${MONTH_NAME_SHORT} ${YEAR}
 */
interface ${NAME}Repository : DevRepository {
    
}