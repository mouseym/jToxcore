package im.tox.jtoxcore.test;

import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxException;
import im.tox.jtoxcore.ToxFriend;
import im.tox.jtoxcore.ToxFriendList;
import im.tox.jtoxcore.ToxWorker;
import im.tox.jtoxcore.callbacks.CallbackHandler;

import java.net.UnknownHostException;

public class JToxTest {
	public static void main(String[] args) throws UnknownHostException,
			ToxException {
		ToxFriendList friendList = new ToxFriendList();
		CallbackHandler<ToxFriend> handler = new CallbackHandler<ToxFriend>(friendList);

		JTox jtox = new JTox(friendList, handler);
		jtox.bootstrap("54.215.145.71", 33445,
				"6EDDEE2188EF579303C0766B4796DCBA89C93058B6032FEA51593DCD42FB746C ");
		System.out.println(jtox.getAddress());
		ToxWorker worker = new ToxWorker(jtox);
		Thread workThread = new Thread(worker);
		workThread.start();
	}
}