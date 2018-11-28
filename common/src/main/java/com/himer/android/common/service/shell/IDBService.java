package com.himer.android.common.service.shell;

import com.himer.android.common.service.IService;

import java.util.List;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/12.
 */
public interface IDBService extends IService {

    <T> long insert(T object);

    <T> void insertAll(List<T> list);

    <T> long insertOrUpdate(T object);

    <T> boolean delete(T object);

    <T> boolean update(T object);

    <T> boolean deleteById(Class<T> classType, long id);

    <T> boolean deleteAll(Class<T> classType);

    <T> T queryById(Class<T> classType, long id);

    <T> List<T> query(Class<T> classType, String where, String... selectionArg);

    <T> List<T> queryAll(Class<T> classType);

}
