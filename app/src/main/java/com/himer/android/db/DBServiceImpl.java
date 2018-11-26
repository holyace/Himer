package com.himer.android.db;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.chad.android.common.service.shell.IDBService;
import com.himer.android.AppConstant;
import com.himer.android.greendao.DaoMaster;
import com.himer.android.greendao.DaoSession;
import com.himer.android.util.HLog;

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

    private <D extends Object, T extends AbstractDao<D, Long>> T getDao(Class<D> entryClass) {
        try {
            return (T) mDaoSession.getDao(entryClass);
        } catch (Exception e) {
            HLog.exception(TAG, e);
        }
        return null;
    }

    @Override
    public long insert(Object object) {
        if (object == null) {
            return -1;
        }
        AbstractDao dao = getDao(object.getClass());
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
    public void insertAll(List<? extends Object> list) {
        if (list == null || list.size() == 0) {
            HLog.e(TAG, "insertAll empty list");
            return;
        }
        Class<? extends Object> entryClass = list.get(0).getClass();
        AbstractDao dao = getDao(entryClass);
        if (dao == null) {
            HLog.e(TAG, "insertAll get dao fail", entryClass);
            return;
        }
        dao.insertInTx(list);
    }

    @Override
    public boolean delete(Object object) {
        return false;
    }

    @Override
    public boolean update(Object object) {
        return false;
    }

    @Override
    public boolean deleteById(Class<?> classType, long id) {
        return false;
    }

    @Override
    public boolean deleteAll(Class<?> classType) {
        return false;
    }

    @Override
    public <T> T queryById(Class<T> classType, long id) {
        return null;
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
