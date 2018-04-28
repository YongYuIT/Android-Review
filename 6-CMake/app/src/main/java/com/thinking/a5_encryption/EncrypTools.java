package com.thinking.a5_encryption;

/**
 * Created by Yu Yong on 2018/4/20.
 */

public class EncrypTools {
    static {
        System.loadLibrary("crypto");
        System.loadLibrary("com_thinking_ende");
    }


    public static native String doEnCryp(String key, String id, String txt);

    public static native String getID(String txt);

    public static native String doDeCryp(String key, String txt);

}
/*
cd app/jni
javah -d . -classpath ..\build\intermediates\classes\debug com.thinking.a5_encryption.EncrypTools
*/