# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# signingConfigs blocks in the build.gradle file.
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
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile

# Room
-keep class androidx.room.** { *; }
-keep @androidx.room.Entity class * { *; }

# Hilt
-keep class dagger.hilt.** { *; }
-keep @dagger.hilt.** class * { *; }
