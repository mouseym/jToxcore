#Building for Android#

##Generate a toolchain for Android##
1. Download the Android SDK http://developer.android.com/sdk/index.html. But only get the SDK Tools, not the ADT Bundle.
2. Download the Android NDK for your platform http://developer.android.com/tools/sdk/ndk/index.html. Unpack this, and define ANDROID_NDK_HOME environment variable to point to the Android NDK root folder.
3. Create a toolchain 
```bash
"${ANDROID_NDK_HOME}/build/tools/make-standalone-toolchain.sh" \
--ndk-dir="${ANDROID_NDK_HOME}" \
--toolchain=arm-linux-androideabi-4.6 \ 
--system=linux-x86_64 \ # Change this according to your system
--install-dir=/usr/arm-linux-androideabi \ # Change this to the desired path for the installation directory of the toolchain
--platform=android-9 # This represents the minimum android version this library will be compatible with. 9 is Android 2.3 which still has 20% of the users.
```
##Build libsodium##
Compile libsodium (https://github.com/jedisct1/libsodium/commit/7524a9199e7132d83e47b63331046262c12aba18) or any later version.
```bash
PATH="$PATH:/usr/arm-linux-androideabi/bin"
git clone https://github.com/jedisct1/libsodium
cd libsodium
autoreconf -fi
mkdir build-android && cd build-android
../configure --host="arm-linux-androideabi" \
             --prefix=/usr/arm-linux-androideabi/sysroot/usr \
             --with-sysroot=/usr/arm-linux-androideabi/sysroot \
             --disable-soname-versions
make
make install # su/sudo if needed
```

##Build Toxcore##
Compile Toxcore from the sonOfRa fork: https://github.com/sonOfRa/ProjectTox-Core. Once https://github.com/irungentoo/ProjectTox-Core/pull/751 is merged, compile from the official repository instead. This will build without A/V support for now. I will add instructions for compiling the A/V libraries later
```bash
PATH="$PATH:/usr/arm-linux-androideabi/bin"
git clone https://github.com/sonOfRa/ProjectTox-Core
cd ProjectTox-Core
autoreconf -fi
mkdir build-android && cd build-android
../configure --host="arm-linux-androideabi" \
             --prefix=/usr/arm-linux-androideabi/sysroot/usr \
             --with-sysroot=/usr/arm-linux-androideabi/sysroot \
             --disable-soname-versions \
             --disable-av
make
make install # su/sudo if needed
```

##Build jToxcore##

###CMake Toolchain###
Create a CMake Toolchain file that looks like this
```CMake
SET(CMAKE_SYSTEM_NAME Linux)
SET(CMAKE_C_COMPILER arm-linux-androideabi-gcc)
SET(CMAKE_CXX_COMPILER arm-linux-androideabi-g++)
SET(CMAKE_FIND_ROOT_PATH /usr/arm-linux-androideabi/sysroot)
SET(CMAKE_FIND_ROOT_PATH_MODE_PROGRAM NEVER)
SET(CMAKE_FIND_ROOT_PATH_MODE_LIBRARY ONLY)
SET(CMAKE_FIND_ROOT_PATH_MODE_INCLUDE ONLY)
add_definitions(-DANDROID)
```

###Build###
Get the latest version here: https://github.com/Tox/jToxcore
```bash
PATH="$PATH:/usr/arm-linux-androideabi/bin"
git clone https://github.com/Tox/jToxcore
cd jToxcore
mkdir build-android && cd build-android
cmake .. -DCMAKE_TOOLCHAIN_FILE=/path/to/toolchain/file -DJNI_H=/usr/arm-linux-androideabi/sysroot/usr/include -DNEED_JNI_MD=n
make
```
That's it. The .jar file is in src/jToxcore.jar, the .so file is in jni/libjtoxcore.so
```
