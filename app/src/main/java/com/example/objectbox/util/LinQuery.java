package com.example.objectbox.util;

import com.example.objectbox.util.ObjectBox;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscriptionList;
import io.objectbox.reactive.DataTransformer;
import io.objectbox.reactive.ErrorObserver;

/**
 * 订阅
 * @param <T>
 */
public class LinQuery<T> {

    private DataSubscriptionList subscriptions = new DataSubscriptionList();

    public Box getBox(Class<T> object){
        Box<T> tBox = ObjectBox.get().boxFor(object);
        return tBox;
    }

    public void subscribes(Query<T> query,DataTransformer datas,ErrorObserver error,DataObserver success) {
        query.subscribe(subscriptions).on(AndroidScheduler.mainThread()).transform(datas).onError(error).observer(success);
    }

    public void stop() {
        subscriptions.cancel();
    }

    //转换数据
    DataTransformer datas = new DataTransformer<List<T>, Object>() {
        @Override
        public Object transform(List<T> source) throws Exception {
            return null;
        }
    };
    //错误返回
    ErrorObserver error =  new ErrorObserver() {
        @Override
        public void onError(Throwable th) {

        }
    };
    //成功返回
    DataObserver success = new DataObserver<T>() {
        @Override
        public void onData(T data) {

        }
    };
}
