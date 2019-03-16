package com.niro.resorder.helper;

import android.graphics.Canvas;

public class DrawBlankSpace implements IDrawItem {

    private int blankSpace;

    public DrawBlankSpace(int blankSpace) {
        this.blankSpace = blankSpace;
    }

    @Override
    public void drawOnCanvas(Canvas canvas, float x, float y) {
    }

    @Override
    public int getHeight() {
        return blankSpace;
    }
}
