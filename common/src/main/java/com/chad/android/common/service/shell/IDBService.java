package com.chad.android.common.service.shell;

import com.chad.android.common.service.IService;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/12.
 */
public interface IDBService extends IService {

    long insert(Object obj);

    boolean delete(Object obj);

    boolean update(Object obj);

    <T> T query(Object obj);
}
