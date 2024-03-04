#include <jni.h>
#include <string>


extern "C"
JNIEXPORT jstring JNICALL
Java_com_agree_ecosystem_core_utils_utility_UrlManagement_getBaseUrlDev(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("https://dev-api.agreeculture.id/");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_agree_ecosystem_core_utils_utility_UrlManagement_getBaseUrlStaging(
        JNIEnv *env,
        jobject thiz
) {
    return env->NewStringUTF("https://stage-api.agreeculture.id/");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_agree_ecosystem_core_utils_utility_UrlManagement_getBaseUrlProd(
        JNIEnv *env,
        jobject thiz
) {
    return env->NewStringUTF("https://partner-api.agreeculture.id/");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_agree_ecosystem_core_utils_utility_UrlManagement_getAgreepediaBaseUrlDev(
        JNIEnv *env,
        jobject thiz
) {
    return env->NewStringUTF("https://agree-logtan-api-dev.vsan-apps.playcourt.id/agreepedia/");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_agree_ecosystem_core_utils_utility_UrlManagement_getAgreepediaBaseUrlStaging(
        JNIEnv *env,
        jobject thiz
) {
    return env->NewStringUTF("https://stage-homepage-api.agreeculture.id/agreepedia/");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_agree_ecosystem_core_utils_utility_UrlManagement_getAgreepediaBaseUrlProd(
        JNIEnv *env,
        jobject thiz
) {
    return env->NewStringUTF("https://homepage-api.agreeculture.id/agreepedia/");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_agree_ecosystem_core_utils_utility_UrlManagement_getLocaleBaseUrl(
        JNIEnv *env,
        jobject thiz
) {
    return env->NewStringUTF("https://dev-nocodb.agreeculture.id/api/");
}