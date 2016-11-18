package com.zephyr.sweetcameraapp.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/11/18 0018.
 */
public class ToastUtil {
    public static void showMessageNormal(Context context, String content) {
        if (context == null) {
            throw new NullPointerException("context can not be null!");
        }
        if (content == null) {
            throw new RuntimeException("Toast content can not be null!");
        }
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
}
