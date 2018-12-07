package com.himer.android.db;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.himer.android.AppConstant;
import com.himer.android.common.service.shell.IDBService;
import com.himer.android.common.util.HLog;
import com.himer.android.greendao.DaoMaster;
import com.himer.android.greendao.DaoSession;

import org.greenrobot.greendao.AbstractDao;

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

    private <ENTRY> AbstractDao<ENTRY, Long> getDao(Class<ENTRY> entryClass) {
        try {
            return (AbstractDao<ENTRY, Long>) mDaoSession.getDao(entryClass);
        } catch (Exception e) {
            HLog.exception(TAG, e);
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTRY> long insert(ENTRY object) {
        if (object == null) {
            return -1;
        }
        AbstractDao<ENTRY, Long> dao = getDao((Class<ENTRY>) object.getClass());
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
    public <ENTRY> void insertAll(List<ENTRY> list) {
        if (list == null || list.size() == 0) {
            HLog.e(TAG, "insertAll empty list");
            return;
        }
        Class<ENTRY> entryClass = (Class<ENTRY>) list.get(0).getClass();
        AbstractDao<ENTRY, Long> dao = getDao(entryClass);
        if (dao == null) {
            HLog.e(TAG, "insertAll get dao fail", entryClass);
            return;
        }
        dao.insertInTx(list);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTRY> long insertOrUpdate(ENTRY object) {
        AbstractDao<ENTRY, Long> dao = getDao((Class<ENTRY>) object.getClass());

        if (dao == null) {
            return -1;
        }

        return dao.insertOrReplace(object);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTRY> boolean delete(ENTRY object) {
        Class<ENTRY> entryClass = (Class<ENTRY>) object.getClass();
        AbstractDao<ENTRY, Long> dao = getDao(entryClass);
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
    public <ENTRY> boolean update(ENTRY object) {
        return false;
    }

    @Override
    public <ENTRY> boolean deleteById(Class<ENTRY> classType, long id) {
        return false;
    }

    @Override
    public <ENTRY> boolean deleteAll(Class<ENTRY> classType) {
        return false;
    }

    @Override
    public <ENTRY> ENTRY queryById(Class<ENTRY> classType, long id) {
        AbstractDao<ENTRY, Long> dao = getDao(classType);
        if (dao == null) {
            return null;
        }
        return dao.load(id);
    }

    @Override
    public <ENTRY> List<ENTRY> query(Class<ENTRY> classType, String where, String... selectionArg) {
        return null;
    }

    @Override
    public <ENTRY> List<ENTRY> queryAll(Class<ENTRY> classType) {

        AbstractDao<ENTRY, Long> dao = getDao(classType);
        if (dao == null) {
            HLog.e(TAG, "queryAll get dao fail", classType);
            return null;
        }
        return dao.loadAll();
    }
}
