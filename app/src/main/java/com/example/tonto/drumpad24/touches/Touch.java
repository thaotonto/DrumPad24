package com.example.tonto.drumpad24.touches;

import android.view.View;

/**
 * Created by tonto on 4/15/2017.
 */

public class Touch {
    private float x;
    private float y;
    private int id;
    private int action;

    public Touch(float x, float y, int id, int action) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.action = action;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getAction() {
        return action;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Touch{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                ", action=" + action +
                '}';
    }

    public boolean isInside(View v) {
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
}
