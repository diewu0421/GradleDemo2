package com.example.patch_lib

import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * 浙江集商优选电子商务有限公司
 * @author zenglw
 * @date   12/18/20 8:14 PM
 */
class ShareReflectHelper {

    companion object {
        fun findMethod(obj: Any, methodName: String, vararg parameters: Class<*>) : Method {
            var cls = obj.javaClass
            while (true) {
                runCatching {
                    return cls.getDeclaredMethod(methodName, *parameters).apply { isAccessible = true }
                }.onFailure {
                    cls = cls.superclass as Class<Any>
                }
            }
        }

        fun findField(obj: Any, fieldName: String): Field {
            var cls = obj.javaClass
            while (true) {
                runCatching {
                    return cls.getDeclaredField(fieldName).apply { isAccessible = true }
                }.onFailure {
                    cls = cls.superclass as Class<Any>
                }
            }
        }
    }
}