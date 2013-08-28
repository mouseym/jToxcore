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

import java.net.InetSocketAddress;

import im.tox.jtoxcore.callbacks.OnFriendRequestCallback;
import im.tox.jtoxcore.callbacks.OnMessageCallback;

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
	 * This field contains the pointer used in all native tox_ method calls.
	 */
	private long messengerPointer;

	/**
	 * Create a new instance of JTox with a given messengerPointer. Due to the
	 * nature of the native calls, this call is <i>unsafe<i>. Calling it with an
	 * arbitrary long that was not obtained from another JTox instance will
	 * result in undefined behavior when calling other methods on this instance.
	 * This method is mainly aimed at Android Developers to ease passing the
	 * JTox instance around between different activities.
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 */
	private JTox(long messengerPointer) {
		this.messengerPointer = messengerPointer;
	}

	/**
	 * Native call to tox_new
	 * 
	 * @return the pointer to the messenger struct on success, 0 on failure
	 */
	private static native long tox_new();

	/**
	 * Creates a new instance of JTox and stores the pointer to the internal
	 * struct in messengerPointer. We are not using a constructor here in order
	 * to avoid partial objects being created when the native calls fail
	 * 
	 * @return a new JTox instance
	 * @throws ToxException
	 *             when the native call indicates an error
	 */
	public static JTox newTox() throws ToxException {
		long pointer = tox_new();

		if (pointer == 0) {
			throw new ToxException(ToxError.TOX_FAERR_UNKNOWN);
		} else {
			return new JTox(pointer);
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
	 *             in case an error code is returned by the native tox_addfriend
	 *             call
	 */
	public int addFriend(String address, String data) throws ToxException {
		int errcode = tox_addfriend(this.messengerPointer, address, data);

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
	 */
	private native int tox_addfriend_norequest(long messengerPointer,
			String address);

	public int confirmRequest(String address) throws ToxException {
		int errcode = tox_addfriend_norequest(this.messengerPointer, address);

		if (errcode >= 0) {
			return errcode;
		} else {
			throw new ToxException(ToxError.TOX_FAERR_UNKNOWN);
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

	public String getAddress() throws ToxException {
		String address = tox_getaddress(this.messengerPointer);

		if (address == null) {
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
	 * @throws ToxException
	 *             when the friend does not exist
	 * @return the local id of the specified friend
	 */
	public int getFriendId(String clientid) throws ToxException {
		int errcode = tox_getfriend_id(this.messengerPointer, clientid);

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

	public String getClientId(int friendnumber) throws ToxException {
		String result = tox_getclient_id(this.messengerPointer, friendnumber);

		if (result == null) {
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
	 */
	public void doTox() {
		tox_do(this.messengerPointer);
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
	 */
	public void bootstrap(InetSocketAddress address, String pubkey) {
		tox_bootstrap(messengerPointer, address.getAddress().getAddress(),
				address.getPort(), pubkey);
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
	 */
	public boolean isConnected() {
		if (tox_isconnected(messengerPointer) == 0) {
			return false;
		} else {
			return true;
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
	 */
	public void setOnFriendRequestCallback(OnFriendRequestCallback callback) {
		tox_onfriendrequest(this.messengerPointer, callback);
	}

	/**
	 * @return the pointer to the internal messenger struct, as a long
	 */
	public long getPointer() {
		return this.messengerPointer;
	}
}
