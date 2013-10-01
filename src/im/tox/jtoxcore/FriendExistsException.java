/* FriendExistsException.java
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
 * Exception to be thrown if a friend is added to a Friendlist even though a
 * friend with that number already exists.
 * 
 * @author sonOfRa
 */
public class FriendExistsException extends Exception {
	private static final long serialVersionUID = 1L;
	private int friendnumber;

	/**
	 * Create a new FriendExistsException with the specified friendnumber and no
	 * message
	 * 
	 * @param friendnumber
	 *            the friendnumber
	 */
	public FriendExistsException(int friendnumber) {
		super();
		this.friendnumber = friendnumber;
	}

	/**
	 * Create a new FriendExistsException with the specified friendnumber and
	 * detail message
	 * 
	 * @param friendnumber
	 *            the friendnumber
	 * @param message
	 *            the message
	 */
	public FriendExistsException(int friendnumber, String message) {
		super(message);
		this.friendnumber = friendnumber;
	}

	/**
	 * @return the friendnumber
	 */
	public int getFriendnumber() {
		return this.friendnumber;
	}
}
