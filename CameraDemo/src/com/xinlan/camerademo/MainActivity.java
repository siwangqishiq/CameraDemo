package com.xinlan.camerademo;

import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.app.Activity;

public class MainActivity extends Activity implements SurfaceHolder.Callback
{
    public static final int MAX_WIDTH = 200; 
    public static final int MAX_HEIGHT = 200; 
    
    private SurfaceView cameraView;
    private Camera camera; 
    private SurfaceHolder holder;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        cameraView = (SurfaceView)this.findViewById(R.id.surface); 
        cameraView.setFocusable(true);  
        cameraView.setFocusableInTouchMode(true); 
        cameraView.setClickable(true); 
        //SurfaceView中的getHolder方法可以获取到一个SurfaceHolder实例 
        holder = cameraView.getHolder(); 
        //为了实现照片预览功能，需要将SurfaceHolder的类型设置为PUSH 
        //这样，画图缓存就由Camera类来管理，画图缓存是独立于Surface的 
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); 
        holder.addCallback(this);
    }
    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
    {
        
    }
    @Override
    public void surfaceCreated(SurfaceHolder arg0)
    {
        try {
            //打开摄像头
            camera=Camera.open();    
            //旋转90度
            camera.setDisplayOrientation(90);
            
            //摄像头把预览结果交给外观控件SurfaceView的控制者 ，显示在外观控件上
            camera.setPreviewDisplay(cameraView.getHolder());
            //开始预览，抓取预览交给外观控件
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder arg0)
    {
        // TODO Auto-generated method stub
        if(camera!=null){
            camera.stopPreview();
            camera.release();
            camera=null;
        }
    }
}//end class
