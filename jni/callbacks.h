typedef struct {
	JNIEnv *env;
	jobject jobj;
} tox_jni_callback;

typedef jobject friendrequest_callback;
typedef jobject friendmessage_callback;
typedef jobject action_callback;
typedef jobject namechange_callback;
typedef jobject statusmessage_callback;
typedef jobject userstatus_callback;
typedef jobject read_receipt_callback;
typedef jobject connectionstatus_callback;

static void callback_friendrequest(uint8_t *, uint8_t*, uint16_t, void*);
void callback_friendmessage(Tox *, int, uint8_t*, uint16_t, void*);
void callback_action(Tox *, int, uint8_t*, uint16_t, void*);
void callback_namechange(Tox *, int, uint8_t*, uint16_t, void*);
void callback_statusmessage(Tox *, int, uint8_t*, uint16_t, void*);
void callback_userstatus(Tox *, int, TOX_USERSTATUS, void*);
void callback_read_receipt(Tox *, int, uint32_t, void*);
void callback_connectionstatus(Tox *, int, uint8_t, void*);

