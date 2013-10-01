/* ToxError.java
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
 * Enum for Tox Error codes while adding friends
 * 
 * @author sonOfRa
 * 
 */
public enum ToxError {
	/**
	 * The specified status message or name was too long
	 */
	TOX_TOOLONG,
	/**
	 * Message for sending was empty
	 */
	TOX_FAERR_NOMESSAGE,
	/**
	 * Attempted to add the public key of the current instance as a friend
	 */
	TOX_FAERR_OWNKEY,
	/**
	 * A friend request was already sent to this user
	 */
	TOX_FAERR_ALREADYSENT,
	/**
	 * An unknown error occured
	 */
	TOX_UNKNOWN,
	/**
	 * The checksum for the specified public key was incorrect
	 */
	TOX_FAERR_BADCHECKSUM,
	/**
	 * If friend was already added, but the nospam value was changed
	 */
	TOX_FAERR_SETNEWNOSPAM,
	/**
	 * Failed to increase the size of the internal friend list
	 */
	TOX_FAERR_NOMEM,
	/**
	 * Attempted to call a method on a killed tox instance
	 */
	TOX_KILLED_INSTANCE,
	/**
	 * Failed to send message
	 */
	TOX_SEND_FAILED,
	/**
	 * An invalid port was specified (not in range 0-65535)
	 */
	TOX_INVALID_PORT,
	/**
	 * A friend with the specified friendnumber already exists
	 */
	TOX_FRIEND_EXISTS,
	/**
	 * Attempted to set our own status to "invalid"
	 */
	TOX_STATUS_INVALID;
}
