package com.zephyr.sweetcameraapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.zephyr.sweetcameraapp.MainActivity;
import com.zephyr.sweetcameraapp.Utils.FileUtil;
import com.zephyr.sweetcameraapp.interfaces.TakePhotoFinishedListener;
import com.zephyr.sweetcameraapp.manager.CameraManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/11/23 0023.
 */
public class MyCameraPreview extends SurfaceView implements SurfaceHolder.Callback , Camera.PictureCallback{

    private static final String TAG = "MyCameraPreview";

    private SurfaceHolder mHolder;

    /*拍照完成后的回调*/
    public TakePhotoFinishedListener mListener;

    public MyCameraPreview(Context context) {
        super(context);
        mListener = ((TakePhotoFinishedListener) context);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// 摒弃的设置，但是Android3.0之前需要它
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated: ");
        try {
            CameraManager.getCameraIntance();
            CameraManager.initCamera();// 初始化Camera
            CameraManager.doStartPriview(holder); // 开启预览
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在surfaceCreated调用后，至少会被调用一次
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged: ");
        if (mHolder.getSurface() == null) {
            return;
        }
        // 如果surface format或者size发生变化，则需要先停止预览，然后重新设置参数，最后重新开启预览
        CameraManager.doStopPriview();
        // set preview size and make any resize, rotate or
        // reformatting changes here
        CameraManager.doStartPriview(holder);
//        mCamera.cancelAutoFocus();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed: ");
        CameraManager.doStopPriview();
        CameraManager.releaseCamera();
    }

    /**
     * 拍照的回调
     * @param data
     * @param camera
     */
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        /*由于拍摄完成的照片和相机传感器的方向是一致的，既和竖屏有个90度的夹角，所以我们需要将生成的图片旋转90度*/
        Bitmap tempBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Bitmap finalBitmap = null;
        if (tempBitmap == null) return;
        Matrix matrix = new Matrix();
        // 先根据前置或者后置摄像头来确定旋转角度
        if (MainActivity.mCameraIndex == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            matrix.postRotate(270);
        } else {
            matrix.postRotate(90);
        }
        matrix.postScale(0.2f, 0.2f);        // TODO bitmap没有优化,先暂时按固定比例缩放

        finalBitmap = Bitmap.createBitmap(tempBitmap, 0, 0, tempBitmap.getWidth(), tempBitmap.getHeight(), matrix, true);
        File file = FileUtil.getOutputMediaFile(FileUtil.IMAGE_TAG);
        if (file == null) return;
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CameraManager.doStartPriview(mHolder);
        mListener.onFinished(finalBitmap);
        tempBitmap.recycle();
        tempBitmap = null;

//        finalBitmap.recycle();
//        finalBitmap = null;
    }

}
