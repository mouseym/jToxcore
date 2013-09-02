package im.tox.jtoxcore.test;

import java.net.InetSocketAddress;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxError;
import im.tox.jtoxcore.ToxException;
import im.tox.jtoxcore.callbacks.OnFriendRequestCallback;
import im.tox.jtoxcore.callbacks.OnMessageCallback;

public class JToxTest {
	public static void main(String[] args) {
		try {
			JTox jtox = new JTox();
			System.out.println(jtox.getAddress());
			jtox.bootstrap(
					new InetSocketAddress(Inet4Address
							.getByName("54.215.145.71"), 33445),
					"6EDDEE2188EF579303C0766B4796DCBA89C93058B6032FEA51593DCD42FB746C");
			jtox.setOnFriendRequestCallback(new MyOnFriendRequestCallback(jtox));
			jtox.setOnMessageCallback(new MyOnMessageCallback());
			
			while(true) {
				jtox.doTox();
			}
		} catch (ToxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class MyOnFriendRequestCallback extends OnFriendRequestCallback {
	private JTox jtox;

	public MyOnFriendRequestCallback(JTox jtox) {
		this.jtox = jtox;
	}

	@Override
	public void execute(String publicKey, String message) {
		System.out.println("Yay! We received a friend request from: "
				+ publicKey
				+ " with "
				+ (message.isEmpty() ? "no message."
						: "the following message: " + message));
		try {
			this.jtox.confirmRequest(publicKey);
			System.out.println("Friend request has been autoconfirmed!");
		} catch (ToxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class MyOnMessageCallback extends OnMessageCallback {

	@Override
	public void execute(int friendNumber, String message) {
		System.out.println("Message from " + friendNumber + ": " + message);
	}

}
