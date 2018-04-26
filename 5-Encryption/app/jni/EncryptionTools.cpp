#include "EncryptionTools.h"
#include <openssl/evp.h>  
#include <openssl/bio.h>
#include <openssl/buffer.h>
#include <openssl/aes.h>
#include "Tools.h"

#include<android\log.h>


EncryptionTools::EncryptionTools()
{
}


EncryptionTools::~EncryptionTools()
{
}

const string EncryptionTools::ivec = "thisisopensslfor";
const string EncryptionTools::id_key = "thisisendekeyforid";
const string EncryptionTools::en_tag = "#$#$#$#$";

uint8_t* EncryptionTools::do_en_de(const int size, const uint8_t* input, const string &key, const int enc){


	string log = key + " " + to_string(size) + " " + to_string(enc) + "----input-->";
	for (int i = 0; i < size; i++){
		log += " " + to_string(input[i]);
	}
	log += "--end";
	__android_log_print(ANDROID_LOG_INFO, "yuyong_en_de", "%s", log.c_str());


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
	__android_log_print(ANDROID_LOG_INFO, "yuyong_en_de", "%s", log.c_str());


	AES_KEY aes_key;
	int gen_key;
	uint8_t * key_cache = (uint8_t *)malloc(256 / 8);
	memset(key_cache, 0, 256 / 8);
	memcpy(key_cache, (const uint8_t*)key.data(), key.length());


	log = "----key cache 32-->";
	for (int i = 0; i < (256 / 8); i++){
		log += " " + to_string(key_cache[i]);
	}
	log += "--end";
	__android_log_print(ANDROID_LOG_INFO, "yuyong_en_de", "%s", log.c_str());



	if (enc == AES_ENCRYPT){
		gen_key = AES_set_encrypt_key(key_cache, 256, &aes_key);
	}
	else{
		gen_key = AES_set_decrypt_key(key_cache, 256, &aes_key);
	}

	__android_log_print(ANDROID_LOG_INFO, "yuyong_en_de", "gen key %s --> %i", key.c_str(), gen_key);

	uint8_t* result = (uint8_t*)malloc(size);
	memset(result, 0, size);
	AES_cbc_encrypt(input, result, size, &aes_key, en_de_iv, enc);
	free(en_de_iv);
	en_de_iv = NULL;
	free(key_cache);
	key_cache = NULL;

	log = key + "----output-->";
	for (int i = 0; i < size; i++){
		log += " " + to_string(result[i]);
	}
	log += "--end";
	__android_log_print(ANDROID_LOG_INFO, "yuyong_en_de", "%s", log.c_str());

	return result;
}

