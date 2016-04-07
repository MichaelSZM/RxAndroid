package com.szm.administrator.fgg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * 延迟
 */
public class SearchDelaActivity extends AppCompatActivity {

    private EditText mSearchInput;
    private TextView mNoResultsIndicator;
    private RecyclerView mSearchResults;
    private SimpleStringAdapter mSearchResultsAdapter;

    Subscription subscription;
    PublishSubject<String> publishSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach_dela);
        initConfig();
        createObservables();
        listenToSearchInput();
    }

    private void listenToSearchInput() {
        mSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                publishSubject.onNext(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void createObservables() {
        publishSubject=publishSubject.create();
        subscription=publishSubject
                .debounce(400, TimeUnit.MILLISECONDS)//设置没有数据传入达400毫秒时才发送数据
                .observeOn(Schedulers.io())//切换到io线程，map涉及到查询，比较耗时
                .map(new Func1<String, List<String>>() {
                    @Override
                    public List<String> call(String s) {
                        return getMatchingCities(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//切换回主线程，要在onNext中更新界面数据
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<String> strings) {
                        handleSearchResults(strings);
                    }
                });
    }

    private void handleSearchResults(List<String> cities) {
        if (cities.isEmpty()) {
            showNoSearchResults();
        } else {
            showSearchResults(cities);
        }
    }

    private void showSearchResults(List<String> cities) {
        mNoResultsIndicator.setVisibility(View.GONE);
        mSearchResults.setVisibility(View.VISIBLE);
        mSearchResultsAdapter.setStrings(cities);
    }

    private void showNoSearchResults() {
        mNoResultsIndicator.setVisibility(View.VISIBLE);
        mSearchResults.setVisibility(View.GONE);
    }

    private void initConfig() {
        mSearchInput = (EditText) findViewById(R.id.search_input);
        mNoResultsIndicator = (TextView) findViewById(R.id.no_results_indicator);
        mSearchResults = (RecyclerView) findViewById(R.id.search_results);
        mSearchResults.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultsAdapter = new SimpleStringAdapter(this);
        mSearchResults.setAdapter(mSearchResultsAdapter);
    }


    private List<String> getData(){
        List<String> list=new ArrayList<>();
        StringBuffer sb=new StringBuffer();
        String str="a";
        for(int i=0;i<5;i++){
            sb.append(str);
            list.add(sb.toString());
        }
        sb=new StringBuffer();
        str="b";
        for(int i=0;i<5;i++){
            sb.append(str);
            list.add(sb.toString());
        }
        return list;
    }

    private List<String> getMatchingCities(String searchString) {
        if (searchString.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> toReturn = new ArrayList<>();
        for (String city : getData()) {
            if (city.toLowerCase().startsWith(searchString.toLowerCase())) {
                toReturn.add(city);
            }
        }
        return toReturn;
    }

}
