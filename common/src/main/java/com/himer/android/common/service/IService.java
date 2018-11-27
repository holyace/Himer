package com.himer.android.common.service;

import android.app.Application;

import java.util.Map;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/12.
 */
public interface IService {

    void init(Application app, Map<String, Object> params);

    void destory();
}
