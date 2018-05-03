#include "EncryptionTools.h"
#include <openssl/evp.h>
#include <openssl/bio.h>
#include <openssl/buffer.h>
#include <openssl/aes.h>
#include "Tools.h"

EncryptionTools::EncryptionTools() {
}


EncryptionTools::~EncryptionTools() {
}

const string EncryptionTools::ivec = "thisisopensslfor";
const string EncryptionTools::id_key = "thisisendekeyforid";
const string EncryptionTools::en_tag = "#$#$#$#$";

uint8_t *
EncryptionTools::do_en_de(const int size, const uint8_t *input, const string &key, const int enc) {

    //准备异或向量
    uint8_t *en_de_iv = (uint8_t *) malloc(size);
    memset(en_de_iv, 0, size);
    int block_num = size / AES_BLOCK_SIZE;
    for (int i = 0; i < block_num; i++) {
        memcpy(en_de_iv + i * AES_BLOCK_SIZE, EncryptionTools::ivec.data(), AES_BLOCK_SIZE);
    }

    AES_KEY aes_key;
    int gen_key;
    uint8_t *key_cache = (uint8_t *) malloc(256 / 8);
    memset(key_cache, 0, 256 / 8);
    memcpy(key_cache, (const uint8_t *) key.data(), key.length());

    if (enc == AES_ENCRYPT) {
        gen_key = AES_set_encrypt_key(key_cache, 256, &aes_key);
    } else {
        gen_key = AES_set_decrypt_key(key_cache, 256, &aes_key);
    }

    uint8_t *result = (uint8_t *) malloc(size);
    memset(result, 0, size);
    AES_cbc_encrypt(input, result, size, &aes_key, en_de_iv, enc);
    free(en_de_iv);
    en_de_iv = NULL;
    free(key_cache);
    key_cache = NULL;

    return result;
}

string EncryptionTools::do_ency_cpp(const string &key, const string &tag, const string &txt) {
    int tag_lg = tag.length() + 1;
    int tag_size = (tag_lg / AES_BLOCK_SIZE) * AES_BLOCK_SIZE +
                   (tag_lg % AES_BLOCK_SIZE == 0 ? 0 : AES_BLOCK_SIZE);
    int tag_block_num = tag_size / AES_BLOCK_SIZE;
    string tag_block_num_str = en_tag + Tools::to_string(tag_block_num);
    int before_size = tag_size + AES_BLOCK_SIZE;
    uint8_t *before_input_data = (uint8_t *) malloc(before_size);
    memset(before_input_data, 0, before_size);
    memcpy(before_input_data, tag_block_num_str.data(), tag_block_num_str.length() + 1);
    memcpy(before_input_data + AES_BLOCK_SIZE, tag.data(), tag_lg);

    uint8_t *before_en_result = do_en_de(before_size, before_input_data, id_key, AES_ENCRYPT);
    free(before_input_data);
    before_input_data = NULL;

    int input_lg = txt.length() + 1;
    int size = (input_lg / AES_BLOCK_SIZE) * AES_BLOCK_SIZE +
               (input_lg % AES_BLOCK_SIZE == 0 ? 0 : AES_BLOCK_SIZE);
    uint8_t *input_data = (uint8_t *) malloc(size);
    memset(input_data, 0, size);
    memcpy(input_data, txt.data(), input_lg);
    uint8_t *en_result = do_en_de(size, input_data, key, AES_ENCRYPT);
    free(input_data);
    input_data = NULL;

    uint8_t *all_result = (uint8_t *) malloc(before_size + size);
    memcpy(all_result, before_en_result, before_size);
    memcpy(all_result + before_size, en_result, size);
    free(before_en_result);
    before_en_result = NULL;
    free(en_result);
    en_result = NULL;

    string result = do_base64_en_cpp(all_result, before_size + size);
    free(all_result);
    all_result = NULL;
    return result;
}

char *EncryptionTools::do_ency(const string &key, const string &tag, const string &txt) {
    int tag_lg = tag.length() + 1;
    int tag_size = (tag_lg / AES_BLOCK_SIZE) * AES_BLOCK_SIZE +
                   (tag_lg % AES_BLOCK_SIZE == 0 ? 0 : AES_BLOCK_SIZE);
    int tag_block_num = tag_size / AES_BLOCK_SIZE;
    string tag_block_num_str = en_tag + Tools::to_string(tag_block_num);
    int before_size = tag_size + AES_BLOCK_SIZE;
    uint8_t *before_input_data = (uint8_t *) malloc(before_size);
    memset(before_input_data, 0, before_size);
    memcpy(before_input_data, tag_block_num_str.data(), tag_block_num_str.length() + 1);
    memcpy(before_input_data + AES_BLOCK_SIZE, tag.data(), tag_lg);

    uint8_t *before_en_result = do_en_de(before_size, before_input_data, id_key, AES_ENCRYPT);
    free(before_input_data);
    before_input_data = NULL;

    int input_lg = txt.length() + 1;
    int size = (input_lg / AES_BLOCK_SIZE) * AES_BLOCK_SIZE +
               (input_lg % AES_BLOCK_SIZE == 0 ? 0 : AES_BLOCK_SIZE);
    uint8_t *input_data = (uint8_t *) malloc(size);
    memset(input_data, 0, size);
    memcpy(input_data, txt.data(), input_lg);
    uint8_t *en_result = do_en_de(size, input_data, key, AES_ENCRYPT);
    free(input_data);
    input_data = NULL;

    uint8_t *all_result = (uint8_t *) malloc(before_size + size);
    memcpy(all_result, before_en_result, before_size);
    memcpy(all_result + before_size, en_result, size);
    free(before_en_result);
    before_en_result = NULL;
    free(en_result);
    en_result = NULL;

    char *result = do_base64_en(all_result, before_size + size);
    free(all_result);
    all_result = NULL;
    return result;
}


