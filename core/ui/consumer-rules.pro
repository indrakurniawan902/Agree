-keepclassmembers class **.R$* {
       public static <fields>;
}

-keepnames class ** { *; }

# Menjaga nama paket
-keepattributes Signature

# Mencegah obfuscation pada semua data class
-keep class com.agree.ui.data.model.** {
    <fields>;
    <methods>;
}

# Mencegah obfuscation pada semua properti (fields) di dalam data class
-keepclassmembers class com.agree.ui.data.model.** {
    <fields>;
}