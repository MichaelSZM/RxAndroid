package com.szm.administrator.fgg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 */
public class AsyncActivity extends AppCompatActivity {

    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createObservableAsync();
    }

    /**
     *异步使用
     */
    private void createObservableAsync() {
        Observable<List<Integer>> observable=Observable.fromCallable(new Callable<List<Integer>>() {
            @Override
            public List<Integer> call() throws Exception {
                return getIntegers();
            }
        });
        subscription=observable.subscribeOn(Schedulers.io())//设置注册所在线程为io线程
                  .observeOn(AndroidSchedulers.mainThread())//设置观察所在线程为ui主线程
                  .subscribe(new Observer<List<Integer>>() {
                      @Override
                      public void onCompleted() {

                      }

                      @Override
                      public void onError(Throwable e) {

                      }

                      @Override
                      public void onNext(List<Integer> strings) {
                          Log.i("szm--","szm----"+strings.toString());
                          Toast.makeText(AsyncActivity.this,strings.toString(),Toast.LENGTH_SHORT).show();
                      }
                  });
    }


    /**
     * 模拟耗时操作
     * @return
     */
    private List<Integer> getIntegers(){
        List<Integer> ll=new ArrayList<>();
        try {
            for(int i=0;i<8;i++){
                ll.add(i+1);
                Thread.sleep(3000);
            }
        }catch (Exception e){

        }
        return ll;
    }

    /**
     * 解除订阅，防止内存泄漏
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
