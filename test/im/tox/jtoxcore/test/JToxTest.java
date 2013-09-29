package im.tox.jtoxcore.test;

import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxException;
import im.tox.jtoxcore.ToxWorker;
import im.tox.jtoxcore.callbacks.CallbackHandler;

import java.net.UnknownHostException;

public class JToxTest {
	public static void main(String[] args) throws UnknownHostException {
		try {
			CallbackHandler handler = new CallbackHandler();

			JTox jtox = new JTox(handler);
			handler.registerOnActionCallback(new TestOnActionCallback());
			handler.registerOnConnectionStatusCallback(new TestOnConnectionStatusCallback());
			handler.registerOnFriendRequestCallback(new TestOnFriendRequestCallback(
					jtox));
			handler.registerOnMessageCallback(new TestOnMessageCallback());
			handler.registerOnNameChangeCallback(new TestOnNameChangeCallback());
			handler.registerOnReadReceiptCallback(new TestOnReadReceiptCallback());
			handler.registerOnStatusMessageCallback(new TestOnStatusMessageCallback());
			handler.registerOnUserStatusCallback(new TestOnUserStatusCallback());

			jtox.bootstrap("66.175.223.88", 33445,
					"AC4112C975240CAD260BB2FCD134266521FAAF0A5D159C5FD3201196191E4F5D");
			System.out.println(jtox.getAddress());
			Thread worker = new Thread(new ToxWorker(jtox));
			worker.start();
		} catch (ToxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}