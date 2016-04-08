package com.szm.administrator.fgg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * flatmap可以实现一对多的转换
 */
public class FlatMapTryActivity extends AppCompatActivity {

    Subscription subscription;

    List<Student> students = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_map_try);
        initData();
//        createObservableDealWithFlatMap();
        propertyWay();
    }

    private void initData() {
        List<String> courses = new ArrayList<>();
        courses.add("数学");
        courses.add("语文");
        for (int i = 0; i < 10; i++) {
            students.add(new Student("member + " + i, courses));
        }
    }

    private void createObservableDealWithFlatMap() {
        subscription= Observable.from(students).flatMap(new Func1<Student, Observable<String>>() {
            @Override
            public Observable<String> call(Student student) {
                return Observable.from(student.courses);
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i("szm--",s);
            }
        });
    }

    private  void propertyWay(){
        subscription= Observable.from(students).subscribe(new Action1<Student>() {
            @Override
            public void call(Student student) {
                student.printAllCourses();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription!=null&&subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

    private class Student {

        String num;
        List<String> courses;

        public Student(String num,List<String> courses){
            this.num=num;
            this.courses=courses;
        }

        public void printAllCourses(){
            if(courses!=null&&courses.size()>0){
                for(String s:courses){
                    Log.i("szm--","schoolNum:---"+num+s);
                }
            }
        }
    }
}
