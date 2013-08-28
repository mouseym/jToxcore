package im.tox.jtoxcore.test;

import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxException;

public class JToxTest {
	public static void main(String[] args) {
		System.out.println(System.getProperty("java.library.path"));
		try {
			JTox jtox = JTox.newTox();
		} catch (ToxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
