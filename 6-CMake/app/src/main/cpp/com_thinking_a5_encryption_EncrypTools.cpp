#include <malloc.h>
#include"com_thinking_a5_encryption_EncrypTools.h"
#include"J2C2JTools.h"
#include"EncryptionTools.h"

JNIEXPORT jstring JNICALL
Java_com_thinking_a5_1encryption_EncrypTools_doEnCryp__Ljava_lang_String_2Ljava_lang_String_2
        (JNIEnv *env, jclass j_class, jstring key, jstring txt) {
    string c_key = J2C2JTools::jstring2string(key, env);
    string c_txt = J2C2JTools::jstring2string(txt, env);

    string reslut = EncryptionTools::do_ency_cpp(c_key, c_txt);
    jstring out_put = env->NewStringUTF(reslut.c_str());
    return out_put;
}

JNIEXPORT jstring JNICALL
Java_com_thinking_a5_1encryption_EncrypTools_doEnCryp__Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2

        (JNIEnv *env, jclass j_class, jstring key, jstring id, jstring txt) {
    string c_key = J2C2JTools::jstring2string(key, env);
    string c_id = J2C2JTools::jstring2string(id, env);
    string c_txt = J2C2JTools::jstring2string(txt, env);

    string reslut = EncryptionTools::do_ency_cpp(c_key, c_id, c_txt);
    jstring out_put = env->NewStringUTF(reslut.c_str());
    return out_put;


}


JNIEXPORT jstring JNICALL Java_com_thinking_a5_1encryption_EncrypTools_doDeCryp
        (JNIEnv *env, jclass j_class, jstring key, jstring txt) {
    string c_key = J2C2JTools::jstring2string(key, env);
    string c_txt = J2C2JTools::jstring2string(txt, env);
    char *result = EncryptionTools::do_decy(c_key, c_txt);
    if (result == NULL) {
        return NULL;
    }
    jstring out_put = env->NewStringUTF(result);
    if (env->ExceptionOccurred()) {
        env->ExceptionDescribe();
        env->ExceptionClear();
        return NULL;
    } else {
        free(result);
        result = NULL;
        return out_put;
    }
}

JNIEXPORT jstring JNICALL Java_com_thinking_a5_1encryption_EncrypTools_getID
        (JNIEnv *env, jclass j_class, jstring txt) {
    string c_txt = J2C2JTools::jstring2string(txt, env);
    bool isGetSuccess = false;
    string result = EncryptionTools::get_id(c_txt, isGetSuccess);
    if (isGetSuccess == NULL)
        return NULL;
    jstring out_put = env->NewStringUTF(result.c_str());
    if (env->ExceptionOccurred()) {
        env->ExceptionDescribe();
        env->ExceptionClear();
        return NULL;
    } else {
        return out_put;
    }
}