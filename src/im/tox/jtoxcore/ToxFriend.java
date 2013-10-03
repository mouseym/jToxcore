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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Bean for a friend in Tox. Provides all the basic information about a Friend
 * instance. Also offers an optional nickname field per friend.
 * <p>
 * Please not that the nickname field is not a field that is internal to the tox
 * API. If you want this field to be persistent and still available after
 * saving/loading, you will have to persist it to your own storage and take care
 * of loading it correctly at runtime. All other fields are automatically
 * populated when using the {@link JTox#load(byte[])} method.
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
	protected transient List<Integer> sentMessages;
	protected transient List<Integer> deliveredMessages;

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
		this.sentMessages = Collections
				.synchronizedList(new ArrayList<Integer>());
		this.deliveredMessages = Collections
				.synchronizedList(new ArrayList<Integer>());
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
	 * @return the IDs for sent messages
	 */
	public List<Integer> getSentMessages() {
		return this.sentMessages;
	}

	/**
	 * @return the IDs of sent messages for which we received a read receipt
	 */
	public List<Integer> getDeliveredMessages() {
		return this.deliveredMessages;
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
