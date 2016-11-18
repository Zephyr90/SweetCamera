package com.zephyr.sweetcameraapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zephyr.sweetcameraapp.Utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback{

    @InjectView(R.id.mSurfaceView)
    SurfaceView mSurfaceView;
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
    ImageView mIvPreview;
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

    private static final int REQUEST_PERMISSION_CAMERA = 101;
    private SurfaceHolder mHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initScreen();
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mHolder = mSurfaceView.getHolder();
    }

    private void initScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @OnClick({R.id.mSurfaceView, R.id.ivBackHome, R.id.ivSwitchCamera, R.id.ivShowFunctionBar,
            R.id.ivTouchTakePhoto, R.id.ivSwitchFlashlight, R.id.ivCountDownTakePhoto, R.id.llSettingSwitcher,
            R.id.ivPreview, R.id.ivDecoration, R.id.ivStart, R.id.ivTone, R.id.ivSpecialEffects, R.id.llBottomBar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mSurfaceView:
                break;
            case R.id.ivBackHome:
                break;
            case R.id.ivSwitchCamera:
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
    public void surfaceCreated(SurfaceHolder holder) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // 初始化Camera
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mHolder = holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults[0] == 0) {
                ToastUtil.showMessageNormal(this, "授权成功");
                // 开始初始化Camera
                initCamera();
            }
        }
    }

    private void initCamera() {
    }
}
