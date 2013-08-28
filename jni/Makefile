# Define a variable for classpath
CLASS_PATH = ../bin

GCC_INCLUDE = -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux
# Package name
PACKAGE_NAME = im.tox.jtoxcore

all : libjtox.so

libjtox.so : JTox.o
	gcc -shared -o $@ $< -ltoxcore -lsodium

JTox.o : JTox.c JTox.h
	LANG="en_US.UTF-8" gcc $(GCC_INCLUDE) -fPIC -ggdb -c $< -o $@

# Define a virtual path for .class in the bin directory
vpath %.class $(CLASS_PATH)/im/tox/jtoxcore

# $* matches the target filename without the extension
JTox.h : JTox.class
	javah -classpath $(CLASS_PATH) $(PACKAGE_NAME).$*

clean :
	rm -f libjtox.so JTox.o im_tox_jtoxcore_JTox.h