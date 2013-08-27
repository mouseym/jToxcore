package im.tox.jtoxcore.callbacks;

public abstract class OnMessageCallback {
	public abstract void execute(int friendNumber, String message);
}
