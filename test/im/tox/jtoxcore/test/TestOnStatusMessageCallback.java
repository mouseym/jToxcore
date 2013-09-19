package im.tox.jtoxcore.test;

import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxException;
import im.tox.jtoxcore.callbacks.OnStatusMessageCallback;

public class TestOnStatusMessageCallback extends OnStatusMessageCallback {

	public TestOnStatusMessageCallback(JTox jtox) {
		super(jtox);
	}

	@Override
	public void execute(int friendnumber, byte[] newstatus) {
		String status;
		try {
			status = JTox.getByteString(newstatus);
		} catch (ToxException e) {
			status = "ERROR: Could not get new status message";
		}

		System.out.println(friendnumber + " changed their statusmessage to: "
				+ status);
	}

}
