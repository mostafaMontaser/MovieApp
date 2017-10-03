package udacity.movieapp.database.ormlite.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import udacity.movieapp.database.ormlite.DatabaseManager;

/**
 * Created by Mostafa Montaser on 2/5/2017.
 */
public abstract class BaseDao<T extends Object> {

    protected Dao<T, String> mDao;

    public BaseDao(Class<T> classType) {
        try {
            mDao = DatabaseManager.getInstance().getDatabaseHelper().getDao(classType);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
    }

    public int create(T businessObj) {
        try {
            return mDao.create(businessObj);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }

    public int createOrUpdate(T businessObject) {
        try {
            return mDao.createOrUpdate(businessObject).getNumLinesChanged();
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }


    public int update(T businessObject) {
        try {
            return mDao.update(businessObject);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(T businessObject) throws SQLException {
        return mDao.delete(businessObject);
    }

    public int deleteAll() throws SQLException {
        DeleteBuilder<T, String> deleteBuilder = mDao.deleteBuilder();
        return deleteBuilder.delete();
    }

    public List<T> getAll() {
        try {
            return mDao.queryForAll();
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return null;
    }

    public int saveAll(final List<T> businessObjectList) throws Exception {

        mDao.callBatchTasks(new Callable<T>() {
            @Override
            public T call() throws Exception {
                for (T businessObj : businessObjectList) {
                    mDao.create(businessObj);
                }
                return null;
            }
        });
        return businessObjectList.size();
    }

    public long countAll() throws SQLException {
        QueryBuilder queryBuilder = mDao.queryBuilder();
        queryBuilder.setCountOf(true);
        return mDao.countOf(queryBuilder.prepare());
    }
}
