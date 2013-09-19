package im.tox.jtoxcore.test;

import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.callbacks.OnReadReceiptCallback;

public class TestOnReadReceiptCallback extends OnReadReceiptCallback {

	public TestOnReadReceiptCallback(JTox jtox) {
		super(jtox);
	}

	@Override
	public void execute(int friendnumber, int receipt) {
		System.out.println("Received a receipt from " + friendnumber
				+ " for message " + receipt);
	}

}
