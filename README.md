# UnicodeExifInterface

[![](https://jitpack.io/v/ddyos/UnicodeExifInterface.svg)](https://jitpack.io/#ddyos/UnicodeExifInterface)


## Introduction
In some case, we need write Unicode charset String to Exif's "UserComment" tag, but Android Jetpack's ExifInterface lib only support ASCII charset.

Base on Android Jetpack's ExifInterface lib, this lib support Unicode charset on "UserComment" tag.

Write "UserComment" :
- The string is converted to UTF-16 and saved with the Metadata's Byte Order.

Read "UserComment" :
- For Unicode values, return an UTF-16 string.
- Otherwise, retrun an ASCII String, just like Android Jetpack's ExifInterface lib.

## Setup
1. Add JitPack in your root build.gradle at the end of repositories:
```gradle
allprojects {
    repositories {
      ...
      maven { url 'https://jitpack.io' }
    }
}
```
2. Add this plugin library as a dependency in your app's build.gradle file:
```gradle
dependencies {
    // please use Latest Version
    implementation 'com.github.ddyos:UnicodeExifInterface:1.2.0'
}
```
3. Use. Similar to the Android Jetpack's ExifInterface lib:
```Java
try {
    //write
    UnicodeExifInterface unicodeExifInterface = new UnicodeExifInterface(getPhotoPath());
    unicodeExifInterface.setAttribute(UnicodeExifInterface.TAG_USER_COMMENT, "ýÄÑ123中文Englishにほんご");
    unicodeExifInterface.saveAttributes();
    //read
    UnicodeExifInterface unicodeExifInterface = new UnicodeExifInterface(getPhotoPath());
    String userComment = unicodeExifInterface.getAttribute(UnicodeExifInterface.TAG_USER_COMMENT);
} catch (Exception e) {
    e.printStackTrace();
}
```

## License

Apache License 2.0, as found in the [LICENSE](/LICENSE) file.
