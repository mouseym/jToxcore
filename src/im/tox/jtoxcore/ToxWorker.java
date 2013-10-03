/* ToxWorker.java
 *
 *  Copyright (C) 2013 Tox project All Rights Reserved.
 *
 *  This file is part of jToxcore
 *
 *  jToxcore is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  jToxcore is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with jToxcore.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package im.tox.jtoxcore;

/**
 * Default implementation for a Tox Worker runnable. It calls
 * {@link JTox#doTox()} at the given frequency. If no frequency is given, or the
 * given frequency is < 20, it will be set to 20, which is the minimum frequency
 * as suggested by the core developers.
 * 
 * When the instance is killed, a call to {@link JTox#doTox()} will result in an
 * exception. This exception will be caught, and the run method will terminate,
 * thus terminating the Thread this Worker is running in.
 * 
 * @author sonOfRa
 * @param <F>
 *            friend type that is used with the JTox instance held by this class
 * 
 */
public class ToxWorker<F extends ToxFriend> implements Runnable {

	private JTox<F> instance;
	private int sleeptime;

	/**
	 * Creates a new Tox worker runnable with the default frequency of 20Hz
	 * 
	 * @param instance
	 *            the JTox instance to work on
	 */
	public ToxWorker(JTox<F> instance) {
		this.instance = instance;
		this.sleeptime = 1000 / 20;
	}

	/**
	 * Creates a new Tox worker runnable with the given frequency. The minimum
	 * frequency is 20, if the frequency is smaller than 20, it will be set to
	 * 20.
	 * 
	 * @param instance
	 *            the JTox instance to work on
	 * @param frequency
	 *            the frequency (in Hz)
	 */
	public ToxWorker(JTox<F> instance, int frequency) {
		this.instance = instance;
		this.sleeptime = 1000 / frequency >= 20 ? frequency : 20;
	}

	@Override
	public void run() {
		while (true) {
			try {
				this.instance.doTox();
			} catch (ToxException e) {
				return;
			}
			try {
				Thread.sleep(this.sleeptime);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

}
