#include"com_thinking_a5_encryption_TestTools.h"
#include<android/log.h>

JNIEXPORT void JNICALL Java_com_thinking_a5_1encryption_TestTools_do_1test
(JNIEnv *, jclass){
	__android_log_print(ANDROID_LOG_INFO, "yuyong", "test from ndk");
}