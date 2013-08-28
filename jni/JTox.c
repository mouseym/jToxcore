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
#include <tox/tox.h>

#include "im_tox_jtoxcore_JTox.h"

typedef struct {
	JNIEnv *env;
	jobject jobj;
} tox_jni_callback;

static void friendmessage_callback(Tox *tox, int friendnumber, uint8_t *message,
		uint16_t length, tox_jni_callback *data) {
	jclass clazz = (*data->env)->GetObjectClass((JNIEnv*) data->env,
			data->jobj);
	jmethodID method = (*data->env)->GetMethodID(data->env, clazz,
			"executeOnMessageCallback", "(ILjava/lang/String;)V");

	jstring _message = (*data->env)->NewStringUTF((JNIEnv *) data->env,
			message);
	(*data->env)->CallVoidMethod((JNIEnv*) data->env, data->jobj, method,
			friendnumber, _message);
}

JNIEXPORT void JNICALL Java_im_tox_jtoxcore_JTox_tox_1callback_1friendmessage(
		JNIEnv * env, jobject obj, jlong messenger) {
	tox_jni_callback callback = { env, obj };
	tox_callback_friendmessage((Tox *) messenger, (void * )friendmessage_callback,
			&callback);
}

JNIEXPORT jlong JNICALL Java_im_tox_jtoxcore_JTox_tox_1new(JNIEnv * env,
		jclass clazz) {
	return ((jlong) tox_new());
}

JNIEXPORT jint JNICALL Java_im_tox_jtoxcore_JTox_tox_1addfriend(JNIEnv * env,
		jobject obj, jlong messenger, jstring address, jstring data) {
	const uint8_t *_address = (*env)->GetStringUTFChars(env, address, 0);
	const uint8_t *_data = (*env)->GetStringUTFChars(env, data, 0);

	uint8_t *__address = malloc(strlen(_address) + 1);
	uint8_t *__data = malloc(strlen(_data) + 1);
	__address = strdup(_address);
	__data = strdup(_data);

	jsize length = (*env)->GetStringUTFLength(env, data);

	int errcode = tox_addfriend((Tox *) messenger, __address, __data, length);

	free(__address);
	free(__data);
	(*env)->ReleaseStringUTFChars(env, address, _address);
	(*env)->ReleaseStringUTFChars(env, data, _data);

	return errcode;
}

JNIEXPORT jint JNICALL Java_im_tox_jtoxcore_JTox_tox_1addfriend_1norequest(
		JNIEnv * env, jobject obj, jlong messenger, jstring address) {
	const uint8_t *_address = (*env)->GetStringUTFChars(env, address, 0);
	uint8_t *__address = malloc(strlen(_address) + 1);
	__address = strdup(_address);

	int errcode = tox_addfriend_norequest((Tox *) messenger, __address);

	free(__address);
	(*env)->ReleaseStringUTFChars(env, address, _address);

	return errcode;
}

JNIEXPORT jstring JNICALL Java_im_tox_jtoxcore_JTox_tox_1getaddress(
		JNIEnv * env, jobject obj, jlong messenger) {
	uint8_t address[TOX_FRIEND_ADDRESS_SIZE];
	tox_getaddress((Tox*) messenger, address);

	jstring result = (*env)->NewStringUTF(env, address);
	return result;
}

JNIEXPORT jint JNICALL Java_im_tox_jtoxcore_JTox_tox_1getfriend_1id(
		JNIEnv * env, jobject obj, jlong messenger, jstring address) {
	const uint8_t *_address = (*env)->GetStringUTFChars(env, address, 0);

	uint8_t *__address = malloc(strlen(_address) + 1);
	__address = strdup(_address);

	int errcode = tox_getfriend_id((Tox *) messenger, __address);

	free(__address);
	(*env)->ReleaseStringUTFChars(env, address, _address);

	return errcode;
}

JNIEXPORT jstring JNICALL Java_im_tox_jtoxcore_JTox_tox_1getclient_1id(
		JNIEnv * env, jobject obj, jlong messenger, jint friendnumber) {
	uint8_t address[TOX_FRIEND_ADDRESS_SIZE];

	if (tox_getclient_id((Tox *) messenger, friendnumber, address) == -1) {
		return 0;
	} else {
		jstring result = (*env)->NewStringUTF(env, address);
		return result;
	}
}
JNIEXPORT void JNICALL Java_im_tox_jtoxcore_JTox_tox_1do(JNIEnv * env,
		jobject obj, jlong messenger) {
	tox_do((Tox *) messenger);
}

