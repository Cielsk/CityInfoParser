package com.github.cielsk.cityinfoparser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private int mCounter = 0;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.count);

        JsonFileHandler jsonFileHandler = new JsonFileHandler(this);
        AddressInfoParser addressInfoParser = new AddressInfoParser(this);

        Observable task = jsonFileHandler
            .handle()
            .flatMap(Observable::from)
            .doOnNext(addressInfoParser::parse)
            .subscribeOn(Schedulers.io())
            .publish()
            .autoConnect(2);

        task
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(__ -> {
                mCounter++;
                mTextView.setText("Number added :" + mCounter);
            });

        task
            .toCompletable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(() -> Toast
                .makeText(this, "Parsing complete!", Toast.LENGTH_SHORT)
                .show());
    }
}