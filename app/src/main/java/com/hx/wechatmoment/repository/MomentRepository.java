package com.hx.wechatmoment.repository;

import com.hx.wechatmoment.common.base.BaseRepository;
import com.hx.wechatmoment.model.MomentListBean;
import com.hx.wechatmoment.model.UserInfoBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc MomentRepository
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class MomentRepository extends BaseRepository {
    public MomentRepository() {
    }

    /**
     * 获取列表
     *
     * @return Observable
     */
    public Observable<List<MomentListBean>> getMomentList() {
        return Observable.create((ObservableOnSubscribe<List<MomentListBean>>) emitter -> apiService.getMomentList().subscribe(new Observer<List<MomentListBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<MomentListBean> hiUserInfoVo) {
                        emitter.onNext(hiUserInfoVo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        emitter.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        emitter.onComplete();
                    }
                })
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取用户信息
     *
     * @return Observable
     */
    public Observable<UserInfoBean> getUserInfo() {
        return Observable.create((ObservableOnSubscribe<UserInfoBean>) emitter -> apiService.getUserInfo().subscribe(new Observer<UserInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserInfoBean hiUserInfoVo) {
                        emitter.onNext(hiUserInfoVo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        emitter.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        emitter.onComplete();
                    }
                })
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


}
