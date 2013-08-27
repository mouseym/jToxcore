jToxcore
========

JNI wrapper for Toxcore


Currently, it should be possible to simply import the project into Eclipse. 
The CDT tools for Eclipse are needed, and can be installed via this http://download.eclipse.org/tools/cdt/releases/kepler/ update-site.
This is for the most recent version of Eclipse (Kepler)

The project assumes make is available. I don't know the include paths that will be automatically set up when freshly importing this,
so direct any questions on importing this to https://github.com/sonOfRa. Also sonOfRa on IRC in both #tox and #tox-dev.


## Importing into Eclipse ##
1. Clone the repository.
2. Start Eclipse, and under File -> Import -> General select "Existing Projects into Workspace".
3. Browse to the location of the git repository, and untick "Copy projects into workspace" (If you copy the project into your workspace, all changes will be made there, and not to the repository)
4. When done, right click the Project -> New -> Other -> C/C++ -> "Convert to a C/C++ Project (adds C/C++ nature)"
4.1 Select "Convert to C project"
4.2 Select "Makefile Project"
4.3 Select a toolchain. I use Linux GCC, you might have to pick another, depending on your platform and environment
5. Go to Project -> Properties -> C/C++ General -> Paths and Symbols
5.1 In the "Includes" tab, add ${JAVA_HOME}/include and ${JAVA_HOME}/(linux|win32|whatever) to all languages.
5.2 If tox.h is not on your include path, you have to add it manually, just like you added the above include paths. If you did a 'make install' when building tox, the headers should be in /usr/local/include
6. Hit Shift+F9 to open the "Make"-Menu. Here you add a make target called "JTox.h", which you can now execute after bringing up the Make menu again. This target creates the header files for each designated native method in the Java Code

## General Guidelines ##
1. Do not expose native calls publicly, wrap them with Java function as shown in the addFriend example
2. In public facing Java methods, throw Exceptions instead of returning error codes
3. Group native calls with their corresponding public API method (native method directly above API call)
4. JavaDoc on public methods is mandatory. Adding JavaDoc to non-public methods is highly encouraged.
