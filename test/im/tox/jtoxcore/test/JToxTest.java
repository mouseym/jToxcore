package im.tox.jtoxcore.test;

import java.net.InetSocketAddress;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxException;
import im.tox.jtoxcore.callbacks.OnFriendRequestCallback;

public class JToxTest {
	public static void main(String[] args) {
		System.out.println(System.getProperty("java.library.path"));
		try {
			JTox jtox = JTox.newTox();
			jtox.bootstrap(
					new InetSocketAddress(Inet4Address
							.getByName("198.46.136.167"), 33445),
					"728925473812C7AAC482BE7250BCCAD0B8CB9F737BF3D42ABD34459C1768F854");
			String addr = jtox.getAddress();
			jtox.setOnFriendRequestCallback(new OnFriendRequestCallback() {

				@Override
				public void execute(String publicKey, String message) {
					System.out
							.println("Yay! We received a friend request from: "
									+ publicKey
									+ " with "
									+ (message.isEmpty() ? "no message."
											: "the following message: "
													+ message));
				}
			});
			System.out.println(addr);
			while (true) {
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
