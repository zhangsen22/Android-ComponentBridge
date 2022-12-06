package com.zs.mpaas.startup.compiler.utils;

/**
 * ******************************
 * 项目名称:MyAnnotation
 *
 * @Author zhangsen
 * 邮箱:zhangsen839705693@163.com
 * 创建时间:2021    5:51 下午
 * 说明:
 * ******************************
 */
public class StringUtils {

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}
