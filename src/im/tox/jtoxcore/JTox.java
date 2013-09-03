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

import im.tox.jtoxcore.callbacks.OnActionCallback;
import im.tox.jtoxcore.callbacks.OnConnectionStatusCallback;
import im.tox.jtoxcore.callbacks.OnFriendRequestCallback;
import im.tox.jtoxcore.callbacks.OnMessageCallback;
import im.tox.jtoxcore.callbacks.OnNameChangeCallback;
import im.tox.jtoxcore.callbacks.OnReadReceiptCallback;
import im.tox.jtoxcore.callbacks.OnStatusMessageCallback;
import im.tox.jtoxcore.callbacks.OnUserStatusCallback;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This is the main wrapper class for the tox library. It contains wrapper
 * methods for everything in the public API provided by tox.h
 * 
 * @author sonOfRa
 * 
 */
public class JTox {

	static {
		System.loadLibrary("sodium");
		System.loadLibrary("jtox");
	}

	/**
	 * This field contains the lock used for thread safety
	 */
	protected final ReentrantLock lock;

	/**
	 * Map containing associations between pointers and Locks. This is used for
	 * acquiring the correct lock, as well as checking whether a pointer is
	 * still valid
	 */
	private static Map<Long, ReentrantLock> validPointers = Collections
			.synchronizedMap(new HashMap<Long, ReentrantLock>());

	/**
	 * This field contains the pointer used in all native tox_ method calls.
	 */
	protected final long messengerPointer;

	/**
	 * Checks whether the given pointer points to a valid tox instance
	 * 
	 * @param messengerPointer
	 *            the pointer to check
	 * @return true if valid, false otherwise
	 */
	protected final boolean isValidPointer(long messengerPointer) {
		return validPointers.containsKey(messengerPointer);
	}

	/**
	 * Grab a Lock for the current tox instance. This does NOT acquire the lock,
	 * it only returns a reference to it, so it can be used
	 * 
	 * @param messengerPointer
	 *            the pointer to acquire a lock for
	 * @return the lock for the specified pointer
	 */
	protected static final ReentrantLock getLock(long messengerPointer) {
		return validPointers.get(messengerPointer);
	}

	/**
	 * Native call to tox_new
	 * 
	 * @return the pointer to the messenger struct on success, 0 on failure
	 */
	private native long tox_new();

	/**
	 * Creates a new instance of JTox and stores the pointer to the internal
	 * struct in messengerPointer.
	 * 
	 * @return a new JTox instance
	 * @throws ToxException
	 *             when the native call indicates an error
	 */
	public JTox() throws ToxException {
		long pointer = tox_new();
		if (pointer == 0) {
			throw new ToxException(ToxError.TOX_FAERR_UNKNOWN);
		} else {
			this.messengerPointer = pointer;
			this.lock = new ReentrantLock();
			validPointers.put(pointer, this.lock);
		}
	}

	/**
	 * Native call to tox_addfriend
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param address
	 *            address of the friend
	 * @param data
	 *            optional message sent to friend
	 * @param length
	 *            length of the message sent to friend
	 * @return friend number on success, error code on failure
	 */
	private native int tox_addfriend(long messengerPointer, String address,
			String data);

	/**
	 * Use this method to add a friend.
	 * 
	 * @param address
	 *            the address of the friend you want to add
	 * @param data
	 *            an optional message you want to send to your friend
	 * @return the local number of the friend in your list
	 * @throws ToxException
	 *             if the instance has been killed or an error code is returned
	 *             by the native tox_addfriend call
	 */
	public int addFriend(String address, String data) throws ToxException {
		lock.lock();
		int errcode = -5; // Default errcode for unknown error
		try {
			if (!isValidPointer(this.messengerPointer)) {
				throw new ToxException(ToxError.TOX_KILLED_INSTANCE);
			}

			errcode = tox_addfriend(this.messengerPointer, address, data);
		} finally {
			lock.unlock();
		}

		if (errcode >= 0) {
			return errcode;
		} else {
			throw new ToxException(errcode);
		}
	}