char *EncryptionTools::do_ency(const string &key, const string &txt) {
    int input_lg = txt.length() + 1;
    int size = (input_lg / AES_BLOCK_SIZE) * AES_BLOCK_SIZE +
               (input_lg % AES_BLOCK_SIZE == 0 ? 0 : AES_BLOCK_SIZE);
    uint8_t *input_data = (uint8_t *) malloc(size);
    memset(input_data, 0, size);
    memcpy(input_data, txt.data(), input_lg);
    uint8_t *en_result = do_en_de(size, input_data, key, AES_ENCRYPT);
    char *result = do_base64_en(en_result, size);
    free(input_data);
    input_data = NULL;
    free(en_result);
    en_result = NULL;
    return result;
}

int EncryptionTools::get_id_size(const string &txt) {
    //uint8_t *input_data = (uint8_t *) do_base64_de(txt.c_str(), txt.length());
    int input_data_size;
    uint8_t *input_data = (uint8_t *) do_base64_de_cpp(txt.substr(0, 32), input_data_size);
    uint8_t *head_input_data = (uint8_t *) malloc(AES_BLOCK_SIZE);
    memcpy(head_input_data, input_data, AES_BLOCK_SIZE);
    free(input_data);
    input_data = NULL;
    uint8_t *de_head_result = do_en_de(AES_BLOCK_SIZE, head_input_data, id_key, AES_DECRYPT);
    free(head_input_data);
    head_input_data = NULL;
    string de_head_result_str((char *) de_head_result);
    free(de_head_result);
    de_head_result = NULL;
    int index = de_head_result_str.find(en_tag);
    if (index == -1)
        return -1;
    de_head_result_str = de_head_result_str.substr(index + en_tag.length(),
                                                   de_head_result_str.length() - 1);
    return Tools::str2int(de_head_result_str);
}

string EncryptionTools::get_id(const string txt) {
    int tag_size = get_id_size(txt);
    //uint8_t *input_data = (uint8_t *) do_base64_de(txt.c_str(), txt.length());
    int input_data_size;
    uint8_t *input_data = (uint8_t *) do_base64_de_cpp(txt, input_data_size);

    int all_head_size = AES_BLOCK_SIZE * (tag_size + 1);
    uint8_t *de_all_head_result = do_en_de(all_head_size, input_data, id_key, AES_DECRYPT);

    free(input_data);
    input_data = NULL;

    string de_all_head_result_str((char *) (de_all_head_result + AES_BLOCK_SIZE));
    free(de_all_head_result);
    de_all_head_result = NULL;

    return de_all_head_result_str;
}

char *EncryptionTools::do_decy(const string &key, const string &txt) {

    int input_lg;
    uint8_t *input_data = (uint8_t *) do_base64_de_cpp(txt.c_str(), input_lg);

    uint8_t *txt_input_data = input_data;
    int tag_size = get_id_size(txt);
    if (tag_size > 0) {
        tag_size = (tag_size + 1) * AES_BLOCK_SIZE;
        txt_input_data = input_data + tag_size;
        input_lg = input_lg - tag_size;
    }

    uint8_t *de_result = do_en_de(input_lg, txt_input_data, key, AES_DECRYPT);
    free(input_data);
    input_data = NULL;
    return (char *) de_result;

}

void *EncryptionTools::do_base64_de(const char *input, int length) {
    BIO *b64 = NULL;
    BIO *bmem = NULL;
    char *buffer = (char *) malloc(length);
    memset(buffer, 0, length);
    b64 = BIO_new(BIO_f_base64());
    BIO_set_flags(b64, BIO_FLAGS_BASE64_NO_NL);
    bmem = BIO_new_mem_buf(input, length);
    bmem = BIO_push(b64, bmem);
    BIO_read(bmem, buffer, length);
    BIO_free_all(bmem);
    return buffer;
}

string EncryptionTools::do_base64_en_cpp(const void *mem, int size) {
    uint8_t *out_cache = (uint8_t *) malloc(size + 1);
    memset(out_cache, 0, size + 1);
    int result = EVP_EncodeBlock(out_cache, (const uint8_t *) mem, size);
    string str_result((char *) out_cache, result);
    free(out_cache);
    out_cache = NULL;
    if (result != -1)
        return str_result;
    return NULL;
}

void *EncryptionTools::do_base64_de_cpp(string txt, int &outsize) {
    uint8_t *out_cache = (uint8_t *) malloc(txt.length());
    outsize = EVP_DecodeBlock(out_cache, (const uint8_t *) txt.data(), txt.length());
    if (outsize != -1) {
        return out_cache;
    }
    return NULL;
}

char *EncryptionTools::do_base64_en(const void *mem, const int length) {

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

    char *buff = (char *) malloc(bptr->length + 1);
    memcpy(buff, bptr->data, bptr->length);
    buff[bptr->length] = 0;
    BIO_free_all(b64);

    return buff;
}