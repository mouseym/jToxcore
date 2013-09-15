TODO
========
## Fix the handling of UTF-8 strings ##
Java uses a non-standard UTF-8 encoding. We need to handle byte arrays instead of strings, or create a function that can convert modified UTF-8 to standard UTF-8 and the other way around in our C code. This will take quite a lot of work.

## Core functionality ##
- Wrapper for friend list function
- Wrap group chat functions from core

## API Redesign (see api-rewrite branch)##

We could wrap the friendnumber, status, statusmessage and such in a dedicated ToxFriend.java class, and pass 
an instance of that to each method where the friendnumber is required. This would also allow for some easier 
encapsulation of data for API users, as it gives them a central place to store data for each friend. The 
friends should be pure data objects with only setters and getters, all methods should remain in the core 
class.

This is done, the Friend class now is complete.

### Callback changes ###
Since we replaced "int friendnumber" occurrences with Friend instances in JTox.java, we should also do so in all Callbacks.

- Create an instance method for JTox that returns a ToxFriend from the respective Friend list, (or null).
- Call this method from the C Code to get our ToxFriend instance which we can then return to the callback on the Java side. This will make things a little more consistent
- This method can probably be made private, as it will only be used by native code.
- It could also be done completely on the JNI level, using a plain C function to access the static fields instead.

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
