package com.example.taguighealthconsult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class IntroductionActivity extends AppCompatActivity {
    ImageView taguiglogo, tculogo, splashing;
    TextView title;
    LottieAnimationView lottieAnimationView;
    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
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
                Intent i = new Intent(IntroductionActivity.this, IntroductionActivity.class);
                startActivity(i);
            }else if(x1 > x2){
                Intent i = new Intent(IntroductionActivity.this, MainActivity.class);
                startActivity(i);
            }
            break;
        }
        return false;
    }
}
