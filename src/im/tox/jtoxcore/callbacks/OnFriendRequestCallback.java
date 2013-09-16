/* OnFriendRequestCallback.java
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

import im.tox.jtoxcore.JTox;

/**
 * Callback class for receiving friend requests
 * 
 * @author sonOfRa
 * 
 */
public abstract class OnFriendRequestCallback extends ToxCallback {

	/**
	 * Default constructor for {@link OnFriendRequestCallback}
	 * 
	 * @param jtox
	 *            the {@link JTox} instance to use for this callback
	 */
	public OnFriendRequestCallback(JTox jtox) {
		super(jtox);
	}

	/**
	 * Method to be executed each time a friend request is received
	 * 
	 * @param publicKey
	 *            the friend's public key
	 * @param message
	 *            an optional message sent with the friend request. Might be
	 *            <code>null</code>, so you need to check for this
	 */
	public abstract void execute(String publicKey, String message);
}
