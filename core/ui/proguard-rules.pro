# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keepattributes SourceFile,LineNumberTable        # Keep file names and line numbers.
-keep public class * extends java.lang.Exception  # Optional: Keep custom exceptions.

-printconfiguration
-keep,allowobfuscation interface androidx.annotation.Keep

-keep @androidx.annotation.Keep class *
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}

-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

-keep @kotlinx.android.parcel.Parcelize public class *

-keepattributes *Annotation*

-keepclassmembers class **.R$* {
       public static <fields>;
}

-dontwarn okhttp3.internal.platform.ConscryptPlatform

-keepnames class ** { *; }



-dontwarn com.telkom.legion.component.R$attr
-dontwarn com.telkom.legion.component.R$color
-dontwarn com.telkom.legion.component.R$dimen
-dontwarn com.telkom.legion.component.R$drawable
-dontwarn com.telkom.legion.component.R$layout
-dontwarn com.telkom.legion.component.R$string
-dontwarn com.telkom.legion.component.R$style