find_package(Java REQUIRED)
find_package(JNI REQUIRED)

include(UseJava)

# Name for the .jar File
set(TARGET_NAME jToxcore)

# Output directory for .jar file and java .class files
set(CMAKE_JAVA_TARGET_OUTPUT_DIR "${PROJECT_BINARY_DIR}/bin")

# Since class files are apparently not removed by
# 'make clean', create a list and remove them manually
set(CLASSDIR "${CMAKE_JAVA_TARGET_OUTPUT_DIR}/CMakeFiles/${TARGET_NAME}.dir")
set(CLEANFILES
	"${CLASSDIR}/im/tox/jtoxcore/JTox.class"
        "${CLASSDIR}/im/tox/jtoxcore/ToxError.class"
        "${CLASSDIR}/im/tox/jtoxcore/ToxException.class"
        "${CLASSDIR}/im/tox/jtoxcore/ToxUserStatus.class"
        "${CLASSDIR}/im/tox/jtoxcore/ToxWorker.class"
	"${CLASSDIR}/im/tox/jtoxcore/callbacks/OnActionCallback.class"
        "${CLASSDIR}/im/tox/jtoxcore/callbacks/OnConnectionStatusCallback.class"
        "${CLASSDIR}/im/tox/jtoxcore/callbacks/OnMessageCallback.class"
        "${CLASSDIR}/im/tox/jtoxcore/callbacks/OnFriendRequestCallback.class"
        "${CLASSDIR}/im/tox/jtoxcore/callbacks/OnNameChangeCallback.class"
        "${CLASSDIR}/im/tox/jtoxcore/callbacks/OnReadReceiptCallback.class"
        "${CLASSDIR}/im/tox/jtoxcore/callbacks/OnStatusMessageCallback.class"
        "${CLASSDIR}/im/tox/jtoxcore/callbacks/OnUserStatusCallback.class"
        "${CLASSDIR}/im/tox/jtoxcore/callbacks/ToxCallback.class"
)
set_directory_properties(PROPERTIES ADDITIONAL_MAKE_CLEAN_FILES "${CLEANFILES}")

# Core source files 
set(JTOX_CORE
	im/tox/jtoxcore/JTox.java
	im/tox/jtoxcore/ToxError.java
	im/tox/jtoxcore/ToxException.java
	im/tox/jtoxcore/ToxUserStatus.java
	im/tox/jtoxcore/ToxWorker.java
)

# Callback source files
set(JTOX_CALLBACKS
	im/tox/jtoxcore/callbacks/OnActionCallback.java
        im/tox/jtoxcore/callbacks/OnConnectionStatusCallback.java
        im/tox/jtoxcore/callbacks/OnFriendRequestCallback.java
        im/tox/jtoxcore/callbacks/OnMessageCallback.java
        im/tox/jtoxcore/callbacks/OnNameChangeCallback.java
        im/tox/jtoxcore/callbacks/OnReadReceiptCallback.java
        im/tox/jtoxcore/callbacks/OnStatusMessageCallback.java
        im/tox/jtoxcore/callbacks/OnUserStatusCallback.java
        im/tox/jtoxcore/callbacks/ToxCallback.java
)

set(JTOX_SOURCE ${JTOX_CORE} ${JTOX_CALLBACKS})

# .jar target
add_jar(${TARGET_NAME} ${JTOX_SOURCE})

