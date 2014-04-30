package com.xinlan.camerademo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements
        SurfaceHolder.Callback, Runnable
{
    private Context mContext;
    protected SurfaceHolder sfh;
    public static int screenW, screenH;
    private boolean flag;
    private Canvas canvas;
    private Thread th;

    Paint p = new Paint();

    private float dy = 2;
    private float line_y = 0;

    public static final int LONG_RIGHT = 2;
    public static final int LONG_LEFT = 1;
    public static final int LONG_UP = 3;
    public static final int LONG_DOWN = 0;
    public static final int LONG_WAIT = -1;
    private float longDx = 1f;
    private float longDy = 0.4f;
    private float longx, longy;
    private Bitmap longBitmap;
    private int longWidth, longHeight;
    private Rect longSrcRect;
    private RectF longDstRect;
    private int longStatus = LONG_WAIT;
    private int longFrameIndex = 0;
    private int delay = 0;
    private int waitSeconds = 300;
    private Paint longPaint;
    private int alpha=30;
    
    private GHostGroup mGHostGroup;

    public MySurfaceView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;

        sfh = this.getHolder();
        sfh.addCallback(this);
        sfh.setType(SurfaceHolder.SURFACE_TYPE_HARDWARE);
        sfh.setFormat(PixelFormat.TRANSPARENT);
        setZOrderOnTop(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int w, int h)
    {
        screenW = w;
        screenH = h;
    }

    public void surfaceCreated(SurfaceHolder arg0)
    {

        p.setAntiAlias(true);
        p.setStrokeWidth(2);
        p.setColor(Color.GREEN);
        p.setStyle(Style.STROKE);
        
        longPaint = new Paint();
        longPaint.setAlpha(alpha);

        longBitmap = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.longs);
        longWidth = longBitmap.getWidth() / 4;
        longHeight = longBitmap.getHeight() / 4;
        longSrcRect = new Rect();
        longDstRect = new RectF();
        longx = -longWidth;
        longy = 250;
        
        mGHostGroup = new GHostGroup(mContext);
        
        flag = true;
        th = new Thread(this);
        th.start();
    }

    public void surfaceDestroyed(SurfaceHolder arg0)
    {
        flag = false;
    }

    public void logic()
    {
        line_y += dy;
        if (line_y > screenH)
        {
            line_y = 0;
        }

        if (longStatus == LONG_WAIT)
        {
            waitSeconds--;
            if (waitSeconds < 0)
            {
                longStatus = LONG_RIGHT;
            }
        }
        else if (longStatus == LONG_LEFT || longStatus == LONG_RIGHT)
        {
            int left = longFrameIndex * longWidth;
            int top = longStatus * longHeight;
            longSrcRect.set(left, top, left + longWidth, top + longHeight);
            delay++;
            if (delay >= 10)
            {
                delay = 0;
                longFrameIndex = (longFrameIndex + 1) % 4;
            }
            float deltaY = 5 * (float) Math.sin(longx);
            longy += deltaY * longDy;
            longDstRect.set(longx, longy, longx + 1.5f * longWidth, longy + 1.5f
                    * longHeight);

            longx += longDx;

            if (longx > screenW + 10)
            {
                longDx = -longDx;
                longStatus = LONG_LEFT;
            }
            else if (longx <= -2 * longWidth)
            {
                longDx = -longDx;
                longStatus = LONG_RIGHT;
            }
            
            if(alpha<=30)
            {
                alpha++;
            }else if(alpha>=200)
            {
                alpha--;
            }
        }
    }

    public void draw()
    {
        try
        {
            canvas = sfh.lockCanvas();
            // clearDraw();
            if (canvas != null)
            {
                canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);

                longPaint.setAlpha(alpha);
                canvas.drawBitmap(longBitmap, longSrcRect, longDstRect, longPaint);
                canvas.drawLine(0, line_y, screenW, line_y, p);
            }
        }
        catch (Exception e)
        {
        }
        finally
        {
            if (canvas != null)
            {
                sfh.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    public void run()
    {
        while (flag)
        {
            long start = System.currentTimeMillis();
            logic();
            draw();
            long end = System.currentTimeMillis();
            try
            {
                if (end - start < 10)
                {
                    Thread.sleep(10 - (end - start));
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }// end while
    }
}// end class
