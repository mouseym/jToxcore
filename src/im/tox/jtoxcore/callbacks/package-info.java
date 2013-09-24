/**
 * This package contains the abstract Callback classes needed for the various callbacks
 * supplied by the native tox library.
 * 
 * <p>Some functions have byte arrays as parameters rather than Strings. This is due to
 * the way the JNI code works, and this is unlikely to change. 
 * Use {@link im.tox.jtoxcore.JTox#getByteString(byte[])} to get a Java String from the given argument.
 */
package im.tox.jtoxcore.callbacks;