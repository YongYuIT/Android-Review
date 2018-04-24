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

const string EncryptionTools::ivec = "thisisopensslfor";

uint8_t* EncryptionTools::do_en_de(const int size, const uint8_t* input, const string &key, const int enc){

	string log = key + " " + to_string(size) + " " + to_string(enc) + "----input-->";
	for (int i = 0; i < size; i++){
		log += " " + to_string(input[i]);
	}
	log += "--end";
	__android_log_print(ANDROID_LOG_INFO, "yuyong", log.c_str());

	//准备异或向量
	uint8_t* en_de_iv = (uint8_t*)malloc(size);
	memset(en_de_iv, 0, size);
	int block_num = size / AES_BLOCK_SIZE;
	for (int i = 0; i < block_num; i++){
		memcpy(en_de_iv + i*AES_BLOCK_SIZE, EncryptionTools::ivec.data(), AES_BLOCK_SIZE);
	}
	log = "----iv-->";
	for (int i = 0; i < size; i++){
		log += " " + to_string(en_de_iv[i]);
	}
	log += "--end";
	__android_log_print(ANDROID_LOG_INFO, "yuyong", log.c_str());

	AES_KEY aes_key;
	if (enc == AES_ENCRYPT){
		AES_set_encrypt_key((const uint8_t*)key.data(), 256, &aes_key);
	}
	else{
		AES_set_decrypt_key((const uint8_t*)key.data(), 256, &aes_key);
	}
	uint8_t* result = (uint8_t*)malloc(size);
	memset(result, 0, size);
	AES_cbc_encrypt(input, result, size, &aes_key, en_de_iv, enc);
	free(en_de_iv);
	en_de_iv = NULL;

	log = key + "----output-->";
	for (int i = 0; i < size; i++){
		log += " " + to_string(result[i]);
	}
	log += "--end";
	__android_log_print(ANDROID_LOG_INFO, "yuyong", log.c_str());

	return result;
}


char* EncryptionTools::do_ency(const string& key, const string& txt){
	int input_lg = txt.length() + 1;
	int size = (input_lg / AES_BLOCK_SIZE)*AES_BLOCK_SIZE + (input_lg%AES_BLOCK_SIZE == 0 ? 0 : AES_BLOCK_SIZE);
	uint8_t* input_data = (uint8_t*)malloc(size);
	memset(input_data, 0, size);
	memcpy(input_data, txt.data(), input_lg);
	uint8_t* en_result = do_en_de(size, input_data, key, AES_ENCRYPT);
	char* result = do_base64_en(en_result, size);
	free(input_data);
	input_data = NULL;
	free(en_result);
	en_result = NULL;
	return result;
}
char* EncryptionTools::do_decy(const string& key, const string& txt){

	uint8_t* input_data = (uint8_t*)do_base64_de(txt.c_str(), txt.length());
	int input_lg = txt.length();
	while (input_data[input_lg - 1] == 0)
	{
		input_lg -= 1;
	}
	uint8_t* de_result = do_en_de(input_lg, input_data, key, AES_DECRYPT);
	__android_log_print(ANDROID_LOG_INFO, "yuyong", "do_decy result --> %s", de_result);
	return (char*)de_result;

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