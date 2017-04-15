package com.example.tonto.drumpad24.soundpack;

import android.widget.ImageView;

/**
 * Created by tonto on 4/15/2017.
 */

public class PadInfo {
    private ImageView view;
    private boolean loop;
    private boolean group;
    private boolean playFully;

    public PadInfo(ImageView view, boolean loop, boolean group, boolean playFully) {
        this.view = view;
        this.loop = loop;
        this.group = group;
        this.playFully = playFully;
    }

    public ImageView getView() {
        return view;
    }

    public boolean isLoop() {
        return loop;
    }

    public boolean isGroup() {
        return group;
    }

    public boolean isPlayFully() {
        return playFully;
    }
}
