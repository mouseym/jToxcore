/* im_tox_jtoxcore_JTox.c
 *
 *  Copyright (C) 2013 Tox project All Rights Reserved.
 *
 *  This file is part of jToxcore
 *
 *  jToxcore is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  jToxcore is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with jToxcore.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>
#include <tox/tox.h>

#include "im_tox_jtoxcore_JTox.h"
#include "callbacks.h"

#define ADDR_SIZE_HEX (TOX_FRIEND_ADDRESS_SIZE * 2 + 1)

/**
 * Convert a given binary address to a human-readable hexadecimal string
 */
void addr_to_hex(uint8_t *addr, char *buf) {
	int i;

	for (i = 0; i < TOX_FRIEND_ADDRESS_SIZE; i++) {
		char xx[3];
		snprintf(xx, sizeof(xx), "%02X", addr[i] & 0xff);
		strcat(buf, xx);
	}
}

/**
 * Convert a given human-readable hexadecimal string into binary
 */
void hex_to_addr(const char *hex, uint8_t *buf) {
	size_t len = strlen(hex);
	uint8_t *val = malloc(len);
	char *pos = malloc(strlen(hex) + 1);
	pos = strdup(hex);
	int i;

	for (i = 0; i < len; ++i, pos += 2)
		sscanf(pos, "%2hhx", &buf[i]);
}

JNIEXPORT jlong JNICALL Java_im_tox_jtoxcore_JTox_tox_1new(JNIEnv * env,
		jclass clazz) {
	return ((jlong) tox_new());
}

JNIEXPORT jint JNICALL Java_im_tox_jtoxcore_JTox_tox_1addfriend(JNIEnv * env,
		jobject obj, jlong messenger, jstring address, jstring data) {
	const uint8_t *_address = (*env)->GetStringUTFChars(env, address, 0);
	const uint8_t *_data = (*env)->GetStringUTFChars(env, data, 0);

	uint8_t __address[TOX_FRIEND_ADDRESS_SIZE];
	hex_to_addr(_address, __address);
	uint8_t *__data = malloc(strlen(_data) + 1);

	jsize length = (*env)->GetStringUTFLength(env, data);

	int errcode = tox_addfriend((Tox *) messenger, __address, __data, length);

	free(__data);
	(*env)->ReleaseStringUTFChars(env, address, _address);
	(*env)->ReleaseStringUTFChars(env, data, _data);

	return errcode;
}

JNIEXPORT jint JNICALL Java_im_tox_jtoxcore_JTox_tox_1addfriend_1norequest(
		JNIEnv * env, jobject obj, jlong messenger, jstring address) {
	const char *_address = (*env)->GetStringUTFChars(env, address, 0);
	uint8_t __address[TOX_FRIEND_ADDRESS_SIZE];
	hex_to_addr(_address, __address);

	int errcode = tox_addfriend_norequest((Tox *) messenger, __address);

	(*env)->ReleaseStringUTFChars(env, address, _address);

	return errcode;
}

JNIEXPORT jstring JNICALL Java_im_tox_jtoxcore_JTox_tox_1getaddress(
		JNIEnv * env, jobject obj, jlong messenger) {
	uint8_t addr[TOX_FRIEND_ADDRESS_SIZE];
	tox_getaddress((Tox *) messenger, addr);
	char id[ADDR_SIZE_HEX] = { 0 };
	addr_to_hex(addr, id);

	jstring result = (*env)->NewStringUTF(env, id);
	return result;
}

JNIEXPORT jint JNICALL Java_im_tox_jtoxcore_JTox_tox_1getfriend_1id(
		JNIEnv * env, jobject obj, jlong messenger, jstring address) {
	const uint8_t *_address = (*env)->GetStringUTFChars(env, address, 0);

	uint8_t __address[TOX_FRIEND_ADDRESS_SIZE];
	hex_to_addr(_address, __address);

	int errcode = tox_getfriend_id((Tox *) messenger, __address);

	(*env)->ReleaseStringUTFChars(env, address, _address);

	return errcode;
}

JNIEXPORT jstring JNICALL Java_im_tox_jtoxcore_JTox_tox_1getclient_1id(
		JNIEnv * env, jobject obj, jlong messenger, jint friendnumber) {
	uint8_t address[TOX_FRIEND_ADDRESS_SIZE];

	if (tox_getclient_id((Tox *) messenger, friendnumber, address) == -1) {
		return 0;
	} else {
		char _address[ADDR_SIZE_HEX] = { 0 };
		addr_to_hex(address, _address);
		jstring result = (*env)->NewStringUTF(env, _address);
		return result;
	}
}
JNIEXPORT void JNICALL Java_im_tox_jtoxcore_JTox_tox_1do(JNIEnv * env,
		jobject obj, jlong messenger) {
	tox_do((Tox *) messenger);
}

JNIEXPORT void JNICALL Java_im_tox_jtoxcore_JTox_tox_1bootstrap(JNIEnv * env,
		jobject obj, jlong messenger, jbyteArray ip, jint port, jstring address) {
	tox_IP_Port ipport;
	tox_IP _ip;

	jbyte *ip_array = (*env)->GetByteArrayElements(env, ip, 0);
	jsize n = (*env)->GetArrayLength(env, ip);
	int i;

	for (i = 0; i < n; ++i) {
		_ip.c[i] = ip_array[i];
	}

	(*env)->ReleaseByteArrayElements(env, ip, ip_array, 0);
	ipport.ip = _ip;
	ipport.port = htons((uint16_t) port);

	const char *_address = (*env)->GetStringUTFChars(env, address, 0);
	uint8_t __address[TOX_FRIEND_ADDRESS_SIZE];
	hex_to_addr(_address, __address);

	tox_bootstrap((Tox *) messenger, ipport, __address);

	(*env)->ReleaseStringUTFChars(env, address, _address);
}

JNIEXPORT jint JNICALL Java_im_tox_jtoxcore_JTox_tox_1isconnected(JNIEnv * env,
		jobject obj, jlong messenger) {
	return tox_isconnected((Tox *) messenger);
}

JNIEXPORT void JNICALL Java_im_tox_jtoxcore_JTox_tox_1onfriendrequest(
		JNIEnv * env, jobject obj, jlong messenger,
		friendrequest_callback callback) {

	tox_jni_callback *data = malloc(sizeof(tox_jni_callback));
	data->env = env;
	data->jobj = (*env)->NewGlobalRef(env, callback);
	(*env)->DeleteLocalRef(env, callback);
	tox_callback_friendrequest((Tox *) messenger,
			(void *) callback_friendrequest, data);
}

static void callback_friendrequest(uint8_t *pubkey, uint8_t *message,
		uint16_t length, void *ptr) {
	tox_jni_callback *data = ptr;
	const jclass class = (*data->env)->GetObjectClass(data->env, data->jobj);
	const jmethodID meth = (*data->env)->GetMethodID(data->env, class,
			"execute", "(Ljava/lang/String;Ljava/lang/String;)V");
	char buf[ADDR_SIZE_HEX] = {0};
	addr_to_hex(pubkey, buf);
	jstring _pubkey = (*data->env)->NewStringUTF(data->env, buf);
	jstring _message = (*data->env)->NewStringUTF(data->env, message);
	(*data->env)->CallVoidMethod(data->env, data->jobj, meth, _pubkey,
			_message);
}

