package im.tox.jtoxcore.test;

import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxException;
import im.tox.jtoxcore.callbacks.OnMessageCallback;

public class TestOnMessageCallback extends OnMessageCallback {

	public TestOnMessageCallback(JTox jtox) {
		super(jtox);
	}

	@Override
	public void execute(int friendnumber, byte[] message) {
		String msg;
		try {
			msg = JTox.getByteString(message);
		} catch (ToxException e) {
			msg = "ERROR: Unable to get message";
		}
		System.out.println("Received the following message from friend "
				+ friendnumber + ": \"" + msg + "a\"");
	}
}