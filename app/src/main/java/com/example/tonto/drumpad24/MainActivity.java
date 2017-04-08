package com.example.tonto.drumpad24;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView butt11;
    private ImageView butt12;
    private ImageView butt13;
    private ImageView butt21;
    private ImageView butt22;
    private ImageView butt23;
    private ImageView butt31;
    private ImageView butt32;
    private ImageView butt33;
    private ImageView butt41;
    private ImageView butt42;
    private ImageView butt43;
    private View.OnTouchListener press;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        press = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView view = (ImageView) v;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        view.setImageResource(R.drawable.buttonpressed);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        view.setImageResource(R.drawable.button);
                        break;
                }
                return false;
            }
        };
        findView();

    }

    private void findView() {
        butt11 = (ImageView) findViewById(R.id.butt1_1);
        butt12 = (ImageView) findViewById(R.id.butt1_2);
        butt13 = (ImageView) findViewById(R.id.butt1_3);
        butt21 = (ImageView) findViewById(R.id.butt2_1);
        butt22 = (ImageView) findViewById(R.id.butt2_2);
        butt23 = (ImageView) findViewById(R.id.butt2_3);
        butt31 = (ImageView) findViewById(R.id.butt3_1);
        butt32 = (ImageView) findViewById(R.id.butt3_2);
        butt33 = (ImageView) findViewById(R.id.butt3_3);
        butt41 = (ImageView) findViewById(R.id.butt4_1);
        butt42 = (ImageView) findViewById(R.id.butt4_2);
        butt43 = (ImageView) findViewById(R.id.butt4_3);
        butt11.setOnTouchListener(press);
        butt12.setOnTouchListener(press);
        butt13.setOnTouchListener(press);
        butt21.setOnTouchListener(press);
        butt22.setOnTouchListener(press);
        butt23.setOnTouchListener(press);
        butt31.setOnTouchListener(press);
        butt32.setOnTouchListener(press);
        butt33.setOnTouchListener(press);
        butt41.setOnTouchListener(press);
        butt42.setOnTouchListener(press);
        butt43.setOnTouchListener(press);
    }
}
