package im.tox.jtoxcore.test;

import im.tox.jtoxcore.callbacks.OnConnectionStatusCallback;

public class TestOnConnectionStatusCallback implements OnConnectionStatusCallback {

	@Override
	public void execute(int friendnumber, boolean online) {
		if (online) {
			System.out.println(friendnumber + " came online");
		} else {
			System.out.println(friendnumber + " went offline");
		}
	}

}
