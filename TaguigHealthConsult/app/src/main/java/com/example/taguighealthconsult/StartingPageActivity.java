package com.example.taguighealthconsult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class StartingPageActivity extends AppCompatActivity {
    float x1, x2, y1, y2;
    Button login_button;
    Button register_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_page);
       login_button = findViewById(R.id.login_button);
       register_edit = findViewById(R.id.register_edit);

           login_button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(StartingPageActivity.this, LoginActivity.class);
                   startActivity(intent);
               }
           });

           register_edit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(StartingPageActivity.this, RegisterActivity.class);
                   startActivity(intent);
               }
           });

    }
    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 <x2){
                    Intent i = new Intent(StartingPageActivity.this,ImageViewActivity.class);
                    startActivity(i);
                }else if(x1 > x2){
                    Intent i = new Intent(StartingPageActivity.this,StartingPageActivity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }
}
