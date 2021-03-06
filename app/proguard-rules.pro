# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/Pachira/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#picasso
-dontwarn com.squareup.okhttp.**


# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }

 -dontwarn okhttp3.**
 -dontwarn okio.**
 -dontwarn javax.annotation.**
 # A resource is loaded with a relative path so the package of this class must be preserved.
 -keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
 -dontwarn okio.**
 -dontwarn javax.annotation.**

# lambda
-dontwarn java.lang.invoke.*
-dontwarn **$$Lambda$*

#autosize
-keep class me.jessyan.autosize.** { *; }
-keep interface me.jessyan.autosize.** { *; }

# snake swipe back
-keepattributes *Annotation*
-keep class **.*_SnakeProxy
-keep @com.youngfeng.snake.annotations.EnableDragToClose public class *