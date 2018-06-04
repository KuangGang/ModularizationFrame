package com.kgandroid.library.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomCenterDrawableText extends TextView {

    public CustomCenterDrawableText(Context context, AttributeSet attrs,
                                    int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomCenterDrawableText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCenterDrawableText(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            if (drawables[0] != null) {
            	Drawable drawableLeft = drawables[0];
            	onDrawContent(drawableLeft, canvas);
            } else if (drawables[2] != null) {
            	Drawable drawableRight = drawables[2];
            	onDrawContent(drawableRight, canvas);
            }
        }
        super.onDraw(canvas);
    }
    
    public void onDrawContent(Drawable drawable, Canvas canvas) {
    	float textWidth = getPaint().measureText(getText().toString());
        int drawablePadding = getCompoundDrawablePadding();
        int drawableWidth = 0;
        drawableWidth = drawable.getIntrinsicWidth();
        float bodyWidth = textWidth + drawableWidth + drawablePadding;
        setPadding(0, 0, (int)(getWidth() - bodyWidth), 0);
        canvas.translate((getWidth() - bodyWidth) / 2, 0);
    }
}