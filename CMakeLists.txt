cmake_minimum_required (VERSION 2.6)
project (jToxcore)

# Version
set(JTOXCORE_VERSION 0.2.0)

# JNI Header information
set(JNI_HEADER_LOCATION "${CMAKE_BINARY_DIR}/header")
set(JNI_HEADER_NAME "JTox.h")

# Target name for .jar
set(JAR_TARGET_NAME jToxcore)

# Subdir for Java code
add_subdirectory (src)

