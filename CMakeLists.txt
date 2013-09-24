cmake_minimum_required (VERSION 2.6)
project (jToxcore)

set(CMAKE_MODULE_PATH "${CMAKE_SOURCE_DIR}/cmake/Modules" ${CMAKE_MODULE_PATH})
# Version
set(JTOXCORE_VERSION 0.3.0)

# JNI Header information
set(JNI_HEADER_LOCATION "${CMAKE_BINARY_DIR}/header")
set(JNI_HEADER_NAME "JTox.h")

# Target name for .jar
set(JAR_TARGET_NAME jToxcore)

# Target name for shared library
set(LIB_TARGET_NAME jtoxcore)

# Target name for test 
# Subdir for Java code
add_subdirectory (src)
add_subdirectory (jni)

if(DEFINED BUILD_TESTCLIENT)
	if(${BUILD_TESTCLIENT} STREQUAL "y")
		set(TEST_TARGET_NAME jToxtest)
		add_subdirectory (test)
	endif()
endif()
