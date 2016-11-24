package com.zephyr.sweetcameraapp.manager;

import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/11/23 0023.
 */
public class CameraManager {

    private static final String TAG = "CameraManager";
    private static Camera mCamera;
    public static synchronized Camera getCameraIntance() {
        if (mCamera == null) {
            try {
                mCamera = Camera.open();
            } catch (Exception e){
                // Camera is not available (in use or does not exist)
                Log.d(TAG, "getCameraIntance: Camera is not available (in use or does not exist)" );
            }
        }
        return mCamera;
    }


    /**
     * 初始化Camera
     */
    public static void initCamera() {
        mCamera.setDisplayOrientation(90);
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
        for (Camera.Size size : sizes) {
            Log.d(TAG, "initCamera: " + size.height + " , " + size.width);
        }
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        parameters.setPictureFormat(PixelFormat.JPEG);
        parameters.setPreviewSize(1280, 720);
        parameters.setPictureSize(1280, 720);
        mCamera.setParameters(parameters);
    }

    /**
     * 开启预览
     * @param holder
     */
    public static void doStartPriview(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止预览
     */
    public static void doStopPriview() {
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "error:" + e.getMessage());
        }
    }

    /**
     * 拍照
     * @param callback
     */
    public static void doTakePicture(Camera.PictureCallback callback) {
        mCamera.takePicture(null, null, callback);
    }

    /**
     * 切换前后摄像头
     * @param number
     */
    public static void doSwitchCamera(int number, SurfaceHolder holder) {
        int cameras = Camera.getNumberOfCameras();
        if (cameras == 1) return;
        doStopPriview();
        releaseCamera();
        mCamera = Camera.open(number);
        initCamera();
        doStartPriview(holder);
    }

    /**
     * 释放Camera资源
     */
    public static void releaseCamera(){
        if (mCamera != null) {
            Log.d(TAG, "Camera2: " + mCamera);
            mCamera.release();
            mCamera = null;
        }
    }

}
