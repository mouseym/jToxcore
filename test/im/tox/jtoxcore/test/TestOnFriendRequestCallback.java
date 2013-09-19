package im.tox.jtoxcore.test;

import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxException;
import im.tox.jtoxcore.callbacks.OnFriendRequestCallback;

public class TestOnFriendRequestCallback extends OnFriendRequestCallback {

	public TestOnFriendRequestCallback(JTox jtox) {
		super(jtox);
	}

	@Override
	public void execute(String publicKey, byte[] message) {
		String msg;
		if (message == null) {
			msg = "No mesage";
		} else {
			try {
				msg = JTox.getByteString(message);
			} catch (ToxException e) {
				msg = "ERROR: Unable to get message";
			}
		}
		System.out.println("We received a friend request from " + publicKey
				+ " with the following message: " + msg);

		try {
			jtox.confirmRequest(publicKey);
			System.out.println("confirmed!");
		} catch (ToxException e) {
			System.out.println("Unable to confirm friend request");
		}
	}
}