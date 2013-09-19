package im.tox.jtoxcore.test;

import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.callbacks.OnConnectionStatusCallback;

public class TestOnConnectionStatusCallback extends OnConnectionStatusCallback {

	public TestOnConnectionStatusCallback(JTox jtox) {
		super(jtox);
	}

	@Override
	public void execute(int friendnumber, boolean online) {
		if (online) {
			System.out.println(friendnumber + " came online");
		} else {
			System.out.println(friendnumber + " went offline");
		}
	}

}
