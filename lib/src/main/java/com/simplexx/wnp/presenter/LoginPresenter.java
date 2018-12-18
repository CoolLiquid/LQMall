package com.simplexx.wnp.presenter;

import com.simplexx.wnp.baselib.basemvp.IView;
import com.simplexx.wnp.baselib.executor.ActionRunnable;
import com.simplexx.wnp.repository.INotifyHistoryRepository;
import com.simplexx.wnp.repository.entity.NotifyHistory;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by wnp on 2018/7/2.
 */

public class LoginPresenter extends BasePresenter<LoginPresenter.ILoginView> {
    public LoginPresenter(ILoginView iView) {
        super(iView);
    }


    public interface ILoginView extends IView {
        void onTestRepository(String flag);
    }

    @Inject
    INotifyHistoryRepository iNotifyHistoryRepository;

    /**
     * 测试数据库是否接入正确
     */
    public void testRepository() {
        newActionBuilder().setRunnable(new ActionRunnable() {

            @Override
            public void run() {
                Date date = new Date();
                date.setTime(System.currentTimeMillis());

                iNotifyHistoryRepository
                        .save(new NotifyHistory("123", "456", 123456l, 890l, date));

                if (iNotifyHistoryRepository.exists("123")) {
                    NotifyHistory notifyHistory = iNotifyHistoryRepository.load("123");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(notifyHistory.getCreate());
                    getView().onTestRepository(String.valueOf(calendar.get(Calendar.DATE)));
                }
            }
        }).setRunBackground().run();
    }

    @Override
    public void onViewCreate() {
        super.onViewCreate();
        System.out.print("onViewCreate");
        testRepository();
    }

    @Override
    public void onViewDestroy() {
        super.onViewDestroy();
        System.out.print("onViewDestroy");
    }

    @Override
    public void onViewResume() {
        super.onViewResume();
        System.out.print("onViewResume");
    }

    @Override
    public void onViewPause() {
        super.onViewPause();
        System.out.print("onViewPause");
    }

    @Override
    public void onViewStart() {
        super.onViewStart();
        System.out.print("onViewStart");
    }

    @Override
    public void onViewStop() {
        super.onViewStop();
        System.out.print("onViewStop");
    }
}
