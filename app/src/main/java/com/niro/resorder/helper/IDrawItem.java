package com.niro.resorder.helper;

import android.graphics.Canvas;

public interface IDrawItem {
    void drawOnCanvas(Canvas canvas, float x, float y);

    int getHeight();

}
