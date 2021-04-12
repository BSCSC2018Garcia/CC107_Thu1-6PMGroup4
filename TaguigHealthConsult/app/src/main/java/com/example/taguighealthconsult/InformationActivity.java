package com.example.taguighealthconsult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InformationActivity extends AppCompatActivity {
        Button home_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        home_back = findViewById(R.id.back_home);
        home_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dash = new Intent(InformationActivity.this, DashBoardActivity.class);
                startActivity(dash);
            }
        });
    }
}
