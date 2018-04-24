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
private:
	const static int ENCR_TYPE = 256;
	static char* do_base64_en(const void* mem, const int length);
	static void* do_base64_de(const char* input, int length);
	static void do_en_decy(int type, const string& key, const uint8_t* input, const int in_length, uint8_t* &output, int &output_leng);
};

#endif

