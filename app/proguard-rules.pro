# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /usr/local/Cellar/android-sdk-macosx/tools/proguard/proguard-android.txt
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
-optimizations !code/simplification/cast,!code/allocation/*,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification
-dontpreverify

# The remainder of this file is identical to the non-optimized version
# of the Proguard configuration file (except that the other file has
# flags to turn off optimization).

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# ADDED
-keep class com.google.zxing.client.android.camera.open.**
#-keep class com.google.zxing.client.android.camera.exposure.**
-keep class com.google.zxing.client.android.common.executor.**

# ADDED
-dontobfuscate
-useuniqueclassmembernames

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**

#Retrofit
-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontwarn retrofit2.Platform$Java8
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepclassmembernames interface * {
    @retrofit2.http.* <methods>;
}

#RxJava
-dontwarn rx.**
-keep class rx.** { *; }
-keep interface rx.** { *; }

#picasso
-dontwarn com.squareup.picasso.**

#apache
-dontwarn org.apache.**
-keep class org.apache.** { *; }
-keep interface org.apache.** { *; }


#square
-dontwarn com.square.**
-keep class com.square.** { *; }
-keep interface com.square.** { *; }

#Gson
-dontwarn com.google.gson.**
-keep class com.google.gson.** { *; }
-keep interface com.google.gson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.happytai.elife.model.** { *; }

# Serializable
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    <fields>;
}

# OkHttp
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

# ButterKnife
# Retain generated class which implement Unbinder.
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }

# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinding.
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }

-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# ADDED
-keep class com.google.zxing.client.android.camera.open.**
#-keep class com.google.zxing.client.android.camera.exposure.**
-keep class com.google.zxing.client.android.common.executor.**
