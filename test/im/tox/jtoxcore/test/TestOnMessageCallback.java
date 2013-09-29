package im.tox.jtoxcore.test;

import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxException;
import im.tox.jtoxcore.callbacks.OnMessageCallback;

public class TestOnMessageCallback implements OnMessageCallback {

	@Override
	public void execute(int friendnumber, String message) {
		System.out.println("Received the following message from friend "
				+ friendnumber + ": \"" + message + "a\"");
	}
}