package com.thinking.a5_encryption;

import android.util.Log;

/**
 * Created by Yu Yong on 2018/4/20.
 */

public class EncrypTools {
    static {
        //adb shell
        //cat /proc/cpuinfo
        String CPU_ABI = android.os.Build.CPU_ABI;
        Log.i("yuyong", "CPU_ABI = " + CPU_ABI);
        System.loadLibrary("crypto");
        System.loadLibrary("com_thinking_ende");
        //System.load("/data/app/com.thinking.a6_cmake/lib/libcrypto.so");
        //System.load("/data/app/com.thinking.a6_cmake/lib/libcom_thinking_ende.so");
    }


    public static native String doEnCryp(String key, String txt);

    public static native String doEnCryp(String key, String id, String txt);

    public static native String getID(String txt);

    public static native String doDeCryp(String key, String txt);

}
/*
cd app\src\main\cpp
javah -d . -classpath ..\..\..\build\intermediates\classes\debug com.thinking.a5_encryption.EncrypTools
*/