package com.example.evilmind.doodler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by evilmind on 5/5/2016.
 */
public class SimpleDrawingView extends View {

    private Paint paint = new Paint();
    private Path path = new Path();
    public boolean drawFlag;
    public Bitmap bitmapToDraw;

    public SimpleDrawingView(Context context, AttributeSet attrs) {

        super(context, attrs);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        //paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void LoadImage(Canvas canvas)
    {
        //Canvas MyCanvas = new Canvas(bitmapToDraw.copy(Bitmap.Config.ARGB_8888, true));
        if (bitmapToDraw != null) {
            canvas.drawBitmap(bitmapToDraw, 0, 0, paint);
        }
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawPath(path, paint);
        LoadImage(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Get the coordinates of the touch event.
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Set a new starting point
                path.moveTo(eventX, eventY);
                return true;
            case MotionEvent.ACTION_MOVE:
                // Connect the points
                path.lineTo(eventX, eventY);
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }
}