	/**
	 * Native call to tox_addfriend_norequest
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param address
	 *            the address of the client you want to add
	 * @return the local number of the friend in your list
	 */
	private native int tox_addfriend_norequest(long messengerPointer,
			String address);

	/**
	 * Confirm a friend request, or add a friend to your own list without
	 * sending them a friend request
	 * 
	 * @param address
	 *            address of the friend to add
	 * @return the local number of the friend in your list
	 * @throws ToxException
	 *             if the instance was killed or an error occurred when adding
	 *             the friend
	 */
	public int confirmRequest(String address) throws ToxException {

		lock.lock();
		int errcode = -5; // Default errcode for unknown error
		try {
			if (!isValidPointer(this.messengerPointer)) {
				throw new ToxException(ToxError.TOX_KILLED_INSTANCE);
			}

			errcode = tox_addfriend_norequest(this.messengerPointer, address);
		} finally {
			lock.unlock();
		}

		if (errcode >= 0) {
			return errcode;
		} else {
			throw new ToxException(errcode);
		}
	}

	/**
	 * Native call to tox_getaddress
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct.
	 * 
	 * @return the client's address on success, null on failure
	 */
	private native String tox_getaddress(long messengerPointer);

	/**
	 * Get our own address
	 * 
	 * @return our client's address
	 * @throws ToxException
	 *             when the instance has been killed or an error occurred when
	 *             trying to get our address
	 */
	public String getAddress() throws ToxException {
		lock.lock();
		String address;
		try {
			if (!isValidPointer(this.messengerPointer)) {
				throw new ToxException(ToxError.TOX_KILLED_INSTANCE);
			}
			address = tox_getaddress(this.messengerPointer);
		} finally {
			lock.unlock();
		}

		if (address == null || address.equals("")) {
			throw new ToxException(ToxError.TOX_FAERR_UNKNOWN);
		} else {
			return address;
		}
	}

	/**
	 * Native call to tox_getfriend_id
	 * 
	 * @param clientid
	 *            the friend's public key
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @return the local id of the specified friend, or -1 if friend does not
	 *         exist
	 */
	private native int tox_getfriend_id(long messengerPointer, String clientid);

	/**
	 * Returns the local id of a friend given a public key. Throws an exception
	 * in case the specified friend does not exist
	 * 
	 * @param clientid
	 *            the friend's public key
	 * @return the local id of the specified friend
	 * @throws ToxException
	 *             if the instance has been killed or the friend does not exist
	 */
	public int getFriendId(String clientid) throws ToxException {

		lock.lock();
		int errcode = -1;
		try {
			if (!isValidPointer(this.messengerPointer)) {
				throw new ToxException(ToxError.TOX_KILLED_INSTANCE);
			}

			errcode = tox_getfriend_id(this.messengerPointer, clientid);
		} finally {
			lock.unlock();
		}

		if (errcode == -1) {
			throw new ToxException(ToxError.TOX_FAERR_UNKNOWN);
		} else {
			return errcode;
		}
	}

	/**
	 * Native call to tox_getclient_id
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param friendnumber
	 *            local number of the friend
	 * @return the public key of the specified friend
	 */
	private native String tox_getclient_id(long messengerPointer,
			int friendnumber);

	/**
	 * Get the client id for a given friendnumber
	 * 
	 * @param friendnumber
	 *            the number of the friend
	 * @return client id for the given friend
	 * @throws ToxException
	 *             if the instance has been killed, or an error occurred when
	 *             attempting to fetch the client id
	 */
	public String getClientId(int friendnumber) throws ToxException {

		lock.lock();
		String result;
		try {
			if (!isValidPointer(this.messengerPointer)) {
				throw new ToxException(ToxError.TOX_KILLED_INSTANCE);
			}

			result = tox_getclient_id(this.messengerPointer, friendnumber);
		} finally {
			lock.unlock();
		}

		if (result == null || result.equals("")) {
			throw new ToxException(ToxError.TOX_FAERR_UNKNOWN);
		} else {
			return result;
		}
	}

	/**
	 * Native call to tox_do
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 */
	private native void tox_do(long messengerPointer);

