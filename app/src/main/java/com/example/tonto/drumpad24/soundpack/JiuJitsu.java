package com.example.tonto.drumpad24.soundpack;

import android.app.Activity;
import android.widget.ImageView;

import com.example.tonto.drumpad24.R;

import java.util.ArrayList;

/**
 * Created by tonto on 4/15/2017.
 */

public class JiuJitsu implements SoundPack {
    private ArrayList<PadInfo> padInfos;
    private Activity activity;

    public JiuJitsu(Activity mainActivity) {
        padInfos = new ArrayList<>();
        activity = mainActivity;
        padInfos.add(new PadInfo((ImageView) activity.findViewById(R.id.pad1_1),false,false,true));
        padInfos.add(new PadInfo((ImageView) activity.findViewById(R.id.pad1_2),false,false,true));
        padInfos.add(new PadInfo((ImageView) activity.findViewById(R.id.pad1_3),false,false,true));
        padInfos.add(new PadInfo((ImageView) activity.findViewById(R.id.pad2_1),false,false,true));
        padInfos.add(new PadInfo((ImageView) activity.findViewById(R.id.pad2_2),false,false,true));
        padInfos.add(new PadInfo((ImageView) activity.findViewById(R.id.pad2_3),false,false,true));
        padInfos.add(new PadInfo((ImageView) activity.findViewById(R.id.pad3_1),false,false,true));
        padInfos.add(new PadInfo((ImageView) activity.findViewById(R.id.pad3_2),false,false,false));
        padInfos.add(new PadInfo((ImageView) activity.findViewById(R.id.pad3_3),false,false,true));
        padInfos.add(new PadInfo((ImageView) activity.findViewById(R.id.pad4_1),false,false,true));
        padInfos.add(new PadInfo((ImageView) activity.findViewById(R.id.pad4_2),false,false,true));
        padInfos.add(new PadInfo((ImageView) activity.findViewById(R.id.pad4_3),false,false,true));
    }

    @Override
    public ArrayList<PadInfo> getPadInfos() {
        return padInfos;
    }

    @Override
    public String toString() {
        return "jjt";
    }
}
