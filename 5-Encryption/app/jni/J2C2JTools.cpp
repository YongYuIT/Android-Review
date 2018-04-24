#include "J2C2JTools.h"


J2C2JTools::J2C2JTools()
{
}


J2C2JTools::~J2C2JTools()
{
}

string J2C2JTools::jstring2string(const jstring& str, JNIEnv* env){
	jboolean is_get_success = false;
	const char* c_str = env->GetStringUTFChars(str, &is_get_success);
	if (!is_get_success)
	{
		return NULL;
	}
	string out_put(c_str);
	env->ReleaseStringUTFChars(str, c_str);
	return out_put;
}