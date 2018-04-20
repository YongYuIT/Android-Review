#include"com_thinking_a5_encryption_TestTools.h"
#include<android\log.h>

#include <openssl/evp.h>  
#include <openssl/bio.h>
#include <openssl/buffer.h>
// base64 ±àÂë
char * base64Encode(const char *buffer, int length, bool newLine)
{
	BIO *bmem = NULL;
	BIO *b64 = NULL;
	BUF_MEM *bptr;

	b64 = BIO_new(BIO_f_base64());
	if (!newLine) {
		BIO_set_flags(b64, BIO_FLAGS_BASE64_NO_NL);
	}
	bmem = BIO_new(BIO_s_mem());
	b64 = BIO_push(b64, bmem);
	BIO_write(b64, buffer, length);
	BIO_flush(b64);
	BIO_get_mem_ptr(b64, &bptr);
	BIO_set_close(b64, BIO_NOCLOSE);

	char *buff = (char *)malloc(bptr->length + 1);
	memcpy(buff, bptr->data, bptr->length);
	buff[bptr->length] = 0;
	BIO_free_all(b64);

	return buff;
}

// base64 ½âÂë
char * base64Decode(const char *input, int length, bool newLine)
{
	BIO *b64 = NULL;
	BIO *bmem = NULL;
	char *buffer = (char *)malloc(length);
	memset(buffer, 0, length);
	b64 = BIO_new(BIO_f_base64());
	if (!newLine) {
		BIO_set_flags(b64, BIO_FLAGS_BASE64_NO_NL);
	}
	bmem = BIO_new_mem_buf(input, length);
	bmem = BIO_push(b64, bmem);
	BIO_read(bmem, buffer, length);
	BIO_free_all(bmem);

	return buffer;
}

#include <string>
using namespace std;

JNIEXPORT void JNICALL Java_com_thinking_a5_1encryption_TestTools_do_1test
(JNIEnv *, jclass){
	__android_log_print(ANDROID_LOG_INFO, "yuyong", "test from ndk");
	string test_in = "fuck you";
	string out = base64Encode(test_in.c_str(), test_in.length(), false);
	__android_log_print(ANDROID_LOG_INFO, "yuyong", out.c_str());
	string out_de = base64Decode(out.c_str(), out.length(), false);
	__android_log_print(ANDROID_LOG_INFO, "yuyong", out_de.c_str());
}