How To Build jToxcore
====================

We're using CMake as a build system, so you will need CMake in order to build jToxcore.

## Getting the repository ##
This step is always the same: ```git clone https://github.com/Tox/jToxcore```

## Building for your own platform ##
Once you checked out the repository, you can build for your own platform by using

```
cd jToxcore
mkdir build
cd build
cmake ..
make
```
This will create a .jar file in ```build/bin/jToxcore-version.jar ```, and a shared library named libjtoxcore in ```build/jni/libjtoxcore.{so|dll}```. Due to the inner workings of the JVM, you will only be able to use this library on a system with the same combination of OS and Architecture: If you built it on a 32-bit Linux, it will only work in a 32-bit JVM on Linux. If you built it under 64-bit Windows, it will only work in a 64-bit JVM on Windows. The .jar is reusable and can also be used on other platforms.

## Building for other platforms ##
Before you start, you have to compile toxcore for the target platform. Instructions for building tox are in the tox repository. Once completed, you will have to create a <a href="http://www.cmake.org/Wiki/CMake_Cross_Compiling">CMake Toolchain File</a> that fits your architecture.

The following variables need to be set when calling CMake when cross-compiling:
- ```CMAKE_TOOLCHAIN_FILE``` which contains the path to your toolchain file
- ```JNI_H``` this is the path to the directory where the jni.h header is located. This is usually $JAVA_HOME/include.
- ```JNI_MD_H``` or ```NEED_JNI_MD=n``` this is the path to the directory where the jni_md.h header is located. Note that this header is platform specific, and you will have to copy it from the TARGET platform in order to achieve the correct results when compiling. On Android, the platform specific details are defined in the jni.h header, and you will not need a jni_md.h header. If you are compiling for Android, set ```NEED_JNI_MD=n```, and CMake will not look for the header.

An example ```cmake``` command might look like this:

```
cd jToxcore
mkdir build
cd build
cmake .. -DCMAKE_TOOLCHAIN_FILE=~/myWin64Toolchain -DJNI_H=~/include/win64/jni -DJNI_MD_H=~/include/win64/jnimd
```

When CMake completes, you can generate the output files with ```make``` as described above.


## Building the testing client ##
In order to enable the testing client, pass this option to cmake: ```BUILD_TESTCLIENT=y```
