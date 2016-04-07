package com.szm.administrator.fgg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 *使用map将一种类型转换为另一种
 */
public class SingleAndMapActivity extends AppCompatActivity {

    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_and_map);
        createObservable();
    }

    private void createObservable() {
        subscription= Single.just(5)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return String.valueOf(integer+10);
                    }
                })
                .subscribe(new SingleSubscriber<String>() {
                    @Override
                    public void onSuccess(String value) {
                        Toast.makeText(SingleAndMapActivity.this,value,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable error) {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscription!=null&&subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}
