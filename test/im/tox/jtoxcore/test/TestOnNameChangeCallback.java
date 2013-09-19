package im.tox.jtoxcore.test;

import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxException;
import im.tox.jtoxcore.callbacks.OnNameChangeCallback;

public class TestOnNameChangeCallback extends OnNameChangeCallback {

	public TestOnNameChangeCallback(JTox jtox) {
		super(jtox);
	}

	@Override
	public void execute(int friendnumber, byte[] newname) {
		String name;
		try {
			name = JTox.getByteString(newname);
		} catch (ToxException e) {
			name = "ERROR: Unable to get name";
		}

		System.out.println(friendnumber + " changed their name to: " + name);
	}

}
