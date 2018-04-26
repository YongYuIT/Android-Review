#include"com_thinking_a5_encryption_EncrypTools.h"
#include"J2C2JTools.h"
#include"EncryptionTools.h"
#include<android\log.h>
JNIEXPORT jstring JNICALL Java_com_thinking_a5_1encryption_EncrypTools_doEnCryp
(JNIEnv * env, jclass j_class, jstring key, jstring id, jstring txt){
	string c_key = J2C2JTools::jstring2string(key, env);
	string c_id = J2C2JTools::jstring2string(id, env);
	string c_txt = J2C2JTools::jstring2string(txt, env);
	const char* result = EncryptionTools::do_ency(c_key, c_id, c_txt);
	jstring out_put = env->NewStringUTF(result);
	if (env->ExceptionCheck()){
		env->ExceptionClear();
		return NULL;
	}
	else{
		return out_put;
	}
}

JNIEXPORT jstring JNICALL Java_com_thinking_a5_1encryption_EncrypTools_doDeCryp
(JNIEnv *env, jclass j_class, jstring key, jstring txt){
	string c_key = J2C2JTools::jstring2string(key, env);
	string c_txt = J2C2JTools::jstring2string(txt, env);
	const char*  result = EncryptionTools::do_decy(c_key, c_txt, true);
	jstring out_put = env->NewStringUTF(result);
	if (env->ExceptionCheck()){
		env->ExceptionClear();
		return NULL;
	}
	else{
		return out_put;
	}
}

JNIEXPORT jstring JNICALL Java_com_thinking_a5_1encryption_EncrypTools_getID
(JNIEnv * env, jclass j_class, jstring txt){
	string c_txt = J2C2JTools::jstring2string(txt, env);
	const char* result = EncryptionTools::get_id(c_txt);
	jstring out_put = env->NewStringUTF(result);
	if (env->ExceptionCheck()){
		env->ExceptionClear();
		return NULL;
	}
	else{
		return out_put;
	}
}