package com.zephyr.sweetcameraapp.Utils;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.zephyr.sweetcameraapp.R;

/**
 * Created by Administrator on 2016/11/24 0024.
 */
public class AnimationHelper {

    public static Animation startTakePhotoAnimation(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.anim_take_photo);
    }
}
