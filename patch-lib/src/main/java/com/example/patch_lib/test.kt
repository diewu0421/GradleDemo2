package com.example.patch_lib

/**
 * 浙江集商优选电子商务有限公司
 *
 * @author zenglw
 * @date 12/19/20 1:06 AM
 */
internal class test {
    init {
        val a = arrayOfNulls<Any>(2)
        println((a as Array<String?>).size)
    }
}