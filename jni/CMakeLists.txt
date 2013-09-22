find_package(JNI REQUIRED)
find_package(libtoxcore REQUIRED)

include_directories(
	"${JNI_HEADER_LOCATION}"
	"${JAVA_INCLUDE_PATH}"
	"${JAVA_INCLUDE_PATH2}"
	"${libtoxcore_INCLUDE_DIRS}"
)

add_library(
	${LIB_TARGET_NAME}
	SHARED
	callbacks.h
	JTox.c
)

target_link_libraries(
	${LIB_TARGET_NAME}
	${libtoxcore_LIBRARIES}
)

add_dependencies(${LIB_TARGET_NAME} ${JAR_TARGET_NAME})
