package im.tox.jtoxcore.test;

import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.callbacks.OnReadReceiptCallback;

public class TestOnReadReceiptCallback implements OnReadReceiptCallback {

	@Override
	public void execute(int friendnumber, int receipt) {
		System.out.println("Received a receipt from " + friendnumber
				+ " for message " + receipt);
	}

}
