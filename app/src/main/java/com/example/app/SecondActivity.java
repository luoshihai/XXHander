package com.example.app;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.lsh.XXRecyclerview.CommonRecyclerAdapter;
import com.lsh.XXRecyclerview.CommonViewHolder;
import com.lsh.XXRecyclerview.XXRecycleView;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends BaseActivity {

    private TextView mTextView;
    private XXRecycleView xxl;
    private List<String> datas = new ArrayList<>();
    private CommonRecyclerAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mHander.addKeyHandler(SecondActivity.class, this);
        xxl = (XXRecycleView) findViewById(R.id.xxl);
        datas.add("1");
        datas.add("2");
        datas.add("3");
        datas.add("4");
        mAdapter = new CommonRecyclerAdapter<String>(this, datas, android.R.layout.simple_expandable_list_item_1) {
            @Override
            public void convert(CommonViewHolder commonViewHolder, String s, int i, boolean b) {
                commonViewHolder.setText(android.R.id.text1, s);
            }
        };
        xxl.setAdapter(mAdapter);

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
        switch (msg.what) {
            case 1:
                mTextView.setText(((String) msg.obj));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mHander.removeKeyData()
    }
}
