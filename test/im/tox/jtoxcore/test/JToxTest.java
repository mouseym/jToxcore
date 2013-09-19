package im.tox.jtoxcore.test;

import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxException;
import im.tox.jtoxcore.ToxWorker;

import java.net.UnknownHostException;

public class JToxTest {
	public static void main(String[] args) throws UnknownHostException {
		try {
			JTox jtox = new JTox();
			jtox.bootstrap("66.175.223.88", 33445,
					"AC4112C975240CAD260BB2FCD134266521FAAF0A5D159C5FD3201196191E4F5D");
			System.out.println(jtox.getAddress());
			jtox.setOnActionCallback(new TestOnActionCallback(jtox));
			jtox.setOnConnectionStatusCallback(new TestOnConnectionStatusCallback(
					jtox));
			jtox.setOnFriendRequestCallback(new TestOnFriendRequestCallback(
					jtox));
			jtox.setOnMessageCallback(new TestOnMessageCallback(jtox));
			jtox.setOnNameChangeCallback(new TestOnNameChangeCallback(jtox));
			jtox.setOnReadReceiptCallback(new TestOnReadReceiptCallback(jtox));
			jtox.setOnStatusMessageCallback(new TestOnStatusMessageCallback(
					jtox));
			jtox.setOnUserStatusCallback(new TestOnUserStatusCallback(jtox));
			Thread worker = new Thread(new ToxWorker(jtox));
			worker.start();
		} catch (ToxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}