package com.himer.android.db;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.himer.android.AppConstant;
import com.himer.android.common.service.shell.IDBService;
import com.himer.android.common.util.HLog;
import com.himer.android.greendao.DaoMaster;
import com.himer.android.greendao.DaoSession;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.annotation.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/12.
 */
public class DBServiceImpl implements IDBService {

    private static final String TAG = DBServiceImpl.class.getSimpleName();

    private DaoSession mDaoSession;

    private volatile boolean mInit = false;

    @Override
    public void init(Application app, Map<String, Object> params) {

        if (mInit) {
            HLog.w(TAG, "DBServiceImpl init already called!");
            return;
        }

        try {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(
                    app, AppConstant.DB_NAME, null);
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster master = new DaoMaster(db);
            DaoSession session = master.newSession();

            mDaoSession = session;
            mInit = true;
        } catch (Exception e) {
            HLog.exception(TAG, e);
        }
    }

    @Override
    public void destory() {
        if (mDaoSession != null) {
            mDaoSession.clear();
        }
        mDaoSession = null;
    }

    private <T> AbstractDao<T, Long> getDao(Class<T> entryClass) {
        try {
            return (AbstractDao<T, Long>) mDaoSession.getDao(entryClass);
        } catch (Exception e) {
            HLog.exception(TAG, e);
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> long insert(T object) {
        if (object == null) {
            return -1;
        }
        AbstractDao<T, Long> dao = getDao((Class<T>) object.getClass());
//        AbstractDao<T, Long> dao = null;
        if (dao == null) {
            HLog.e(TAG, "insert get dao fail", object.toString());
            return -1;
        }
        try {
            return dao.insert(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void insertAll(List<T> list) {
        if (list == null || list.size() == 0) {
            HLog.e(TAG, "insertAll empty list");
            return;
        }
        Class<T> entryClass = (Class<T>) list.get(0).getClass();
//        Class<T> entryClass = null;
        AbstractDao<T, Long> dao = getDao(entryClass);
        if (dao == null) {
            HLog.e(TAG, "insertAll get dao fail", entryClass);
            return;
        }
        dao.insertInTx(list);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> long insertOrUpdate(T object) {
        AbstractDao<T, Long> dao = getDao((Class<T>) object.getClass());

        if (dao == null) {
            return -1;
        }

        return dao.insertOrReplace(object);
    }

    @Override
    public <T> boolean delete(T object) {
        Class<? extends Object> entryClass = object.getClass();
        AbstractDao dao = getDao(entryClass);
        if (dao == null) {
            HLog.e(TAG, "delete get dao fail", entryClass);
            return false;
        }
        try {
            dao.delete(object);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public <T> boolean update(T object) {
        return false;
    }

    @Override
    public <T> boolean deleteById(Class<T> classType, long id) {
        return false;
    }

    @Override
    public <T> boolean deleteAll(Class<T> classType) {
        return false;
    }

    @Override
    public <T> T queryById(Class<T> classType, long id) {
        AbstractDao<T, Long> dao = getDao(classType);
        if (dao == null) {
            return null;
        }
        return dao.load(id);
    }

    @Override
    public <T> List<T> query(Class<T> classType, String where, String... selectionArg) {
        return null;
    }

    @Override
    public <T> List<T> queryAll(Class<T> classType) {

        AbstractDao dao = getDao(classType);
        if (dao == null) {
            HLog.e(TAG, "queryAll get dao fail", classType);
            return null;
        }
        return dao.loadAll();
    }
}
