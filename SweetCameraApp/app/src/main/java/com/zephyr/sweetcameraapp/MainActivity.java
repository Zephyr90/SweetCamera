package com.zephyr.sweetcameraapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zephyr.sweetcameraapp.Utils.AnimationHelper;
import com.zephyr.sweetcameraapp.Utils.SPUtil;
import com.zephyr.sweetcameraapp.Utils.ToastUtil;
import com.zephyr.sweetcameraapp.interfaces.TakePhotoFinishedListener;
import com.zephyr.sweetcameraapp.manager.CameraManager;
import com.zephyr.sweetcameraapp.view.MyCameraPreview;
import com.zephyr.sweetcameraapp.view.SquareImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements TakePhotoFinishedListener{

    @InjectView(R.id.previewContent)
    FrameLayout mPreviewContent;
    @InjectView(R.id.ivBackHome)
    ImageView mIvBackHome;
    @InjectView(R.id.ivSwitchCamera)
    ImageView mIvSwitchCamera;
    @InjectView(R.id.ivShowFunctionBar)
    ImageView mIvShowFunctionBar;
    @InjectView(R.id.ivTouchTakePhoto)
    ImageView mIvTouchTakePhoto;
    @InjectView(R.id.ivSwitchFlashlight)
    ImageView mIvSwitchFlashlight;
    @InjectView(R.id.ivCountDownTakePhoto)
    ImageView mIvCountDownTakePhoto;
    @InjectView(R.id.llSettingSwitcher)
    RelativeLayout mLlSettingSwitcher;
    @InjectView(R.id.ivPreview)
    SquareImageView mIvPreview;
    @InjectView(R.id.ivDecoration)
    ImageView mIvDecoration;
    @InjectView(R.id.ivStart)
    ImageView mIvStart;
    @InjectView(R.id.ivTone)
    ImageView mIvTone;
    @InjectView(R.id.ivSpecialEffects)
    ImageView mIvSpecialEffects;
    @InjectView(R.id.llBottomBar)
    LinearLayout mLlBottomBar;

    private static final String TAG = "MainActivity";
    /*权限申请返回码*/
    private static final int REQUEST_PERMISSION_CAMERA = 101;
    /*前置后置摄像头index*/
    public static int mCameraIndex = Camera.CameraInfo.CAMERA_FACING_BACK;
    /*图像预览SurfaceView*/
    private  MyCameraPreview mPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        initScreen();
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        // 请求权限，获取Camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            CameraManager.getCameraIntance();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
        }
        mPreview = new MyCameraPreview(this);
        mPreviewContent.addView(mPreview);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SPUtil.putData(this, "camera_index", mCameraIndex + "");
    }

    private void initScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @OnClick({R.id.previewContent, R.id.ivBackHome, R.id.ivSwitchCamera, R.id.ivShowFunctionBar,
            R.id.ivTouchTakePhoto, R.id.ivSwitchFlashlight, R.id.ivCountDownTakePhoto, R.id.llSettingSwitcher,
            R.id.ivPreview, R.id.ivDecoration, R.id.ivStart, R.id.ivTone, R.id.ivSpecialEffects, R.id.llBottomBar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.previewContent:
                break;
            case R.id.ivBackHome:
                break;
            case R.id.ivSwitchCamera:
                if (mCameraIndex == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    CameraManager.doSwitchCamera(Camera.CameraInfo.CAMERA_FACING_FRONT, mPreview.getHolder());
                    mCameraIndex = Camera.CameraInfo.CAMERA_FACING_FRONT;
                } else {
                    CameraManager.doSwitchCamera(Camera.CameraInfo.CAMERA_FACING_BACK, mPreview.getHolder());
                    mCameraIndex = Camera.CameraInfo.CAMERA_FACING_BACK;
                }
                break;
            case R.id.ivShowFunctionBar:
                break;
            case R.id.ivTouchTakePhoto:
                break;
            case R.id.ivSwitchFlashlight:
                break;
            case R.id.ivCountDownTakePhoto:
                break;
            case R.id.llSettingSwitcher:
                break;
            case R.id.ivPreview:
                break;
            case R.id.ivDecoration:
                break;
            case R.id.ivStart:
                mIvStart.startAnimation(AnimationHelper.startTakePhotoAnimation(this));
                CameraManager.doTakePicture(mPreview);
                break;
            case R.id.ivTone:
                break;
            case R.id.ivSpecialEffects:
                break;
            case R.id.llBottomBar:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults[0] == 0) {
                ToastUtil.showMessageNormal(this, "授权成功");
                // 开启Camera
                CameraManager.getCameraIntance();
            }
        }
    }

    @Override
    public void onFinished(Bitmap bitmap) {
        // 拍照完成后的回调
        mIvPreview.setImageBitmap(bitmap);
    }
}