char* EncryptionTools::do_ency(const string& key, const string& tag, const string& txt){
	int tag_lg = tag.length() + 1;
	int tag_size = (tag_lg / AES_BLOCK_SIZE)*AES_BLOCK_SIZE + (tag_lg%AES_BLOCK_SIZE == 0 ? 0 : AES_BLOCK_SIZE);
	__android_log_print(ANDROID_LOG_INFO, "yuyong", "tag_size is %i", tag_size);
	int tag_block_num = tag_size / AES_BLOCK_SIZE;
	string tag_block_num_str = en_tag + to_string(tag_block_num);
	int before_size = tag_size + AES_BLOCK_SIZE;
	uint8_t* before_input_data = (uint8_t*)malloc(before_size);
	memset(before_input_data, 0, before_size);
	memcpy(before_input_data, tag_block_num_str.data(), tag_block_num_str.length() + 1);
	memcpy(before_input_data + AES_BLOCK_SIZE, tag.data(), tag_lg);

	string log = "----intput-->";
	for (int i = 0; i < before_size; i++){
		log += " " + to_string(before_input_data[i]);
	}
	log += "--end";
	__android_log_print(ANDROID_LOG_INFO, "yuyong", "%s", log.c_str());

	uint8_t* before_en_result = do_en_de(before_size, before_input_data, id_key, AES_ENCRYPT);
	free(before_input_data);
	before_input_data = NULL;

	int input_lg = txt.length() + 1;
	int size = (input_lg / AES_BLOCK_SIZE)*AES_BLOCK_SIZE + (input_lg%AES_BLOCK_SIZE == 0 ? 0 : AES_BLOCK_SIZE);
	uint8_t* input_data = (uint8_t*)malloc(size);
	memset(input_data, 0, size);
	memcpy(input_data, txt.data(), input_lg);
	uint8_t* en_result = do_en_de(size, input_data, key, AES_ENCRYPT);
	free(input_data);
	input_data = NULL;

	uint8_t* all_result = (uint8_t*)malloc(before_size + size);
	memcpy(all_result, before_en_result, before_size);
	memcpy(all_result + before_size, en_result, size);
	free(before_en_result);
	before_en_result = NULL;
	free(en_result);
	en_result = NULL;

	char* result = do_base64_en(all_result, before_size + size);
	free(all_result);
	all_result = NULL;
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

char* EncryptionTools::get_id(const string txt){
	uint8_t* input_data = (uint8_t*)do_base64_de(txt.c_str(), txt.length());
	int input_lg = txt.length();
	while (input_data[input_lg - 1] == 0)
	{
		input_lg -= 1;
	}

	string log = to_string(input_lg) + "-->" + to_string(txt.length()) + "----pre input-->";
	for (int i = 0; i < 48; i++){
		log += " " + to_string(input_data[i]);
	}
	log += "--end";
	__android_log_print(ANDROID_LOG_INFO, "yuyong", "%s", log.c_str());

	uint8_t* head_input_data = (uint8_t*)malloc(AES_BLOCK_SIZE);
	memcpy(head_input_data, input_data, AES_BLOCK_SIZE);
	uint8_t* de_head_result = do_en_de(AES_BLOCK_SIZE, head_input_data, id_key, AES_DECRYPT);
	free(head_input_data);
	head_input_data = NULL;
	string de_head_result_str((char*)de_head_result);
	free(de_head_result);
	de_head_result = NULL;
	int index = de_head_result_str.find(en_tag);
	if (index == -1)
		return NULL;
	__android_log_print(ANDROID_LOG_INFO, "yuyong", "size info index is %i", index);
	de_head_result_str = de_head_result_str.substr(index + en_tag.length(), de_head_result_str.length() - 1);
	int tag_size = Tools::str2int(de_head_result_str);
	__android_log_print(ANDROID_LOG_INFO, "yuyong", "size info is %i", tag_size);

	int all_head_size = AES_BLOCK_SIZE*(tag_size + 1);
	uint8_t* all_head_data = (uint8_t*)malloc(all_head_size);
	memcpy(all_head_data, input_data, all_head_size);

	log = "----input-->";
	for (int i = 0; i < all_head_size; i++){
		log += " " + to_string(all_head_data[i]);
	}
	log += "--end";
	__android_log_print(ANDROID_LOG_INFO, "yuyong", "%s", log.c_str());

	uint8_t* de_all_head_result = do_en_de(all_head_size, all_head_data, id_key, AES_DECRYPT);

	log = "----output-->";
	for (int i = 0; i < all_head_size; i++){
		log += " " + to_string(de_all_head_result[i]);
	}
	log += "--end";
	__android_log_print(ANDROID_LOG_INFO, "yuyong", "%s", log.c_str());

	free(all_head_data);
	all_head_data = NULL;

	free(input_data);
	input_data = NULL;
	return (char*)(de_all_head_result + AES_BLOCK_SIZE);
}

char* EncryptionTools::do_decy(const string& key, const string& txt){

	uint8_t* input_data = (uint8_t*)do_base64_de(txt.c_str(), txt.length());
	int input_lg = txt.length();
	while (input_data[input_lg - 1] == 0)
	{
		input_lg -= 1;
	}
	uint8_t* de_result = do_en_de(input_lg, input_data, key, AES_DECRYPT);
	free(input_data);
	input_data = NULL;
	//__android_log_print(ANDROID_LOG_INFO, "yuyong", "do_decy result --> %s", de_result);
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