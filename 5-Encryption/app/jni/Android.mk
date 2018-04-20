LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := openssl_cyp
LOCAL_SRC_FILES := E:\Review\20180326002\Android-Review\5-Encryption\android-arm\lib\libcrypto.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := com_thinking_encryp
LOCAL_SRC_FILES := test.cpp
LOCAL_C_INCLUDES += E:\Review\20180326002\Android-Review\5-Encryption\android-arm\include
LOCAL_LDLIBS +=  -llog -ldl
LOCAL_SHARED_LIBRARIES := openssl_cyp
include $(BUILD_SHARED_LIBRARY)
