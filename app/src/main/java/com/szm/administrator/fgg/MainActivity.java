package com.szm.administrator.fgg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button async,sync,psubject,search,singlemap,flatmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        async= (Button) findViewById(R.id.async);
        sync= (Button) findViewById(R.id.sync);
        psubject= (Button) findViewById(R.id.psubject);
        search= (Button) findViewById(R.id.search);
        singlemap= (Button) findViewById(R.id.singlemap);
        flatmap= (Button) findViewById(R.id.flatmap);
        async.setOnClickListener(this);
        sync.setOnClickListener(this);
        psubject.setOnClickListener(this);
        search.setOnClickListener(this);
        singlemap.setOnClickListener(this);
        flatmap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.async:
                startActivity(new Intent(this,AsyncActivity.class));
                break;
            case R.id.sync:
                startActivity(new Intent(this,SyncActivityy.class));
                break;
            case R.id.psubject:
                startActivity(new Intent(this,PublishSubjectActivity.class));
                break;
            case R.id.search:
                startActivity(new Intent(this,SearchDelaActivity.class));
                break;
            case R.id.singlemap:
                startActivity(new Intent(this,SingleAndMapActivity.class));
                break;
            case R.id.flatmap:
                startActivity(new Intent(this,FlatMapTryActivity.class));
                break;
            default:
                break;
        }
    }
}
