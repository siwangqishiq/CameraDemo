package com.xinlan.camerademo;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class GHost
{
    private float dx, dy;
    public int status;
    private GHostGroup group;
    private Rect src;
    private RectF dst;
    private Bitmap bit;
    private Paint paint;
    public float x, y, width, height;
    private int alpa = 100;
    public float scale;
    private static Random rand = new Random();

    public GHost(GHostGroup group)
    {
        this.group = group;
        bit = group.mBitmap;
        src = new Rect(0, 0, bit.getWidth(), bit.getHeight());
        dst = new RectF(x, y, x + width, y + height);
        paint = new Paint();
    }

    public void logic()
    {
        width = scale*bit.getWidth();
        height  = scale*bit.getHeight();
        dst.set(x, y, x + width, y + height);
        if (alpa <= 70)
        {
            alpa++;
        }
        else if (alpa > 230)
        {
            alpa--;
        }
        paint.setAlpha(alpa);
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(bit, src, dst, paint);
    }
    
    public static GHost genGHost(GHostGroup group,float init_x,float init_y)
    {
        GHost ghost = new GHost(group);
        ghost.x = init_x;
        ghost.y = init_y;
        ghost.scale = 0.5f+1.5f*rand.nextFloat();
        
        return ghost;
    }
}//end class
