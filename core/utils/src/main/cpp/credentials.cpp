//
// Created by oratakashi on 21/12/22.
//
#include <jni.h>
#include <string>


extern "C"
JNIEXPORT jstring JNICALL
Java_com_agree_ecosystem_core_utils_utility_CredentialManagement_getUsernameEngine(
        JNIEnv *env,
        jobject thiz
) {
    return env->NewStringUTF("agree-logtan-users");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_agree_ecosystem_core_utils_utility_CredentialManagement_getPasswordEngine(
        JNIEnv *env,
        jobject thiz
) {
    return env->NewStringUTF("users-password");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_agree_ecosystem_core_utils_utility_CredentialManagement_getAgreepediaToken(
        JNIEnv *env,
        jobject thiz
) {
    return env->NewStringUTF("dGVsa29tbHA6TFBBZ3JlZTIwMjE=");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_agree_ecosystem_core_utils_utility_CredentialManagement_getBasicToken(
        JNIEnv *env,
        jobject thiz
) {
    return env->NewStringUTF("dGVsa29tOmRhMWMyNWQ4LTM3YzgtNDFiMS1hZmUyLTQyZGQ0ODI1YmZlYQ==");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_agree_ecosystem_core_utils_utility_CredentialManagement_getUsernameLocale(
        JNIEnv *env,
        jobject thiz
) {
    return env->NewStringUTF("agree.user@yopmail.com");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_agree_ecosystem_core_utils_utility_CredentialManagement_getPasswordLocale(
        JNIEnv *env,
        jobject thiz
) {
    return env->NewStringUTF("Rt2^&fww#k$97h7#v2Jx#h$7%s&9*B9Y");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_agree_ecosystem_core_utils_utility_CredentialManagement_getLocaleToken(
        JNIEnv *env,
        jobject thiz
) {
    return env->NewStringUTF("fKUm3I6-2W5AIRNDwgiR8Kp9um8GiuFxJ3YMGMJ4");
}