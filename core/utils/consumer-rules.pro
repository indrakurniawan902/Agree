-keepclassmembers class **.R$* {
       public static <fields>;
}

-keepnames class ** { *; }
-keep class java.time.** { *; }

-keep,includedescriptorclasses class net.sqlcipher.** { *; }
-keep,includedescriptorclasses interface net.sqlcipher.** { *; }

# Mencegah obfuscation pada semua data class
-keep class com.agree.ecosystem.core.utils.data.model.** {
    <fields>;
    <methods>;
}

# Mencegah obfuscation pada semua properti (fields) di dalam data class
-keepclassmembers class com.agree.ecosystem.core.utils.data.model.** {
    <fields>;
}