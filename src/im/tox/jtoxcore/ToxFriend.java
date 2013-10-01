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
 * Bean for a friend in Tox. Provides all the basic information about a Friend
 * instance. Also offers an optional nickname field per friend.
 * 
 * @author sonOfRa
 * 
 */
public class ToxFriend {

	protected String id;
	protected String name;
	protected String nickname;
	protected String statusMessage;
	protected ToxUserStatus status;
	protected boolean online;
	protected final int friendnumber;

	/**
	 * Default constructor for ToxFriends
	 * 
	 * @param friendnumber
	 *            the friendnumber of the created friend
	 */
	protected ToxFriend(int friendnumber) {
		this.friendnumber = friendnumber;
		this.status = ToxUserStatus.TOX_USERSTATUS_NONE;
		this.online = false;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return this.nickname;
	}

	/**
	 * @return the statusMessage
	 */
	public String getStatusMessage() {
		return this.statusMessage;
	}

	/**
	 * @return the status
	 */
	public ToxUserStatus getStatus() {
		return this.status;
	}

	/**
	 * @return the online
	 */
	public boolean isOnline() {
		return this.online;
	}

	/**
	 * @return the friendnumber
	 */
	public int getFriendnumber() {
		return this.friendnumber;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	protected void setId(String id) {
		this.id = id;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * @param nickname
	 *            the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @param statusMessage
	 *            the statusMessage to set
	 */
	protected void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	protected void setStatus(ToxUserStatus status) {
		this.status = status;
	}

	/**
	 * @param online
	 *            the online to set
	 */
	protected void setOnline(boolean online) {
		this.online = online;
	}

}
