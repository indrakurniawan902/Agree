# Menjaga nama paket
-keepattributes Signature

# Mencegah obfuscation pada semua data class
-keep class com.agree.ecosystem.auth.data.reqres.model.** {
    <fields>;
    <methods>;
    public <init>();
}

# Mencegah obfuscation pada semua properti (fields) di dalam data class
-keepclassmembers class com.agree.ecosystem.auth.data.reqres.model.** {
    <fields>;
}