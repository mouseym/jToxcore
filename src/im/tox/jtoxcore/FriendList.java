/* FriendList.java
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

import java.util.List;

/**
 * Interface definition for a Tox Friendlist implementation
 * 
 * @author sonOfRa
 * 
 */
public interface FriendList<F extends ToxFriend> {

	/**
	 * Retrieve a friend from the list by its number
	 * 
	 * @param friendnumber
	 *            the number to look for
	 * @return the first friend in the list with the specified friendnumber.
	 *         Null if no such friend exists.
	 */
	F getByFriendNumber(int friendnumber);

	/**
	 * Retrieve a friend by its client id.
	 * 
	 * @param id
	 *            the id to search for
	 * @return the first friend in the list with the specified client id. Null
	 *         if no such friend exists.
	 */
	F getById(String id);

	/**
	 * Retrieve all friends with the specified name
	 * 
	 * @param name
	 *            the name to look for
	 * @param ignorecase
	 *            when <code>true</code>, this call must ignore the case of
	 *            results
	 * @return a list containing all friends with the specified name. If no
	 *         results are found, an empty List is returned.
	 */
	List<F> getByName(String name, boolean ignorecase);

	/**
	 * Retrieve all friends with the specified nickname
	 * 
	 * @param nickname
	 * @param ignorecase
	 *            when <code>true</code>, this call must ignore the case of
	 *            results
	 * @return a List containing all friends with the specified nickname. If no
	 *         results are found, an empty List is returned.
	 */
	List<F> getByNickname(String nickname, boolean ignorecase);

	/**
	 * Retrieve all Friends that have the content of partial inside of their
	 * names or nicknames. This method should ignore the case when comparing.
	 * 
	 * @param partial
	 *            the partial String to look for
	 * @return a List containing all friends that match the partial String. If
	 *         no results are found, an empty List is returned.
	 */
	List<F> searchFriend(String partial);

	/**
	 * Get all friends that are currently online and have the specified status.
	 * 
	 * @param status
	 *            the status to search for
	 * @return a List containing all friends with the specified status. If no
	 *         friends have that status, an empty List is returned.
	 */
	List<F> getByStatus(ToxUserStatus status);

	/**
	 * Get all currently online friends
	 * 
	 * @return a List containing all online friends. If no friends are online,
	 *         an empty List is returned.
	 */
	List<F> getOnlineFriends();

	/**
	 * Get all currently offline friends
	 * 
	 * @return a List containing all offline friends. If no friends are offline,
	 *         an empty List is returned.
	 */
	List<F> getOfflineFriends();

	/**
	 * Get all friends
	 * 
	 * @return a List containing all friends. If there are no friends, an empty
	 *         List is returned.
	 */
	List<F> all();

	/**
	 * Add the specified friend to the list.
	 * 
	 * @param friend
	 *            the new friend
	 * @throws FriendExistsException
	 *             if a friend with that friendnumber already exists.
	 */
	void addFriend(F friend) throws FriendExistsException;

	/**
	 * Remove the friend associated with that friendnumber
	 * 
	 * @param friendnumber
	 *            the friendnumber to remove
	 */
	void removeFriend(int friendnumber);
}
