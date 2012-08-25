package com.ayakix;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("NewApi")
public class CameraOverlayView extends SurfaceView {
    private Paint tempPaint;
    private Path tempPath; // for real time draw
    
    public CameraOverlayView(Context context) {
        super(context);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
        
        setDrawingCacheEnabled(true);
        setFocusable(true);
        
        this.tempPaint = new Paint();
        this.tempPaint.setColor(Color.GREEN);
        this.tempPaint.setStyle(Paint.Style.STROKE);
        this.tempPaint.setAntiAlias(true);
        this.tempPaint.setStrokeWidth(10);
        
        this.setZOrderOnTop(true);
    }
    
    private void doDraw(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        onDraw(canvas);
        holder.unlockCanvasAndPost(canvas);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this.tempPath, this.tempPaint);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            // for real time draw
            tempPath = new Path();
            tempPath.moveTo(event.getX(), event.getY());
            break;
        case MotionEvent.ACTION_MOVE:
            // for real time draw
            tempPath.lineTo(event.getX(), event.getY());
            break;
        case MotionEvent.ACTION_UP:
            // for real time draw
            tempPath.lineTo(event.getX(), event.getY());
            break;
        default:
        }
        this.doDraw(this.getHolder());
        return true;
    }
}