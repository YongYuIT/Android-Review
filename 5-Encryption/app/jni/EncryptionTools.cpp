#include "EncryptionTools.h"
#include <openssl/evp.h>  
#include <openssl/bio.h>
#include <openssl/buffer.h>
#include <openssl/aes.h>
#include<android\log.h>

EncryptionTools::EncryptionTools()
{
}


EncryptionTools::~EncryptionTools()
{
}

void EncryptionTools::do_en_decy(int type, const string& key, const uint8_t* input, const int in_length, uint8_t* &output, int &output_leng){

	int key_length = key.length();
	output_leng = ((in_length / 16) + (in_length % 16 == 0 ? 0 : 1)) * 16;
	int tmp_key_length = ((key_length / 16) + (key_length % 16 == 0 ? 0 : 1)) * 16;
	output = (uint8_t*)malloc(sizeof(uint8_t)*(output_leng + 1));
	memset(output, 0, sizeof(uint8_t)*(output_leng + 1));
	uint8_t* input_tmp = (uint8_t*)malloc(sizeof(uint8_t)*output_leng);
	uint8_t* key_tmp = (uint8_t*)malloc(sizeof(uint8_t)*tmp_key_length);
	memset(input_tmp, 0, sizeof(uint8_t)*output_leng);
	memset(key_tmp, 0, sizeof(uint8_t)*tmp_key_length);
	memcpy(input_tmp, input, in_length);
	memcpy(key_tmp, key.c_str(), key_length);

	AES_KEY aes_key;
	for (int i = 0; i < output_leng / 16; i++){

		if (type == 1)
		{
			AES_set_encrypt_key(key_tmp + 16 * (key_length < 16 ? 0 : (i % (key_length / 16))), AES_BLOCK_SIZE * 16, &aes_key);
			AES_encrypt(input_tmp + 16 * i, output + 16 * i, &aes_key);
		}
		else{
			AES_set_decrypt_key(key_tmp + 16 * (key_length < 16 ? 0 : (i % (key_length / 16))), AES_BLOCK_SIZE * 16, &aes_key);
			AES_decrypt(input_tmp + 16 * i, output + 16 * i, &aes_key);
		}
	}

	free(input_tmp);
	free(key_tmp);
	input_tmp = NULL;
	key_tmp = NULL;
}

char* EncryptionTools::do_ency(const string& key, const string& txt){
	uint8_t* output;
	int output_leng;
	string log = "en txt=";
	for (int i = 0; i < txt.length(); i++){
		log += to_string(((const uint8_t*)txt.c_str())[i]) + " ";
	}
	__android_log_print(ANDROID_LOG_INFO, "yuyong", log.c_str());
	do_en_decy(1, key, (const uint8_t*)txt.c_str(), txt.length(), output, output_leng);

	log = "after aes256 en,output=";
	for (int i = 0; i < output_leng; i++){
		log += std::to_string(output[i]) + " ";
	}
	__android_log_print(ANDROID_LOG_INFO, "yuyong", log.c_str());

	char* result = do_base64_en(output, output_leng);
	free(output);
	output = NULL;
	return result;
}
char* EncryptionTools::do_decy(const string& key, const string& txt){
	uint8_t* input = (uint8_t*)do_base64_de(txt.c_str(), txt.length());
	int input_legh = txt.length();
	while (input[input_legh - 1] == 0)
	{
		input_legh -= 1;
	}

	string log = "before aes256 de,input=";
	for (int i = 0; i < input_legh; i++){
		log += std::to_string(input[i]) + " ";
	}
	__android_log_print(ANDROID_LOG_INFO, "yuyong", log.c_str());

	uint8_t* output;
	int output_leng;
	do_en_decy(2, key, input, input_legh, output, output_leng);

	log = "de txt=";
	for (int i = 0; i < output_leng; i++){
		log += to_string(output[i]) + " ";
	}
	__android_log_print(ANDROID_LOG_INFO, "yuyong", log.c_str());

	free(input);
	input = NULL;
	return (char*)output;
}

void* EncryptionTools::do_base64_de(const char* input, int length){
	BIO *b64 = NULL;
	BIO *bmem = NULL;
	char *buffer = (char *)malloc(length);
	memset(buffer, 0, length);
	b64 = BIO_new(BIO_f_base64());
	BIO_set_flags(b64, BIO_FLAGS_BASE64_NO_NL);
	bmem = BIO_new_mem_buf(input, length);
	bmem = BIO_push(b64, bmem);
	BIO_read(bmem, buffer, length);
	BIO_free_all(bmem);
	return buffer;
}

char* EncryptionTools::do_base64_en(const void* mem, const int length){

	BIO *bmem = NULL;
	BIO *b64 = NULL;
	BUF_MEM *bptr;

	b64 = BIO_new(BIO_f_base64());
	BIO_set_flags(b64, BIO_FLAGS_BASE64_NO_NL);

	bmem = BIO_new(BIO_s_mem());
	b64 = BIO_push(b64, bmem);
	BIO_write(b64, mem, length);
	BIO_flush(b64);
	BIO_get_mem_ptr(b64, &bptr);
	BIO_set_close(b64, BIO_NOCLOSE);

	char *buff = (char *)malloc(bptr->length + 1);
	memcpy(buff, bptr->data, bptr->length);
	buff[bptr->length] = 0;
	BIO_free_all(b64);

	return buff;
}