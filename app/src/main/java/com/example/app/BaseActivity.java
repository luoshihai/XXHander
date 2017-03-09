package com.example.app;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.luoshihai.xxhander.BaseHandlerOperate;
import com.luoshihai.xxhander.BaseHandlerUpDate;

/**
 * Created by lsh on 2017/3/9.
 */

public class BaseActivity extends AppCompatActivity implements BaseHandlerUpDate{

    protected BaseHandlerOperate mHander;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHander = BaseHandlerOperate.getBaseHandlerOperate();
    }


    @Override
    public void handleMessage(Message msg) {

    }
}
