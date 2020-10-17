package com.ebdaa.reportapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Border extends Drawable
        {
public Paint paint;
public Rect bounds_rect;

public Border(int color, int width)
        {
//            int colorValue = Color.parseColor("#00ff00");
        this.paint = new Paint();
        this.paint.setColor(color);
        this.paint.setStrokeWidth(width);
        this.paint.setStyle(Paint.Style.STROKE);
        }


            @Override
            protected void onBoundsChange(Rect bounds) {
                super.onBoundsChange(bounds);
                this.bounds_rect = bounds;
            }

            @Override
            public void draw(@NonNull Canvas canvas) {
                canvas.drawRect(this.bounds_rect, this.paint);
            }

            @Override
            public void setAlpha(int i) {

            }

            @Override
            public void setColorFilter(@Nullable ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return 1;
            }
        }