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
    Tox *tox;
    JavaVM *jvm;
    jobject handler;
    jobject jtox;
} tox_jni_globals_t;

static void callback_friendrequest(uint8_t *, uint8_t *, uint16_t,
                                   tox_jni_globals_t *);
static void callback_friendmessage(Tox *, int, uint8_t *, uint16_t,
                                   tox_jni_globals_t *);
static void callback_action(Tox *, int, uint8_t *, uint16_t, tox_jni_globals_t *);
static void callback_namechange(Tox *, int, uint8_t *, uint16_t,
                                tox_jni_globals_t *);
static void callback_statusmessage(Tox *, int, uint8_t *, uint16_t,
                                   tox_jni_globals_t *);
static void callback_userstatus(Tox *, int, TOX_USERSTATUS, tox_jni_globals_t *);
static void callback_read_receipt(Tox *, int, uint32_t, tox_jni_globals_t *);
static void callback_connectionstatus(Tox *, int, uint8_t, tox_jni_globals_t *);

