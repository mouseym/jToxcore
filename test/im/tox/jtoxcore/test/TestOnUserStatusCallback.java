package im.tox.jtoxcore.test;

import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxUserStatus;
import im.tox.jtoxcore.callbacks.OnUserStatusCallback;

public class TestOnUserStatusCallback extends OnUserStatusCallback {

	public TestOnUserStatusCallback(JTox jtox) {
		super(jtox);
	}

	@Override
	public void execute(int friendnumber, ToxUserStatus userstatus) {
		System.out.println(friendnumber + " changed their status to "
				+ userstatus.toString());
	}

}
