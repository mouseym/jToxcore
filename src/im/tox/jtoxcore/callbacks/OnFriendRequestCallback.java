package im.tox.jtoxcore.callbacks;

public abstract class OnFriendRequestCallback {
	public abstract void execute(String publicKey, String message);
}
