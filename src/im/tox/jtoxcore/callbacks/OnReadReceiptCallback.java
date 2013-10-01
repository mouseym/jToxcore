/* OnReadReceiptCallback.java
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

package im.tox.jtoxcore.callbacks;

import im.tox.jtoxcore.ToxFriend;

/**
 * Callback class for receiving read receipts
 * 
 * @author sonOfRa
 * @param <F>
 *            Friend type to use for this OnReadReceiptCallback instance
 */
public interface OnReadReceiptCallback<F extends ToxFriend> {

	/**
	 * Method to be executed each time a read receipt is received
	 * 
	 * @param friend
	 *            the friend who sent the receipt
	 * @param receipt
	 *            number of the receipt. This is one of the numbers returned
	 *            when sending messages, so you will need to keep track of those
	 *            in order to use this functionality.
	 */
	void execute(F friend, int receipt);
}
