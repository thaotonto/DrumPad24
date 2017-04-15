package com.example.tonto.drumpad24;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.tonto.drumpad24.soundpack.JiuJitsu;
import com.example.tonto.drumpad24.soundpack.PadInfo;
import com.example.tonto.drumpad24.soundpack.SoundPack;
import com.example.tonto.drumpad24.soundpack.WatchYourStep;
import com.example.tonto.drumpad24.sounds.SoundManager;
import com.example.tonto.drumpad24.touches.Touch;
import com.example.tonto.drumpad24.touches.TouchManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.app.PendingIntent.getActivity;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_POINTER_DOWN;
import static android.view.MotionEvent.ACTION_POINTER_UP;
import static android.view.MotionEvent.ACTION_UP;

public class MainActivity extends AppCompatActivity {
    private SoundPack soundPack;
    private List<PadInfo> padInfoList;
    private List<PressedKeyInfo> pressedKeyInfoList;
    private Spinner spSpinner;
    private String[] soundPackList = new String[]{
            "Watch Your Step",
            "JiuJitsu"
    };
    private String TAG = "MainActivity";

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
        soundPack = new WatchYourStep(this);
        SoundManager.soundPackName = soundPack.toString();
        padInfoList = soundPack.getPadInfos();
        getReferences();
        setupUI();
        addListenner();

        pressedKeyInfoList = new ArrayList<>();
        SoundManager.loadSoundIntoList(this);
    }

    private void getReferences() {
        spSpinner = (Spinner) findViewById(R.id.soundPackSpinner);
    }

    private void setupUI() {
        ArrayAdapter<String> soundPackName = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, soundPackList);
        spSpinner.setAdapter(soundPackName);
    }

    private void addListenner() {
        spSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (soundPackList[position]) {
                    case "Watch Your Step":
                        soundPack = new WatchYourStep(MainActivity.this);
                        SoundManager.soundPackName = soundPack.toString();
                        break;
                    case "JiuJitsu":
                        soundPack = new JiuJitsu(MainActivity.this);
                        SoundManager.soundPackName = soundPack.toString();
                        break;
                }
                padInfoList = soundPack.getPadInfos();
                pressedKeyInfoList = new ArrayList<>();
                Log.d(TAG, String.format("OnItemSelected: %s", soundPack.toString()));
                SoundManager.loadSoundIntoList(getApplicationContext());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        List<Touch> touches = TouchManager.toTouches(event);
        if (touches.size() > 0) {
            Touch firstTouch = touches.get(0);
            PadInfo pressedKey = findPressedKey(firstTouch);
            if (pressedKey != null) {
                if (firstTouch.getAction() == ACTION_DOWN || firstTouch.getAction() == ACTION_POINTER_DOWN) {
                    if (!containsKeyInfoWith(pressedKey.getView())) {
                        pressedKeyInfoList.add(new PressedKeyInfo(pressedKey.getView(),
                                firstTouch.getId()));
                        setPressed(pressedKey.getView(), true);
                        SoundManager.playSound(pressedKey.getView().getTag().toString(),
                                pressedKey);
                    }

                } else if (firstTouch.getAction() == ACTION_UP || firstTouch.getAction() == ACTION_POINTER_UP) {
                    Iterator<PressedKeyInfo> keyInfoIterator = pressedKeyInfoList.iterator();
                    while (keyInfoIterator.hasNext()) {
                        PressedKeyInfo pressedKeyInfo = keyInfoIterator.next();
                        ImageView view = pressedKeyInfo.getIvKey();
                        if (pressedKeyInfo.getPointerId() == firstTouch.getId()) {
                            SoundManager.stopSound(findPressedKey(firstTouch));
                            keyInfoIterator.remove();
                            setPressed(view, false);
                        }
                    }
                } else if (firstTouch.getAction() == ACTION_MOVE) {
                    for (Touch touch : touches) {
                        pressedKey = findPressedKey(touch);
                        if (pressedKey != null && !containsKeyInfoWith(pressedKey.getView())) {
                            pressedKeyInfoList.add(new PressedKeyInfo(pressedKey.getView(),
                                    firstTouch.getId()));
                            setPressed(pressedKey.getView(), true);
                            SoundManager.playSound(pressedKey.getView().getTag().toString(),
                                    pressedKey);
                        }
                        Iterator<PressedKeyInfo> infoIterator = pressedKeyInfoList.iterator();
                        while (infoIterator.hasNext()) {
                            PressedKeyInfo pressedKeyInfo = infoIterator.next();
                            ImageView view = pressedKeyInfo.getIvKey();
                            if (touch.getId() == pressedKeyInfo.getPointerId() && !touch.isInside(view)) {
                                SoundManager.stopSound(findPressedKey(touch));
                                infoIterator.remove();
                                setPressed(view, false);
                            }
                        }
                    }
                }
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
            vKey.setImageResource(R.drawable.pad_green_highlighted);
        } else {
            vKey.setImageResource(R.drawable.pad_green);
        }
    }

    private PadInfo findPressedKey(Touch touch) {
        for (int i = 0; i < padInfoList.size(); i++) {
            if (touch.isInside(padInfoList.get(i).getView())) {
                return padInfoList.get(i);
            }
        }
        return null;
    }
}
