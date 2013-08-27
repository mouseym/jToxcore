# Define a variable for classpath
CLASS_PATH = ../bin

# Package name
PACKAGE_NAME = im.tox.jtoxcore

# Define a virtual path for .class in the bin directory
vpath %.class $(CLASS_PATH)/im/tox/jtoxcore

# $* matches the target filename without the extension
JTox.h : JTox.class
	javah -classpath $(CLASS_PATH) $(PACKAGE_NAME).$*
