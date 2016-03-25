package com.example.myvideomode;

import android.content.res.Configuration;

public class OrientationHelper {
    public static final int LANDSCAPE = Configuration.ORIENTATION_LANDSCAPE;
    public static final int PORTRAIT = Configuration.ORIENTATION_PORTRAIT;
    public static final int NOTHING = -100;

    public static Integer userTending(int orientation, int previous) {

        if (previous == PORTRAIT) {
            if (orientation > 60 && orientation < 120) {
                return LANDSCAPE;
            } else if (orientation > 240 && orientation < 300) {
                return LANDSCAPE;
            } 
        } else if (previous == LANDSCAPE) {
            if (orientation > 330 || orientation < 30) {
                return PORTRAIT;
            } else if (orientation > 150 && orientation < 210) {
                return PORTRAIT;
            }
        }
        return NOTHING;
    }
}
