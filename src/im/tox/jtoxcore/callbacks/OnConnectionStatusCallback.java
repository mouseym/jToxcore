package im.tox.jtoxcore.callbacks;

public abstract class OnConnectionStatusCallback {
	public abstract void execute(int friendnumber, boolean online);
}
