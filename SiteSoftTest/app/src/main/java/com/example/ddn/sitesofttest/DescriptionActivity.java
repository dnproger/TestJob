package com.example.ddn.sitesofttest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by DDN on 24.08.2015.
 */
public class DescriptionActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Intent intent = getIntent();
        String temp = intent.getStringExtra("text");
        TextView tv = (TextView)findViewById(R.id.textView);
        tv.setText(temp);
    }
}
