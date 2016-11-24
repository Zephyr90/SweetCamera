package com.zephyr.sweetcameraapp.Utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/23 0023.
 */
public class FileUtil {
    private static final String TAG = "FileUtil";

    /*image*/
    public static final int IMAGE_TAG = 1;

    /*video*/
    public static final int VIDEO_TAG = 2;


    public static File getOutputMediaFile(int fileType) {
        File file = null;
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "sweetCameraAPP");
        if (!file.exists()) {
            if (!file.mkdir()) {
                Log.d(TAG, "error : fail to create directory");
                return null;
            }
        }

        File media = null;
        String timeStamp = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());

        if (fileType == IMAGE_TAG) {
            //照片
            media = new File(file.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else if (fileType == VIDEO_TAG) {
            media = new File(file.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        } else {
            //视频
            return null;
        }
        return media;
    }
}
