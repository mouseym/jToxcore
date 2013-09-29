package im.tox.jtoxcore.test;

import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxException;
import im.tox.jtoxcore.callbacks.OnStatusMessageCallback;

public class TestOnStatusMessageCallback implements OnStatusMessageCallback {

	@Override
	public void execute(int friendnumber, String newstatus) {
		System.out.println(friendnumber + " changed their statusmessage to: "
				+ newstatus);
	}

}
