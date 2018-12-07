package com.himer.android.common.service.shell;

import com.himer.android.common.service.IService;

import java.util.List;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/12.
 */
public interface IDBService extends IService {

    <ENTRY> long insert(ENTRY object);

    <ENTRY> void insertAll(List<ENTRY> list);

    <ENTRY> long insertOrUpdate(ENTRY object);

    <ENTRY> boolean delete(ENTRY object);

    <ENTRY> boolean update(ENTRY object);

    <ENTRY> boolean deleteById(Class<ENTRY> classType, long id);

    <ENTRY> boolean deleteAll(Class<ENTRY> classType);

    <ENTRY> ENTRY queryById(Class<ENTRY> classType, long id);

    <ENTRY> List<ENTRY> query(Class<ENTRY> classType, String where, String... selectionArg);

    <ENTRY> List<ENTRY> queryAll(Class<ENTRY> classType);

}
