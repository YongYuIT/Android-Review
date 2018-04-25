#include "Tools.h"
#include<sstream>

Tools::Tools()
{
}


Tools::~Tools()
{
}


int Tools::str2int(const string & str_value){
	stringstream stream(str_value);
	int result;
	stream >> result;
	return result;
}