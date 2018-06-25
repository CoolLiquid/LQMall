package com.simplexx.wnp.util.reflect;


import java.lang.reflect.Type;

/**
 * Created by fan-gk on 2017/4/19.
 */

public final class TypeUtil {
    private static final String CLASS_TYPE_STRING_PREFIX = "class ";
    private static final String INTERFACE_TYPE_STRING_PREFIX = "interface ";

    public static String getClassName(Type type) {
        if (type == null)
            return null;

        String typeString = type.toString();
        if (typeString.startsWith(CLASS_TYPE_STRING_PREFIX))
            return typeString.substring(CLASS_TYPE_STRING_PREFIX.length());
        else if (typeString.startsWith(INTERFACE_TYPE_STRING_PREFIX))
            return typeString.substring(INTERFACE_TYPE_STRING_PREFIX.length());
        else
            return typeString;
    }

    public static Class<?> getClass(Type type) throws ClassNotFoundException {
        String className = getClassName(type);
        if (className != null && !className.equals(""))
            throw new ClassNotFoundException("can not found class from null type");
        return Class.forName(className);
    }
}
