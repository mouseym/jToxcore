/* ToxFriend.java
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
 * Interface for a Friend in Tox. This interface only supplies very basic
 * operations needed for the core workings of Tox
 * 
 * The setters are used by internal mechanisms of JToxcore. They should never
 * need to be called manually
 *
 * @author sonOfRa
 */
public interface ToxFriend {

	/**
	 * @return the id
	 */
	String getId();

	/**
	 * @return the name
	 */
	String getName();

	/**
	 * @return the statusMessage
	 */
	String getStatusMessage();

	/**
	 * @return the status
	 */
	ToxUserStatus getStatus();

	/**
	 * @return the online
	 */
	boolean isOnline();

	/**
	 * @return the friendnumber
	 */
	int getFriendnumber();

	/**
	 * @param id
	 *            the id to set
	 */
	void setId(String id);

	/**
	 * @param name
	 *            the name to set
	 */
	void setName(String name);

	/**
	 * @param statusMessage
	 *            the statusMessage to set
	 */
	void setStatusMessage(String statusMessage);

	/**
	 * @param status
	 *            the status to set
	 */
	void setStatus(ToxUserStatus status);

	/**
	 * @param online
	 *            set whether Friend is online
	 */
	void setOnline(boolean online);
}
