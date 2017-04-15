package com.example.tonto.drumpad24.sounds;

import android.content.Context;
import android.media.SoundPool;

import com.example.tonto.drumpad24.soundpack.PadInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import static android.media.AudioManager.STREAM_MUSIC;

/**
 * Created by tonto on 4/15/2017.
 */

public class SoundManager {
    private static int NUMBER_OF_PADS = 12;
    public static SoundPool soundPool = new SoundPool(NUMBER_OF_PADS, STREAM_MUSIC, 0);
    public static ArrayList<Integer> soundIDList = new ArrayList<>();
    static HashMap<String, Integer> listSoundID = new HashMap<>();
    private static int streamId;
    private static ArrayList<PadInfo> playingPads = new ArrayList<>();
    private static ArrayList<Integer> streamIDs = new ArrayList<>();

    static {
        listSoundID.put("PAD01", 0);
        listSoundID.put("PAD02", 1);
        listSoundID.put("PAD03", 2);
        listSoundID.put("PAD04", 3);
        listSoundID.put("PAD05", 4);
        listSoundID.put("PAD06", 5);
        listSoundID.put("PAD07", 6);
        listSoundID.put("PAD08", 7);
        listSoundID.put("PAD09", 8);
        listSoundID.put("PAD10", 9);
        listSoundID.put("PAD11", 10);
        listSoundID.put("PAD12", 11);

    }

    public static void loadSoundIntoList(Context context) {
        for (int i = 1; i <= NUMBER_OF_PADS; i++) {
            int resIDSound = context.getResources().getIdentifier("sound_" + i, "raw", context.getPackageName());
            int soundPoolID = soundPool.load(context, resIDSound, 1);
            soundIDList.add(soundPoolID);
        }
    }


    public static void playSound(String string, PadInfo padInfo) {
        int loop = 0;
        if (padInfo.isGroup()) {
            Iterator<PadInfo> iteratorPadInfo = playingPads.iterator();
            Iterator<Integer> iteratorStreamIDs = streamIDs.iterator();
            while (iteratorPadInfo.hasNext()) {
                PadInfo pad = iteratorPadInfo.next();
                Integer streamID = iteratorStreamIDs.next();
//                if (!padInfo.equals(pad)) {
                if (pad.isGroup()) {
//                        if (!pad.isPlayFully())
                    soundPool.stop(streamID);
                    iteratorPadInfo.remove();
                    iteratorStreamIDs.remove();
                }
//                }
            }
        } else {
            Iterator<PadInfo> iteratorPadInfo = playingPads.iterator();
            Iterator<Integer> iteratorStreamIDs = streamIDs.iterator();
            while (iteratorPadInfo.hasNext()) {
                PadInfo pad = iteratorPadInfo.next();
                Integer streamID = iteratorStreamIDs.next();
                if (padInfo.equals(pad)) {
                    soundPool.stop(streamID);
                    iteratorPadInfo.remove();
                    iteratorStreamIDs.remove();
                }
            }
        }
        playingPads.add(padInfo);
        if (padInfo.isLoop()) {
            loop = -1;
        }
        streamId = soundPool.play(soundIDList.get(listSoundID.get(string)), 1.f, 1.f, 1, loop, 1.0f);
        streamIDs.add(streamId);
    }


    public static void stopSound(PadInfo padInfo) {
        Iterator<PadInfo> iteratorPadInfo = playingPads.iterator();
        Iterator<Integer> iteratorStreamIDs = streamIDs.iterator();
        if (!padInfo.isPlayFully()) {
            while (iteratorPadInfo.hasNext()) {
                PadInfo pad = iteratorPadInfo.next();
                Integer streamID = iteratorStreamIDs.next();
                if (padInfo.equals(pad)) {
                    soundPool.stop(streamID);
                    iteratorPadInfo.remove();
                    iteratorStreamIDs.remove();
                }
            }
        }
    }

    public static void loop(PadInfo padInfo) {
        Iterator<PadInfo> iteratorPadInfo = playingPads.iterator();
        Iterator<Integer> iteratorStreamIDs = streamIDs.iterator();
        while (iteratorPadInfo.hasNext()) {
            PadInfo pad = iteratorPadInfo.next();
            Integer streamID = iteratorStreamIDs.next();
            if (padInfo.equals(pad)) {

            }
        }
    }
}
