LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
$(warning "######################$(TARGET_ARCH_ABI)")

include $(CLEAR_VARS)
LOCAL_MODULE := openssl_cyp
LOCAL_SRC_FILES := E:\Review\openssl\android-$(TARGET_ARCH_ABI)\lib\libcrypto.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := com_thinking_test
LOCAL_SRC_FILES := test.cpp
LOCAL_C_INCLUDES += E:\Review\openssl\android-$(TARGET_ARCH_ABI)\include
LOCAL_LDLIBS +=  -llog -ldl
LOCAL_SHARED_LIBRARIES := openssl_cyp
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := com_thinking_ende
LOCAL_SRC_FILES := com_thinking_a5_encryption_EncrypTools.cpp \
    J2C2JTools.cpp \
    EncryptionTools.cpp
LOCAL_C_INCLUDES += E:\Review\openssl\android-$(TARGET_ARCH_ABI)\include
LOCAL_LDLIBS +=  -llog -ldl
LOCAL_SHARED_LIBRARIES := openssl_cyp
include $(BUILD_SHARED_LIBRARY)
