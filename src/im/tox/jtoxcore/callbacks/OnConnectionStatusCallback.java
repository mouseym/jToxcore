/* OnConnectionStatusCallback.java
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

/**
 * Callback class for receiving connection status changes from friends
 * 
 * @author sonOfRa
 * 
 */
public interface OnConnectionStatusCallback {

	/**
	 * Method to be executed each time a connection status is received
	 * 
	 * @param friendnumber
	 *            the friend who's status changed
	 * @param online
	 *            true if the friend is online after being previously offline,
	 *            false otherwise
	 */
	public abstract void execute(int friendnumber, boolean online);
}
