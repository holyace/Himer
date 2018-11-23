package com.chad.android.common.service;

import java.util.Map;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/12.
 */
public class ServiceManager {

    private static ServiceManager sInstance;

    private Map<Class, IService> sServiceMap;

    private ServiceManager() {

    }

    public static void init(ServiceConfig config) {

    }

    public static ServiceManager getInstance() {
        return sInstance;
    }

    public <T extends IService> T getService(Class<T> clazz) {
        T service = null;

        return service;
    }
}
