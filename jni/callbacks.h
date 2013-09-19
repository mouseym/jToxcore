/* callbacks.h
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
typedef struct {
	JavaVM *jvm;
	jobject jobj;
} tox_jni_callback_t;

typedef tox_jni_callback_t friendrequest_callback_t;
typedef tox_jni_callback_t friendmessage_callback_t;
typedef tox_jni_callback_t action_callback_t;
typedef tox_jni_callback_t namechange_callback_t;
typedef tox_jni_callback_t statusmessage_callback_t;
typedef tox_jni_callback_t userstatus_callback_t;
typedef tox_jni_callback_t read_receipt_callback_t;
typedef tox_jni_callback_t connectionstatus_callback_t;

typedef struct {
	Tox * tox;
	friendrequest_callback_t *frqc;
	friendmessage_callback_t *frmc;
	action_callback_t *ac;
	namechange_callback_t *nc;
	statusmessage_callback_t *smc;
	userstatus_callback_t *usc;
	read_receipt_callback_t *rrc;
	connectionstatus_callback_t *csc;
} tox_jni_globals_t;

static void callback_friendrequest(uint8_t *, uint8_t*, uint16_t, friendrequest_callback_t*);
static void callback_friendmessage(Tox *, int, uint8_t*, uint16_t, friendmessage_callback_t*);
static void callback_action(Tox *, int, uint8_t*, uint16_t, action_callback_t*);
static void callback_namechange(Tox *, int, uint8_t*, uint16_t, namechange_callback_t*);
static void callback_statusmessage(Tox *, int, uint8_t*, uint16_t, statusmessage_callback_t*);
static void callback_userstatus(Tox *, int, TOX_USERSTATUS, userstatus_callback_t*);
static void callback_read_receipt(Tox *, int, uint32_t, read_receipt_callback_t*);
static void callback_connectionstatus(Tox *, int, uint8_t, connectionstatus_callback_t*);

