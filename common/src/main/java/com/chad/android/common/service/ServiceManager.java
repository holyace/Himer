package com.chad.android.common.service;

import java.util.HashMap;
import java.util.Map;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/12.
 */
public class ServiceManager {

    private static Map<Class, IService> sServiceMap;

    private ServiceManager() {

    }

    public static void init(ServiceConfig config) {
        sServiceMap = new HashMap<>();
    }

    public static void setService(Class clazz, IService service) {
        sServiceMap.put(clazz, service);
    }

    public static <T extends IService> T getService(Class<T> clazz) {
        IService service = sServiceMap.get(clazz);
        return (T) service;
    }
}
