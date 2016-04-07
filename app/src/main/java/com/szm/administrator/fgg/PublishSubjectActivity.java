package com.szm.administrator.fgg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rx.Observer;
import rx.Subscription;
import rx.subjects.PublishSubject;

/**
 * Subject这个对象既是Observable又是Observer，可以把Subject想象成一个管道：从一端把数据注入，结果就会从另一端输出。
 */
public class PublishSubjectActivity extends AppCompatActivity implements View.OnClickListener {

    PublishSubject<Integer> publishSubject;

    Subscription subscription;

    Button but;
    TextView tv;
    private int mCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_subject);
        tv= (TextView) findViewById(R.id.tv_show);
        but= (Button) findViewById(R.id.but_add);
        but.setOnClickListener(this);
        createPublishSubject();
    }

    private void createPublishSubject() {
        publishSubject=publishSubject.create();
        subscription=publishSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                tv.setText(String.valueOf(integer));
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

    @Override
    public void onClick(View v) {
        mCounter++;
        publishSubject.onNext(mCounter);
    }
}
