package com.himer.android.db;

import com.chad.android.common.service.shell.IDBService;
import com.himer.android.greendao.DaoSession;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/12.
 */
public class DBServiceImpl implements IDBService {

    public DBServiceImpl(DaoSession session) {

    }

    @Override
    public long insert(Object obj) {
        return 0;
    }

    @Override
    public boolean delete(Object obj) {
        return false;
    }

    @Override
    public boolean update(Object obj) {
        return false;
    }

    @Override
    public <T> T query(Object obj) {
        return null;
    }
}
