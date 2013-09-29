package im.tox.jtoxcore.test;

import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxException;
import im.tox.jtoxcore.callbacks.OnNameChangeCallback;

public class TestOnNameChangeCallback implements OnNameChangeCallback {

	@Override
	public void execute(int friendnumber, String newname) {
		System.out.println(friendnumber + " changed their name to: " + newname);
	}

}
