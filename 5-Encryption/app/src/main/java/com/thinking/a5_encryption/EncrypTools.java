package com.thinking.a5_encryption;

/**
 * Created by Yu Yong on 2018/4/20.
 */

public class EncrypTools {
    static {
        System.loadLibrary("crypto");
        System.loadLibrary("com_thinking_ende");
    }

    //1:加密；2:解密
    public static native String doEnDeCryp(int en_de, String key, String tag, String txt);

    public static native String getID(String txt);
}
/*
cd app/jni
javah -d . -classpath ..\build\intermediates\classes\debug com.thinking.a5_encryption.EncrypTools
*/