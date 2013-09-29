package im.tox.jtoxcore.test;

import im.tox.jtoxcore.callbacks.OnActionCallback;

public class TestOnActionCallback implements OnActionCallback {

	@Override
	public void execute(int friendnumber, String action) {
		System.out.println("Got action from friend:" + friendnumber
				+ " with the following content: " + action);
	}

}
