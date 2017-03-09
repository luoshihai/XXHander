package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHander.addKeyHandler(MainActivity.class, this);
        mTextView = (TextView) findViewById(R.id.tv);
    }

    public void tv(View view) {
        mHander.putMessageKey(SecondActivity.class, 1, "mainActivity发来的消息");
        startActivity(new Intent(this,SecondActivity.class));

    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        mTextView.setText(((String) msg.obj));
    }
}
