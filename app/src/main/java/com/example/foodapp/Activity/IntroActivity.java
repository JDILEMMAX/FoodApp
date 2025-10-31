package com.example.foodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.foodapp.R;

public class IntroActivity extends AppCompatActivity {
private ConstraintLayout startBtn;
private ImageView burger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  startActivity(new Intent(IntroActivity.this, MainActivity.class));
              }
        });

        burger = findViewById(R.id.burger);
        burger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
            }
        });
    }
}