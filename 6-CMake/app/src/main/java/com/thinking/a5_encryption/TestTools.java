package com.thinking.a5_encryption;

/**
 * Created by Yu Yong on 2018/4/20.
 */

public class TestTools {

    static {
        System.loadLibrary("crypto");
        System.loadLibrary("com_thinking_test");
    }

    public native static void do_test();
}

//javah -d E:\Review\20180326002\Android-Review\5-Encryption\app\jni -classpath E:\Review\20180326002\Android-Review\5-Encryption\app\build\intermediates\classes\debug com.thinking.a5_encryption.TestTools