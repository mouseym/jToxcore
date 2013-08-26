1. To compile Toxcore JNI first setup the Android NDK

2. Afterwards follow the standard INSTALL.md

3. When you get to ./configure append the following flags:
./configure --build=x86_64-unknown-linux-gnu --host=arm-linux-androideabi --target=--target=arm-linux-androideabi --with-sysroot=/tmp/my-android-toolchain/sysroot --with-libsodium-headers=/tmp/include --with-libsodium-libs=/tmp/lib --prefix=/tmp/tox4android/ --disable-tests --disable-dht-bootstrap-daemon

4. Chage the folders based on the system.

5. Afterwards run make, without make install. The Android.mk looks in the default build folder
