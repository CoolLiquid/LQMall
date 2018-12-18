package com.wnp.lqmall.repository;

import com.simplexx.wnp.repository.INotifyHistoryRepository;
import com.wnp.lqmall.dao.DaoSession;
import com.wnp.lqmall.dao.NotifyHistoryDao;
import com.simplexx.wnp.repository.entity.NotifyHistory;

/**
 * Created by wnp on 2018/12/18.
 */

public class NotifyHistoryRepository extends BaseRepoditory<NotifyHistoryDao, NotifyHistory, String>
        implements INotifyHistoryRepository {

    public NotifyHistoryRepository(DaoSession daoMaster) {
        super(daoMaster);
    }

    @Override
    protected NotifyHistoryDao getDao(DaoSession daoSession) {
        return daoSession.getNotifyHistoryDao();
    }
}
