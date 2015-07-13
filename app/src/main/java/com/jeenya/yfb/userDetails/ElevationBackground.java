package com.jeenya.yfb.userDetails;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.jeenya.yfb.R;

/**
 * Created by hp on 7/9/2015.
 */
public class ElevationBackground {
    private static Drawable sBackground;
    private static Context c;


    public static Drawable getDrawable(Context d) {
        c = d;

        if (sBackground == null) {
            sBackground = c.getResources().getDrawable(R.drawable.shape_circle);
        }
        return sBackground;
    }
}
