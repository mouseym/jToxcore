package im.tox.jtoxcore.test;

import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxException;
import im.tox.jtoxcore.callbacks.OnActionCallback;

public class TestOnActionCallback extends OnActionCallback {

	public TestOnActionCallback(JTox jtox) {
		super(jtox);
	}

	@Override
	public void execute(int friendnumber, byte[] action) {
		String act;
		try {
			act = JTox.getByteString(action);
		} catch (ToxException e) {
			act = "ERROR: Unable to get action";
		}

		System.out.println("Got action from friend:" + friendnumber
				+ " with the following content: " + act);
	}

}
