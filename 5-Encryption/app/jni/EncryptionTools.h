#ifndef COM_THINKING_ENCY_TOOLS
#define COM_THINKING_ENCY_TOOLS

#include<string>
using namespace std;
class EncryptionTools
{
public:
	EncryptionTools();
	~EncryptionTools();
	static char* do_ency(const string& key, const string& txt);
	static char* do_decy(const string& key, const string& txt);

	static uint8_t* do_en_de(const int size, const uint8_t* input, const string &key, const int enc);
	const static  string ivec;
private:
	static char* do_base64_en(const void* mem, const int length);
	static void* do_base64_de(const char* input, int length);

};

#endif

