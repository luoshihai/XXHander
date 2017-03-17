package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.lsh.XXRecyclerview.CommonRecyclerAdapter;
import com.lsh.XXRecyclerview.CommonViewHolder;
import com.lsh.XXRecyclerview.XXRecycleView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private TextView mTextView;
    private XXRecycleView xxl;
    private List<String> datas = new ArrayList<>();
    private CommonRecyclerAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHander.addKeyHandler(MainActivity.class, this);
        mTextView = (TextView) findViewById(R.id.tv);

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
    }

    public void tv(View view) {
        mHander.putMessageKey(SecondActivity.class, 1, "mainActivity发来的消息");
        startActivity(new Intent(this,SecondActivity.class));

    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        mAdapter.addAll(datas);
        mTextView.setText(((String) msg.obj));
    }
}
