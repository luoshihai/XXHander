package com.example.app;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends BaseActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mHander.addKeyHandler(SecondActivity.class, this);
        mTextView = (TextView) findViewById(R.id.textView);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHander.putMessageKey(MainActivity.class, 1, "第二个界面发来的消息");
                finish();
            }
        });
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        mTextView.setText(((String) msg.obj));
    }
}
