package com.example.tonto.drumpad24;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<ImageView> ivPads;
    private List<PressedKeyInfo> pressedKeyInfoList;

    class PressedKeyInfo {
        private ImageView ivKey;
        private int pointerId;

        public PressedKeyInfo(ImageView ivKey, int pointerId) {
            this.ivKey = ivKey;
            this.pointerId = pointerId;
        }

        public ImageView getIvKey() {
            return ivKey;
        }

        public int getPointerId() {
            return pointerId;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivPads = new ArrayList<>();
        ivPads.add((ImageView) findViewById(R.id.butt1_1));
        ivPads.add((ImageView) findViewById(R.id.butt1_2));
        ivPads.add((ImageView) findViewById(R.id.butt1_3));
        ivPads.add((ImageView) findViewById(R.id.butt2_1));
        ivPads.add((ImageView) findViewById(R.id.butt2_2));
        ivPads.add((ImageView) findViewById(R.id.butt2_3));
        ivPads.add((ImageView) findViewById(R.id.butt3_1));
        ivPads.add((ImageView) findViewById(R.id.butt3_2));
        ivPads.add((ImageView) findViewById(R.id.butt3_3));
        ivPads.add((ImageView) findViewById(R.id.butt4_1));
        ivPads.add((ImageView) findViewById(R.id.butt4_2));
        ivPads.add((ImageView) findViewById(R.id.butt4_3));

        pressedKeyInfoList = new ArrayList<>();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = MotionEventCompat.getActionIndex(event);
        int pointerId = event.getPointerId(pointerIndex);
        float pointerX = event.getX(pointerIndex);
        float pointerY = event.getY(pointerIndex);
        int pointerAction = event.getActionMasked();

        ImageView pressedKey = findPressedKey(pointerX, pointerY);
        if (pressedKey != null) {
            if (pointerAction == MotionEvent.ACTION_MOVE) {
                for (int i = 0; i < pressedKeyInfoList.size(); i++) {
                    PressedKeyInfo pressedKeyInfo = pressedKeyInfoList.get(i);
                    if (pressedKeyInfo.getPointerId() == pointerId && !isInside(pointerX, pointerY, pressedKeyInfo.getIvKey())) {
                        pressedKeyInfoList.remove(i);
                        setPressed(pressedKeyInfo.getIvKey(), false);
                    }
                }
            }

            if (pointerAction == MotionEvent.ACTION_DOWN || pointerAction == MotionEvent.ACTION_POINTER_DOWN ||
                    pointerAction == MotionEvent.ACTION_MOVE) {
                if (!containsKeyInfoWith(pressedKey)) {
                    pressedKeyInfoList.add(new PressedKeyInfo(pressedKey, pointerId));
                }
                setPressed(pressedKey, true);
            }
            if (pointerAction == MotionEvent.ACTION_POINTER_UP || pointerAction == MotionEvent.ACTION_UP) {
                for (int i = 0; i < pressedKeyInfoList.size(); i++) {
                    if (pressedKeyInfoList.get(i).getPointerId() == pointerId) {
                        pressedKeyInfoList.remove(i);
                    }
                }
                setPressed(pressedKey, false);
            }
        }
        return super.onTouchEvent(event);
    }

    private boolean containsKeyInfoWith(ImageView ivKey) {
        for (PressedKeyInfo pressedKeyInfo : pressedKeyInfoList) {
            if (pressedKeyInfo.getIvKey() == ivKey) {
                return true;
            }
        }
        return false;
    }

    private void setPressed(ImageView vKey, boolean isPressed) {
        if (isPressed) {
            vKey.setImageResource(R.drawable.buttonpressed);
        } else {
            vKey.setImageResource(R.drawable.button);
        }
    }

    private boolean isInside(float x, float y, View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + v.getWidth();
        int bottom = top + v.getHeight();

        if (x > left && x < right &&
                y > top && y < bottom) {
            return true;
        } else return false;
    }

    private ImageView findPressedKey(float x, float y) {
        for (int i = 0; i < ivPads.size(); i++) {
            if (isInside(x, y, ivPads.get(i))) {
                return ivPads.get(i);
            }
        }
        return null;
    }
}
