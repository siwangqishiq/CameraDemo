package com.xinlan.camerademo;

import java.util.LinkedList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class GHostGroup
{
    protected Bitmap mBitmap;
    
    protected LinkedList<GHost> list = new LinkedList<GHost>();
    
    public GHostGroup(Context context)
    {
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ghost);
    }
    
    public void logic()
    {
        for(GHost item:list)
        {
            item.logic();
        }//end for
        
    }
    
    public void draw(Canvas canvas)
    {
        for(GHost item:list)
        {
            item.draw(canvas);
        }//end for
        
    }
}//end class
