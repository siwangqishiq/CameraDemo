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
        //SurfaceView�е�getHolder�������Ի�ȡ��һ��SurfaceHolderʵ�� 
        holder = cameraView.getHolder(); 
        //Ϊ��ʵ����ƬԤ�����ܣ���Ҫ��SurfaceHolder����������ΪPUSH 
        //��������ͼ�������Camera������������ͼ�����Ƕ�����Surface�� 
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
            //������ͷ
            camera=Camera.open();    
            //��ת90��
            camera.setDisplayOrientation(90);
            
            //����ͷ��Ԥ�����������ۿؼ�SurfaceView�Ŀ����� ����ʾ����ۿؼ���
            camera.setPreviewDisplay(cameraView.getHolder());
            //��ʼԤ����ץȡԤ��������ۿؼ�
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