package com.storyview.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class Myriad_RegularTextView extends TextView {

    /*
     * Caches typefaces based on their file path and name, so that they don't have to be created every time when they are referenced.
     */
    private static Typeface mTypeface;

    public Myriad_RegularTextView(final Context context) {
        this(context, null);
    }

    public Myriad_RegularTextView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Myriad_RegularTextView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        if (mTypeface == null) {
            mTypeface = Typeface.createFromAsset(context.getAssets(), "font/MyriadPro_Regular.otf");
        }
        setTypeface(mTypeface);
    }

}