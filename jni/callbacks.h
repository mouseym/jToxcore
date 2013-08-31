typedef struct {
	JNIEnv *env;
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

static void callback_friendrequest(uint8_t *, uint8_t*, uint16_t, void*);
static void callback_friendmessage(Tox *, int, uint8_t*, uint16_t, void*);
static void callback_action(Tox *, int, uint8_t*, uint16_t, void*);
static void callback_namechange(Tox *, int, uint8_t*, uint16_t, void*);
static void callback_statusmessage(Tox *, int, uint8_t*, uint16_t, void*);
static void callback_userstatus(Tox *, int, TOX_USERSTATUS, void*);
static void callback_read_receipt(Tox *, int, uint32_t, void*);
static void callback_connectionstatus(Tox *, int, uint8_t, void*);