	/**
	 * The main tox loop that needs to be run at least 20 times per second. When
	 * implementing this, either use it in a main loop to guarantee execution,
	 * or start an asynchronous Thread or Service to do it for you.
	 * 
	 * @throws ToxException
	 *             if the instance has been killed
	 */
	public void doTox() throws ToxException {

		lock.lock();
		try {
			if (!isValidPointer(this.messengerPointer)) {
				throw new ToxException(ToxError.TOX_KILLED_INSTANCE);
			}

			tox_do(this.messengerPointer);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Native call to tox_bootstrap
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param ip
	 *            ip address to bootstrap with
	 * @param port
	 *            port to bootstrap with
	 * @param pubkey
	 *            public key of the bootstrap node
	 */
	private native void tox_bootstrap(long messengerPointer, byte[] ip,
			int port, String pubkey);

	/**
	 * Method used to bootstrap the client's connection.
	 * 
	 * @param address
	 *            A IP-port pair to connect to
	 * @param pubkey
	 *            public key of the bootstrap node
	 * @throws ToxException
	 *             if the instance has been killed
	 */
	public void bootstrap(InetSocketAddress address, String pubkey)
			throws ToxException {

		lock.lock();
		try {
			if (!isValidPointer(this.messengerPointer)) {
				throw new ToxException(ToxError.TOX_KILLED_INSTANCE);
			}

			tox_bootstrap(messengerPointer, address.getAddress().getAddress(),
					address.getPort(), pubkey);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Native call to tox_isconnected
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 */
	private native int tox_isconnected(long messengerPointer);

	/**
	 * Check if the client is connected to the DHT
	 * 
	 * @return true if connected, false otherwise
	 * @throws ToxException
	 *             if the instance has been killed
	 */
	public boolean isConnected() throws ToxException {

		lock.lock();
		try {
			if (!isValidPointer(this.messengerPointer)) {
				throw new ToxException(ToxError.TOX_KILLED_INSTANCE);
			}

			if (tox_isconnected(messengerPointer) == 0) {
				return false;
			} else {
				return true;
			}
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Native call to tox_callback_friendrequest
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param callback
	 *            the callback to set for receiving friend requests
	 */
	private native void tox_onfriendrequest(long messengerPointer,
			OnFriendRequestCallback callback);

	/**
	 * Method used to set a callback method for receiving friend requests. Any
	 * time a friend request is received on this Tox instance, the
	 * {@link OnFriendRequestCallback#execute(String, String)} method will be
	 * executed.
	 * 
	 * @param callback
	 *            the callback to set for receiving friend requests
	 * @throws ToxException
	 *             if the instance has been killed
	 */
	public void setOnFriendRequestCallback(OnFriendRequestCallback callback)
			throws ToxException {

		lock.lock();
		try {
			if (!isValidPointer(this.messengerPointer)) {
				throw new ToxException(ToxError.TOX_KILLED_INSTANCE);
			}

			tox_onfriendrequest(this.messengerPointer, callback);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Native call to tox_kill
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 */
	private native void tox_kill(long messengerPointer);

	/**
	 * Kills the current instance, triggering a cleanup of all internal data
	 * structures. All subsequent calls on any method in this class will result
	 * in a {@link ToxException} with {@link ToxError#TOX_KILLED_INSTANCE} as an
	 * error code.
	 * 
	 * @throws ToxException
	 *             in case the instance has already been killed
	 */
	public void killTox() throws ToxException {

		lock.lock();
		try {
			if (!isValidPointer(this.messengerPointer)) {
				throw new ToxException(ToxError.TOX_KILLED_INSTANCE);
			}
			validPointers.remove(this.messengerPointer);

			tox_kill(this.messengerPointer);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Native call to tox_callback_friendmessage
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param callback
	 *            the callback to set for receiving messages
	 */
	private native void tox_onfriendmessage(long messengerPointer,
			OnMessageCallback callback);

	/**
	 * Method used to set a callback method for receiving messages. Any time a
	 * message is received on this Tox instance, the
	 * {@link OnMessageCallback#execute(int, String)} method will be executed.
	 * 
	 * @param callback
	 *            the callback to set for receiving messages
	 * @throws ToxException
	 *             if the instance has been killed
	 */
	public void setOnMessageCallback(OnMessageCallback callback)
			throws ToxException {

		lock.lock();
		try {
			if (!isValidPointer(this.messengerPointer)) {
				throw new ToxException(ToxError.TOX_KILLED_INSTANCE);
			}

			tox_onfriendmessage(this.messengerPointer, callback);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Native call to tox_callback_action
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param callback
	 *            the callback to set for receiving actions
	 */
	private native void tox_onaction(long messengerPointer,
			OnActionCallback callback);

	/**
	 * Method used to set a callback method for receiving actions. Any time an
	 * action is received on this Tox instance, the
	 * {@link OnActionCallback#execute(int, String)} method will be executed.
	 * 
	 * @param callback
	 *            the callback to set for receivin actions
	 * @throws ToxException
	 *             if the instance has been killed
	 */
	public void setOnActionCallback(OnActionCallback callback)
			throws ToxException {

		lock.lock();
		try {
			if (!isValidPointer(this.messengerPointer)) {
				throw new ToxException(ToxError.TOX_KILLED_INSTANCE);
			}

			tox_onaction(this.messengerPointer, callback);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Native call to tox_callback_namechange
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param callback
	 *            the callback to set for receiving name changes
	 */
	private native void tox_onnamechange(long messengerPointer,
			OnNameChangeCallback callback);

	/**
	 * Method used to set a callback method for receiving name changes. Any time
	 * a name change is received on this tox instance, the
	 * {@link OnNameChangeCallback#execute(int, String)} method will be executed
	 * 
	 * @param callback
	 *            the callback to set for receiving name changes
	 * @throws ToxException
	 *             if the instance has been killed
	 */
	public void setOnNameChangeCallback(OnNameChangeCallback callback)
			throws ToxException {

		lock.lock();
		try {
			if (!isValidPointer(this.messengerPointer)) {
				throw new ToxException(ToxError.TOX_KILLED_INSTANCE);
			}

			tox_onnamechange(this.messengerPointer, callback);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Native call to tox_callback_statusmessage
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param callback
	 *            the callback to set for receiving status message changes
	 */
	private native void tox_onstatusmessage(long messengerPointer,
			OnStatusMessageCallback callback);

	/**
	 * Method used to set a callback method for receiving status message
	 * changes. Any time a status message change is received on ths tox
	 * instance, the {@link OnStatusMessageCallback#execute(int, String)} method
	 * will be executed
	 * 
	 * @param callback
	 *            the callback to set for receiving status message changes
	 * @throws ToxException
	 *             if the instance has been killed
	 */
	public void setOnStatusMessageCallback(OnStatusMessageCallback callback)
			throws ToxException {
		lock.lock();
		try {
			if (!isValidPointer(this.messengerPointer)) {
				throw new ToxException(ToxError.TOX_KILLED_INSTANCE);
			}
			tox_onstatusmessage(this.messengerPointer, callback);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Native call to tox_callback_userstatus
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param callback
	 *            the callback to set for receiving user status changes
	 */
	private native void tox_on_userstatus(long messengerPointer,
			OnUserStatusCallback callback);

	/**
	 * Method used to set a callback method for receiving user status changes.
	 * Any time a user status change is received on this tox instance, the
	 * {@link OnUserStatusCallback#execute(int, ToxUserStatus)} method will be
	 * executed
	 * 
	 * @param callback
	 *            callback to set for receiving user status changes
	 * @throws ToxException
	 *             if the instance has been killed
	 */
	public void setOnUserStatusCallback(OnUserStatusCallback callback)
			throws ToxException {
		lock.lock();
		try {
			if (!isValidPointer(this.messengerPointer)) {
				throw new ToxException(ToxError.TOX_KILLED_INSTANCE);
			}
			tox_on_userstatus(this.messengerPointer, callback);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Native call to tox_callback_read_receipt
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param callback
	 *            the callback to set for receiving read receipts
	 */
	private native void tox_on_read_receipt(long messengerPointer,
			OnReadReceiptCallback callback);

	/**
	 * Method used to set a callback method for receiving read receipts. Any
	 * time a read receipt is received on this tox instance, the
	 * {@link OnReadReceiptCallback#execute(int, int)}, method will be executed
	 * 
	 * @param callback
	 *            the callback to set for receiving read receipts
	 * @throws ToxException
	 *             if the instance has been killed
	 */
	public void setOnReadReceiptCallback(OnReadReceiptCallback callback)
			throws ToxException {
		lock.lock();
		try {
			if (!isValidPointer(this.messengerPointer)) {
				throw new ToxException(ToxError.TOX_KILLED_INSTANCE);
			}
			tox_on_read_receipt(this.messengerPointer, callback);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Native call to tox_callback_connectionstatus
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param callback
	 *            the callback to set for receiving receipts
	 */
	private native void tox_on_connectionstatus(long messengerPointer,
			OnConnectionStatusCallback callback);

	/**
	 * Method used to set a callback method for receiving connection status
	 * changes. Any time a connection status change is received on this tox
	 * instance, the {@link OnConnectionStatusCallback#execute(int, boolean)}
	 * method will be executed
	 * 
	 * @param callback
	 *            the callback to set for receiving connection status changes
	 * @throws ToxException
	 *             if the instance has been killed
	 */
	public void setOnConnectionStatusCallback(
			OnConnectionStatusCallback callback) throws ToxException {
		lock.lock();
		try {
			if (!isValidPointer(this.messengerPointer)) {
				throw new ToxException(ToxError.TOX_KILLED_INSTANCE);
			}
			tox_on_connectionstatus(this.messengerPointer, callback);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Native call to tox_friendstatus
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param friendnumber
	 *            the number of the friend
	 * @return the proper {@link ToxStatus} of the specified friend
	 */
	private native ToxStatus tox_friendstatus(long messengerPointer,
			int friendnumber);

	/**
	 * Method to get the specified friend's status.
	 * {@link ToxStatus#TOX_FRIEND_ADDED} means we added the friend, but the
	 * friend request was not yet sent, possibly due to a network error, or
	 * because the friend is offline<br>
	 * {@link ToxStatus#TOX_FRIEND_REQUESTED} means we added the friend, and the
	 * friend request was sent to him<br>
	 * {@link ToxStatus#TOX_FRIEND_CONFIRMED} means we connected to this friend
	 * at least once, or called {@link JTox#confirmRequest(String)} with the
	 * public key of this friend<br>
	 * {@link ToxStatus#TOX_NOFRIEND} means there is no friend with that number
	 * {@link ToxStatus#TOX_FRIEND_ONLINE} means that friend is online
	 * 
	 * @param friendnumber
	 *            the number of the friend
	 * @return a {@link ToxStatus} as described above
	 * @throws ToxException
	 *             if the instance has been killed
	 */
	public ToxStatus getFriendStatus(int friendnumber) throws ToxException {
		lock.lock();
		try {
			if (!isValidPointer(this.messengerPointer)) {
				throw new ToxException(ToxError.TOX_KILLED_INSTANCE);
			}
			return tox_friendstatus(this.messengerPointer, friendnumber);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Native call to tox_delfriend
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param friendnumber
	 *            the number of the friend
	 * @return true on success, false on failure
	 */
	private native boolean tox_delfriend(long messengerPointer, int friendnumber);

	/**
	 * Method used to delete a friend
	 * 
	 * @param friendnumber
	 *            the number of the friend
	 * @return true on success, false on failure
	 * @throws ToxException
	 *             if the instance has been killed
	 */
	public boolean deleteFriend(int friendnumber) throws ToxException {
		lock.lock();
		try {
			if (!isValidPointer(this.messengerPointer)) {
				throw new ToxException(ToxError.TOX_KILLED_INSTANCE);
			}
			return tox_delfriend(this.messengerPointer, friendnumber);
		} finally {
			lock.unlock();
		}
	}
}
