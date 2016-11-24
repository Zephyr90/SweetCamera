
package com.zephyr.sweetcameraapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;

public class SPUtil {
    private static SharedPreferences spUserInfo;

    private static void init(Context context) {
        spUserInfo = context.getSharedPreferences("shared_key", Context.MODE_PRIVATE);
    }

    
    /**
     * 向SharedPreferences文件存入键值对类型的数据
     * 
     * @param name
     * @param value
     */
    public static void putData(Context context, String name, String value) {
        if(StringUtils.isEmpty(name) || StringUtils.isBlank(name)){
            return;
        }
        init(context);
        Log.e("zephyr", "------" + name + ": " + value + "------------");
        spUserInfo.edit().putString(name, value).commit();
    }

    /**
     * 从SharedPreferences文件中取出数据
     * 
     * @param name
     * @return
     */
    public static String getData(Context context, String name) {
        init(context);
        return spUserInfo.getString(name, null);
    }

    /**
     * 从SharedPreferences文件中删除数据
     * 
     * @param context
     * @param name
     */
    public static void deleteData(Context context, String name) {
        init(context);
        spUserInfo.edit().remove(name).commit();
    }

    /**
     * 清空SharedPreference文件中的所有数据
     * 
     * @param context
     */
    public static void clearData(Context context) {
        init(context);
        spUserInfo.edit().clear().commit();
    }

    public static void saveCurrentPositon(Context context, Location location) {
        init(context);
        if (location != null) {
            spUserInfo.edit().putString("posX", location.getLongitude() + "").commit();
            spUserInfo.edit().putString("posY", location.getLatitude() + "").commit();
        }
    }
}
