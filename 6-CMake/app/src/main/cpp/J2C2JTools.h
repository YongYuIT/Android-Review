#ifndef COM_THINKING_J2CTOOLS
#define COM_THINKING_J2CTOOLS

#include<jni.h>
#include<string>
using namespace std;

class J2C2JTools
{
public:
	J2C2JTools();
	~J2C2JTools();
	static string  jstring2string(const jstring & str, JNIEnv* env);
	static jstring string2jstring(const string & input);
};

#endif

