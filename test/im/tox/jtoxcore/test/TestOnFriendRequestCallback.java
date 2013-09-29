package im.tox.jtoxcore.test;

import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxException;
import im.tox.jtoxcore.callbacks.OnFriendRequestCallback;

public class TestOnFriendRequestCallback implements OnFriendRequestCallback {

	private JTox jtox;
	
	public TestOnFriendRequestCallback(JTox jtox) {
		this.jtox = jtox;
	}
	
	@Override
	public void execute(String publicKey, String message) {
		if (message == null) {
			message = "No mesage";
		}
		System.out.println("We received a friend request from " + publicKey
				+ " with the following message: " + message);

		try {
			jtox.confirmRequest(publicKey);
			System.out.println("confirmed!");
		} catch (ToxException e) {
			System.out.println("Unable to confirm friend request");
		}
	}
}