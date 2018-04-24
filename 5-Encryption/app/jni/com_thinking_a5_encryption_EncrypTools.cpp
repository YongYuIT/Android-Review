#include"com_thinking_a5_encryption_EncrypTools.h"
#include"J2C2JTools.h"
#include"EncryptionTools.h"
#include<android\log.h>
JNIEXPORT jstring JNICALL Java_com_thinking_a5_1encryption_EncrypTools_doEnDeCryp
(JNIEnv * env, jclass j_class, jint en_de, jstring key, jstring tag, jstring txt){
	string c_key = J2C2JTools::jstring2string(key, env);
	string c_tag = J2C2JTools::jstring2string(tag, env);
	string c_txt = J2C2JTools::jstring2string(txt, env);
	char* result;
	if (en_de == 1){
		result = EncryptionTools::do_ency(c_key, c_txt);
	}
	else if (en_de == 2){
		result = EncryptionTools::do_decy(c_key, c_txt);
	}
	jstring out_put = env->NewStringUTF(result);
	free(result);
	result = NULL;
	return out_put;
}
