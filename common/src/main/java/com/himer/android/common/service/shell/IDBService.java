package com.himer.android.common.service.shell;

import com.himer.android.common.service.IService;

import java.util.List;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/12.
 */
public interface IDBService extends IService {

    long insert(Object object);

    void insertAll(List<? extends Object> list);

    boolean delete(Object object);

    boolean update(Object object);

    boolean deleteById(Class<? extends Object> classType, long id);

    boolean deleteAll(Class<? extends Object> classType);

    <T> T queryById(Class<T> classType, long id);

    <T> List<T> query(Class<T> classType, String where, String... selectionArg);

    <T> List<T> queryAll(Class<T> classType);

}
