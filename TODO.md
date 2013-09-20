TODO
========
## Fix the handling of UTF-8 strings ##
Java uses a non-standard UTF-8 encoding. We need to handle byte arrays instead of strings, or create a function that can convert modified UTF-8 to standard UTF-8 and the other way around in our C code. This will take quite a lot of work.

## Core functionality ##
- Wrapper for friend list function
- Wrap group chat functions from core

## Testing ##
- Manual testing, maybe a reference client like toxic
- Unit testing for everything that does not rely heavily on connection state. If we find connections to 
establish fast locally, we should also add unit tests for messaging and all the callbacks to ensure that our 
code is working properly

## Documentation ##
- Some JavaDoc might need improvement
- The C Code needs documentation as well

## Build System ##
- We need a build system to automate our builds. There are some possibilities:
- Maven
- Ant
- CMake
- I would personally prefer Maven, however I have no REAL experience on using Maven with JNI, especially when 
we need multiple build environments (Windows, Linux, OSX), as well as multiple ABI-Targets. Since a 32-bit 
JVM cannot load 64-bit libraries and vice-versa, we need both 32-bit and 64-bit for Linux, Windows and OSX, 
as well as 32-bit for Android (there are no 64 build for Android YET, but this might change in the future)
