find_package(Java REQUIRED)

include(UseJava)

set(PACKAGE_NAME im.tox.jtoxcore.test)

set(CLASSDIR "${CMAKE_JAVA_TARGET_OUTPUT_DIR}/CMakeFiles/${TEST_TARGET_NAME}.dir")
set(CLEANFILES_PATH "${CLASSDIR}/im/tox/jtoxcore/test")
set(CLEANFILES
	"${CLEANFILES_PATH}/JToxTest.class"
	"${CLEANFILES_PATH}/TestOnActionCallback.class"
	"${CLEANFILES_PATH}/TestOnConnectionStatusCallback.class"
        "${CLEANFILES_PATH}/TestOnFriendRequestCallback.class"
        "${CLEANFILES_PATH}/TestOnMessageCallback.class"
        "${CLEANFILES_PATH}/TestOnNameChangeCallback.class"
        "${CLEANFILES_PATH}/TestOnReadReceiptCallback.class"
        "${CLEANFILES_PATH}/TestOnStatusMessageCallback.class"
        "${CLEANFILES_PATH}/TestOnUserStatusCallback.class"
)

set(CMAKE_JAVA_INCLUDE_PATH "${PROJECT_BINARY_DIR}/bin/${JAR_TARGET_NAME}-${JTOXCORE_VERSION}.jar")
set(TEST_SOURCE
	im/tox/jtoxcore/test/JToxTest.java
        im/tox/jtoxcore/test/TestOnActionCallback.java
        im/tox/jtoxcore/test/TestOnConnectionStatusCallback.java
        im/tox/jtoxcore/test/TestOnFriendRequestCallback.java
        im/tox/jtoxcore/test/TestOnMessageCallback.java
        im/tox/jtoxcore/test/TestOnNameChangeCallback.java
        im/tox/jtoxcore/test/TestOnReadReceiptCallback.java
        im/tox/jtoxcore/test/TestOnStatusMessageCallback.java
        im/tox/jtoxcore/test/TestOnUserStatusCallback.java
)

set(MANIFEST_INFILE ${CMAKE_CURRENT_SOURCE_DIR}/Manifest.in)
set(MANIFEST_OUTFILE ${CMAKE_CURRENT_BINARY_DIR}/Manifest)

set(CMAKE_JAVA_MANIFEST ${MANIFEST_OUTFILE})
configure_file(${MANIFEST_INFILE} ${MANIFEST_OUTFILE})
add_jar(${TEST_TARGET_NAME} ${TEST_SOURCE})
add_dependencies(${TEST_TARGET_NAME} ${JAR_TARGET_NAME})
