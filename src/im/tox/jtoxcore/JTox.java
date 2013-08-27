/* JTox.java
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
 * This is the main wrapper class for the tox library. It contains wrapper
 * methods for everything in the public API provided by tox.h
 * @author sonOfRa
 *
 */
public class JTox {
	
	/**
	 * This field contains the pointer used in all native
	 * tox_ method calls.
	 */
	private long messengerPointer;
	
	/**
	 * Native call to tox_addfriend
	 * @param address address of the friend
	 * @param data optional message sent to friend
	 * @param length length of the message sent to friend
	 * @return friend number on success, error code on failure
	 */
	private native int tox_addfriend(long messengerPointer, String address, String data);
	
	/**
	 * Use this method to add a friend.
	 * @param address the address of the friend you want to add
	 * @param data an optional message you want to send to your friend
	 * @return the local number of the friend in your list
	 * @throws ToxException in case an error code is returned by the native tox_addfriend call
	 */
	public int addFriend(String address, String data) throws ToxException {
		int errcode = tox_addfriend(this.messengerPointer, address, data);
		
		if(errcode >= 0) {
			return errcode;
		} else {
			throw new ToxException(errcode);
		}
	}
}
