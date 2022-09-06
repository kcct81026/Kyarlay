package com.kyarlay.ayesunaing.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kyarlay.ayesunaing.R;

public class NewsActivity extends AppCompatActivity {

    LinearLayout back_layout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_news);

        back_layout = findViewById(R.id.back_layout);


        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
