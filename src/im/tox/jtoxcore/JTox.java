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

import im.tox.jtoxcore.callbacks.CallbackHandler;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
	//TODO Friend list handling
	/**
	 * Maximum length of a status message in Bytes. Non-ASCII characters take
	 * multiple Bytes.
	 */
	public static final int TOX_MAX_STATUSMESSAGE_LENGTH = 128;

	/**
	 * Maximum length of a nickname in Bytes. Non-ASCII characters take multiple
	 * Bytes.
	 */
	public static final int TOX_MAX_NICKNAME_LENGTH = 128;

	static {
		System.loadLibrary("jtoxcore");
	}

	/**
	 * List containing all currently active tox instances
	 */
	private static List<Long> validPointers = Collections
			.synchronizedList(new ArrayList<Long>());

	private static Map<Integer, JTox> instances = new HashMap<Integer, JTox>();
	private static ReentrantLock instanceLock = new ReentrantLock();
	private static int instanceCounter = 0;
	private final int instanceNumber;

	private CallbackHandler<?> handler;
	private FriendList<?> friendList;

	/**
	 * This field contains the lock used for thread safety
	 */
	private final ReentrantLock lock;

	private List<ToxFriend> friends;

	/**
	 * This field contains the pointer used in all native tox_ method calls.
	 */
	private final long messengerPointer;

	/**
	 * Utility method that checks the current pointer and throws an exception if
	 * it is not valid
	 * 
	 * @throws ToxException
	 *             if the instance has been killed
	 */
	private void checkPointer() throws ToxException {
		if (!validPointers.contains(this.messengerPointer)) {
			throw new ToxException(ToxError.TOX_KILLED_INSTANCE);
		}
	}

	/**
	 * If you need to pass a JTox instance around between different contexts,
	 * and are unable to pass instances directly, use this method to acquire the
	 * instance number of this instance. Once acquired, you can acquire the
	 * instance with {@link JTox#getInstance(int)}.
	 * 
	 * @return the instance number
	 */
	public int getInstanceNumber() {
		return this.instanceNumber;
	}

	/**
	 * Get the instance associated with the specified instance number. This may
	 * return <code>null</code> if either the instance was killed, or if no
	 * instance with that number exists.
	 * 
	 * @param instancenumber
	 * @return the associated instance
	 */
	public static JTox getInstance(int instancenumber) {
		return instances.get(instancenumber);
	}

	/**
	 * Turns the given String into an array of UTF-8 encoded bytes, also adding
	 * a nullbyte at the end for convenience
	 * 
	 * @param in
	 *            the String to convert
	 * @return a byte array
	 * @throws ToxException
	 *             if the UTF-8 encoding is not supported
	 */
	public static byte[] getStringBytes(String in) throws ToxException {
		try {
			return in.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			ToxException e1 = new ToxException(ToxError.TOX_UNKNOWN);
			e1.initCause(e);
			throw e1;
		}
	}

	/**
	 * Turns the given byte array into a UTF-8 encoded string
	 * 
	 * @param in
	 *            the byte array to convert
	 * @return an UTF-8 String based on the given byte array
	 */
	public static String getByteString(byte[] in) {
		try {
			return new String(in, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			RuntimeException e1 = new RuntimeException("We need UTF-8. Sorry.");
			e1.initCause(e);
			throw e1;
		}
	}

	/**
	 * Convert a given hexadecimal String to a byte array.
	 * 
	 * @param in
	 *            String to convert
	 * @return byte array representation of the hexadecimal String
	 */
	public static byte[] hexToByteArray(String in) {
		int length = in.length() / 2;
		byte[] out = new byte[length];
		for (int i = 0; i < length; i += 2) {
			out[i / 2] = (byte) ((Character.digit(in.charAt(i), 16) << 4) + Character
					.digit(in.charAt(i + 1), 16));
		}
		return out;
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
	 * @throws ToxException
	 *             when the native call indicates an error
	 */
	public JTox(FriendList<?> friendList, CallbackHandler<?> handler)
			throws ToxException {
		this.friendList = friendList;
		this.handler = handler;
		long pointer = tox_new();
		if (pointer == 0) {
			throw new ToxException(ToxError.TOX_UNKNOWN);
		} else {
			this.messengerPointer = pointer;
			this.lock = new ReentrantLock();
			validPointers.add(pointer);
			instanceLock.lock();
			this.instanceNumber = instanceCounter++;
			instances.put(instanceCounter, this);
			instanceLock.unlock();
		}
	}

	/**
	 * Creates a new instance of JTox and stores the pointer to the internal
	 * struct in messengerPointer. Also attempts to load the specified byte
	 * array into this instance.
	 * 
	 * @param data
	 *            the data to load for the new tox instance
	 * @throws ToxException
	 *             when the native call indicates an error
	 */
	public JTox(byte[] data, FriendList<?> friendList,
			CallbackHandler<?> handler) throws ToxException {
		this(friendList, handler);
		this.load(data);
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
	private native int tox_addfriend(long messengerPointer, byte[] address,
			byte[] data, int length);

	/**
	 * Method used to add a friend. On success, the friend is added to the list
	 * of friends, and a reference to the friend is returned.
	 * 
	 * @param address
	 *            the address of the friend you want to add
	 * @param data
	 *            an optional message you want to send to your friend
	 * @return the friend
	 * @throws ToxException
	 *             if the instance has been killed or an error code is returned
	 *             by the native tox_addfriend call
	 */
	public ToxFriend addFriend(String address, String data) throws ToxException {
		byte[] dataArray = getStringBytes(data);
		byte[] addressArray = hexToByteArray(address);
		this.lock.lock();
		int errcode;
		try {
			checkPointer();

			errcode = tox_addfriend(this.messengerPointer, addressArray,
					dataArray, dataArray.length);

		} finally {
			this.lock.unlock();
		}

		if (errcode >= 0) {
			ToxFriend friend = new ToxFriend(errcode);
			this.friends.add(friend);
			return friend;
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
			byte[] address);

	/**
	 * Confirm a friend request, or add a friend to your own list without
	 * sending them a friend request. If successful, the Friend is added to the
	 * list, and a reference to the friend is returned.
	 * 
	 * @param address
	 *            address of the friend to add
	 * @return the friend
	 * @throws ToxException
	 *             if the instance was killed or an error occurred when adding
	 *             the friend
	 */
	public ToxFriend confirmRequest(String address) throws ToxException {
		byte[] addressArray = hexToByteArray(address);
		this.lock.lock();
		int errcode;
		try {
			checkPointer();

			errcode = tox_addfriend_norequest(this.messengerPointer,
					addressArray);
		} finally {
			this.lock.unlock();
		}

		if (errcode >= 0) {
			ToxFriend friend = new ToxFriend(errcode);
			this.friends.add(friend);
			return friend;
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
		this.lock.lock();
		String address;
		try {
			checkPointer();
			address = tox_getaddress(this.messengerPointer);
		} finally {
			this.lock.unlock();
		}

		if (address == null || address.equals("")) {
			throw new ToxException(ToxError.TOX_UNKNOWN);
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
	private native int tox_getfriend_id(long messengerPointer, byte[] clientid);

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
	public ToxFriend getFriend(String clientid) throws ToxException {
		byte[] clientArray = hexToByteArray(clientid);
		this.lock.lock();
		int errcode;
		try {
			checkPointer();

			errcode = tox_getfriend_id(this.messengerPointer, clientArray);
		} finally {
			this.lock.unlock();
		}

		if (errcode == -1) {
			throw new ToxException(ToxError.TOX_UNKNOWN);
		} else {
			return this.friends.get(errcode);
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
	 * Get the client id for a given Friend, and update that friends friend ID
	 * to the returned value
	 * 
	 * @param friendnumber
	 *            the friend's number
	 * @return client id for the given friend
	 * @throws ToxException
	 *             if the instance has been killed, or an error occurred when
	 *             attempting to fetch the client id
	 */
	public String getClientId(int friendnumber) throws ToxException {
		this.lock.lock();
		String result;
		try {
			checkPointer();

			result = tox_getclient_id(this.messengerPointer, friendnumber);
		} finally {
			this.lock.unlock();
		}

		if (result == null || result.equals("")) {
			throw new ToxException(ToxError.TOX_UNKNOWN);
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
		this.lock.lock();
		try {
			checkPointer();

			tox_do(this.messengerPointer);
		} finally {
			this.lock.unlock();
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
	private native int tox_bootstrap(long messengerPointer, String ip,
			int port, byte[] pubkey);

	/**
	 * Method used to bootstrap the client's connection.
	 * 
	 * @param host
	 *            Hostname or IP(v4, v6) address to connect to. If the hostname
	 *            contains non-ASCII characters, convert it to punycode when
	 *            calling this method.
	 * @param port
	 *            port to connect to
	 * @param pubkey
	 *            public key of the bootstrap node
	 * @throws ToxException
	 *             if the instance has been killed or an invalid port was
	 *             specified
	 * @throws UnknownHostException
	 *             if the host could not be resolved or the IP address was
	 *             invalid
	 */
	public void bootstrap(String host, int port, String pubkey)
			throws ToxException, UnknownHostException {
		if (port < 0 || port > 65535) {
			throw new ToxException(ToxError.TOX_INVALID_PORT);
		}
		byte[] pubkeyArray = hexToByteArray(pubkey);
		this.lock.lock();
		try {
			checkPointer();

			if (tox_bootstrap(this.messengerPointer, host, port, pubkeyArray) == 0) {
				throw new UnknownHostException(host);
			}

		} finally {
			this.lock.unlock();
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
		this.lock.lock();
		try {
			checkPointer();

			if (tox_isconnected(this.messengerPointer) == 0) {
				return false;
			} else {
				return true;
			}
		} finally {
			this.lock.unlock();
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
		this.lock.lock();
		try {
			checkPointer();

			validPointers.remove(this.messengerPointer);
			tox_kill(this.messengerPointer);
		} finally {
			this.lock.unlock();
		}
		instances.remove(this.instanceNumber);
	}

	/**
	 * Native call to tox_delfriend
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param friendnumber
	 *            the number of the friend
	 * @return false on success, true on failure
	 */
	private native boolean tox_delfriend(long messengerPointer, int friendnumber);

	/**
	 * Method used to delete a friend
	 * 
	 * @param friendnumber
	 *            the friend to delete
	 * @throws ToxException
	 *             if the instance has been killed or an error occurred
	 */
	public void deleteFriend(int friendnumber) throws ToxException {
		this.lock.lock();
		try {
			checkPointer();

			if (tox_delfriend(this.messengerPointer, friendnumber)) {
				throw new ToxException(ToxError.TOX_UNKNOWN);
			}
		} finally {
			this.lock.unlock();
		}
	}

	/**
	 * Native call to tox_sendmessage
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param friendnumber
	 *            the number of the friend
	 * @param message
	 *            the message
	 * @param length
	 *            length of the message in bytes
	 * @return the message ID on success, 0 on failure
	 */
	private native int tox_sendmessage(long messengerPointer, int friendnumber,
			byte[] message, int length);

	/**
	 * Sends a message to the specified friend
	 * 
	 * @param friendnumber
	 *            the friend's number
	 * @param message
	 *            the message
	 * @return the message ID of the sent message. If you want to receive read
	 *         receipts, hang on to this value.
	 * @throws ToxException
	 *             if the instance has been killed or the message was not sent
	 */
	public int sendMessage(int friendnumber, String message)
			throws ToxException {
		this.lock.lock();
		byte[] messageArray = getStringBytes(message);
		int result;
		try {
			checkPointer();

			result = tox_sendmessage(this.messengerPointer, friendnumber,
					messageArray, messageArray.length);
		} finally {
			this.lock.unlock();
		}

		if (result == 0) {
			throw new ToxException(ToxError.TOX_SEND_FAILED);
		} else {
			return result;
		}

	}

	/**
	 * Native call to tox_sendmessage_withid
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param friendnumber
	 *            the number of the friend
	 * @param message
	 *            the message
	 * @param length
	 *            length of the message in bytes
	 * 
	 * @param messageID
	 *            the message ID to use
	 * @return the message ID on success, 0 on failure
	 */
	private native int tox_sendmessage(long messengerPointer, int friendnumber,
			byte[] message, int length, int messageID);

	/**
	 * Sends a message to the specified friend, with a specified ID
	 * 
	 * @param friendnumber
	 *            the friend's number
	 * @param message
	 *            the message
	 * @param messageID
	 *            the message ID to use
	 * @return the message ID of the sent message. If you want to receive read
	 *         receipts, hang on to this value.
	 * @throws ToxException
	 *             if the instance has been killed or the message was not sent.
	 */
	public int sendMessage(int friendnumber, String message, int messageID)
			throws ToxException {
		byte[] messageArray = getStringBytes(message);
		this.lock.lock();
		int result;
		try {
			checkPointer();

			result = tox_sendmessage(this.messengerPointer, friendnumber,
					messageArray, messageArray.length, messageID);

		} finally {
			this.lock.unlock();
		}

		if (result == 0) {
			throw new ToxException(ToxError.TOX_SEND_FAILED);
		} else {
			return result;
		}
	}

	/**
	 * Native call to tox_setname
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param newname
	 *            the new name
	 * @param length
	 *            length of the new name in byte
	 * @return false on success, true on failure
	 */
	private native boolean tox_setname(long messengerPointer, byte[] newname,
			int length);

	/**
	 * Sets our nickname
	 * 
	 * @param newname
	 *            the new name to set. Maximum length is 128 bytes. This means
	 *            that a name containing UTF-8 characters has a shorter
	 *            character limit than one only using ASCII.
	 * @throws ToxException
	 *             if the instance was killed, the name was too long, or another
	 *             error occurred
	 */
	public void setName(String newname) throws ToxException {
		this.lock.lock();
		byte[] newnameArray = getStringBytes(newname);
		if (newnameArray.length >= TOX_MAX_NICKNAME_LENGTH) {
			throw new ToxException(ToxError.TOX_TOOLONG);
		}
		try {
			checkPointer();

			if (tox_setname(this.messengerPointer, newnameArray,
					newnameArray.length)) {
				throw new ToxException(ToxError.TOX_UNKNOWN);
			}
		} finally {
			this.lock.unlock();
		}
	}

	/**
	 * Native call to tox_sendaction
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param friendnumber
	 *            the number of the friend
	 * @param action
	 *            the action to send
	 * @param length
	 *            length of the action in bytes
	 * @return false on success, true on failure
	 */
	private native boolean tox_sendaction(long messengerPointer,
			int friendnumber, byte[] action, int length);

	/**
	 * Sends an IRC-like /me-action to a friend
	 * 
	 * @param friendnumber
	 *            the friend's number
	 * @param action
	 *            the action
	 * @throws ToxException
	 *             if the instance has been killed or the send failed
	 */
	public void sendAction(int friendnumber, String action) throws ToxException {
		this.lock.lock();
		byte[] actionArray = getStringBytes(action);
		try {
			checkPointer();

			if (tox_sendaction(this.messengerPointer, friendnumber,
					actionArray, actionArray.length)) {
				throw new ToxException(ToxError.TOX_SEND_FAILED);
			}
		} finally {
			this.lock.unlock();
		}
	}

	/**
	 * Native call to tox_getselfname
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @return our name
	 */
	private native String tox_getselfname(long messengerPointer);

	/**
	 * Function to get our current name
	 * 
	 * @return our name
	 * @throws ToxException
	 *             if the instance has been killed
	 */
	public String getSelfName() throws ToxException {
		this.lock.lock();
		String name;
		try {
			checkPointer();

			name = tox_getselfname(this.messengerPointer);
		} finally {
			this.lock.unlock();
		}

		if (name == null) {
			throw new ToxException(ToxError.TOX_UNKNOWN);
		} else {
			return name;
		}

	}

	/**
	 * Native call to tox_set_statusmessage
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param message
	 *            our new status message
	 * @param length
	 *            the length of the new status message in bytes
	 * @return false on success, true on failure
	 */
	private native boolean tox_set_statusmessage(long messengerPointer,
			byte[] message, int length);

	/**
	 * Sets our status message
	 * 
	 * @param message
	 *            our new status message
	 * @throws ToxException
	 *             if the instance has been killed, the message was too long, or
	 *             another error occurred
	 */
	public void setStatusMessage(String message) throws ToxException {
		this.lock.lock();
		byte[] messageArray = getStringBytes(message);
		if (messageArray.length >= TOX_MAX_STATUSMESSAGE_LENGTH) {
			throw new ToxException(ToxError.TOX_TOOLONG);
		}
		try {
			checkPointer();

			if (tox_set_statusmessage(this.messengerPointer, messageArray,
					messageArray.length)) {
				throw new ToxException(ToxError.TOX_UNKNOWN);
			}
		} finally {
			this.lock.unlock();
		}
	}

	/**
	 * Native call to tox_getname
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param friendnumber
	 *            the friend's number
	 * @return the specified friend's name
	 */
	private native byte[] tox_getname(long messengerPointer, int friendnumber);

	/**
	 * Get the specified friend's name
	 * 
	 * @param friendnumber
	 *            the friend's number
	 * @return the friend's name
	 * @throws ToxException
	 *             if the instance has been killed or an error occurred
	 */
	public String getName(int friendnumber) throws ToxException {
		this.lock.lock();
		byte[] name;
		try {
			checkPointer();

			name = tox_getname(this.messengerPointer, friendnumber);
		} finally {
			this.lock.unlock();
		}

		if (name == null) {
			throw new ToxException(ToxError.TOX_UNKNOWN);
		} else {
			return getByteString(name);
		}
	}

	/**
	 * Native call to tox_set_userstatus
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param status
	 *            status to set
	 * @return false on success, true on failure
	 */
	private native boolean tox_set_userstatus(long messengerPointer, int status);

	/**
	 * Set our current {@link ToxUserStatus}.
	 * 
	 * @param status
	 *            the status to set
	 * @throws ToxException
	 *             if the instance was killed, or an error occurred while stting
	 *             status
	 */
	public void setUserStatus(ToxUserStatus status) throws ToxException {
		this.lock.lock();
		try {
			checkPointer();

			if (tox_set_userstatus(this.messengerPointer, status.ordinal())) {
				throw new ToxException(ToxError.TOX_UNKNOWN);
			}
		} finally {
			this.lock.unlock();
		}
	}

	/**
	 * Native call to tox_copy_statusmessage
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param friendnumber
	 *            the friend's number
	 * @return the status message
	 */
	private native byte[] tox_getstatusmessage(long messengerPointer,
			int friendnumber);

	/**
	 * Get the friend's status message
	 * 
	 * @param friendnumber
	 *            the friend's number
	 * @return the friend's status message
	 * @throws ToxException
	 *             if the instance has been killed, or an error occurred while
	 *             getting the status message
	 */
	public String getStatusMessage(int friendnumber) throws ToxException {
		this.lock.lock();
		byte[] status;
		try {
			checkPointer();

			status = tox_getstatusmessage(this.messengerPointer, friendnumber);
		} finally {
			this.lock.unlock();
		}

		if (status == null) {
			throw new ToxException(ToxError.TOX_UNKNOWN);
		} else {
			return getByteString(status);
		}
	}

	/**
	 * Native call to tox_friendexists
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param friendnumber
	 *            the friend's number
	 * @return true if friend exists, false otherwise
	 */
	private native boolean tox_friendexists(long messengerPointer,
			int friendnumber);

	/**
	 * Check if the specified friend exists
	 * 
	 * @param friendnumber
	 *            the friend's number
	 * @return true if friend exists, false otherwise
	 * @throws ToxException
	 *             if the instance has been killed
	 */
	public boolean friendExists(int friendnumber) throws ToxException {
		this.lock.lock();
		try {
			checkPointer();

			return tox_friendexists(this.messengerPointer, friendnumber);
		} finally {
			this.lock.unlock();
		}
	}

	/**
	 * Native call to tox_copy_self_statusmessage
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @return our current status message
	 */
	private native byte[] tox_getselfstatusmessage(long messengerPointer);

	/**
	 * Gets our own status message
	 * 
	 * @return our current status message
	 * @throws ToxException
	 */
	public String getStatusMessage() throws ToxException {
		this.lock.lock();
		byte[] message;
		try {
			checkPointer();

			message = tox_getselfstatusmessage(this.messengerPointer);
		} finally {
			this.lock.unlock();
		}

		if (message == null) {
			throw new ToxException(ToxError.TOX_UNKNOWN);
		} else {
			return getByteString(message);
		}
	}

	/**
	 * Native call to tox_get_userstatus
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param friendnumber
	 *            the friend's number
	 * @return the friend's status
	 */
	private native ToxUserStatus tox_get_userstatus(long messengerPointer,
			int friendnumber);

	/**
	 * Get the current status for the specified friend
	 * 
	 * @param friendnumber
	 *            the friend's number
	 * @return the friend's status
	 * @throws ToxException
	 *             if the instance has been killed
	 */
	public ToxUserStatus getUserStatus(int friendnumber) throws ToxException {
		this.lock.lock();
		try {
			checkPointer();

			return tox_get_userstatus(this.messengerPointer, friendnumber);
		} finally {
			this.lock.unlock();
		}
	}

	/**
	 * Native call to tox_get_selfuserstatus
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @return our current status
	 * @throws ToxException
	 *             if the instance has been killed
	 */
	private native ToxUserStatus tox_get_selfuserstatus(long messengerPointer);

	/**
	 * Get our own current status
	 * 
	 * @return one of {@link ToxUserStatus}
	 * @throws ToxException
	 */
	public ToxUserStatus getSelfUserStatus() throws ToxException {
		this.lock.lock();
		try {
			checkPointer();

			return tox_get_selfuserstatus(this.messengerPointer);
		} finally {
			this.lock.unlock();
		}
	}

	/**
	 * Native call to tox_sends_receipts
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param sendReceipts
	 *            <code>true</code> to send receipts, <code>false</code>
	 *            otherwise
	 * @param friendnumber
	 *            the friend's number
	 */
	private native void tox_set_sends_receipts(long messengerPointer,
			boolean sendReceipts, int friendnumber);

	/**
	 * Set whether or not to send read receipts to the originator of a message
	 * once we received a message. This defaults to <code>true</code>, and must
	 * be disabled manually for each friend, if not required
	 * 
	 * @param sendReceipts
	 *            <code>true</code> to send receipts, <code>false</code>
	 *            otherwise
	 * @param friendnumber
	 *            the friend's number
	 * @throws ToxException
	 *             if the instance has been killed
	 */
	public void setSendReceipts(boolean sendReceipts, int friendnumber)
			throws ToxException {
		this.lock.lock();
		try {
			checkPointer();

			tox_set_sends_receipts(this.messengerPointer, sendReceipts,
					friendnumber);
		} finally {
			this.lock.unlock();
		}
	}

	/**
	 * Native call to tox_save
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @return a byte array containing the saved data
	 */
	private native byte[] tox_save(long messengerPointer);

	/**
	 * Save the internal messenger data to a byte array, which can be saved to a
	 * file or database
	 * 
	 * @return a byte array containing the saved data
	 * @throws ToxException
	 *             if the instance has been killed
	 */
	public byte[] save() throws ToxException {
		this.lock.lock();
		try {
			checkPointer();

			return tox_save(this.messengerPointer);
		} finally {
			this.lock.unlock();
		}
	}

	/**
	 * Native call to tox_load
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @param data
	 *            a byte array containing the data to load
	 * @param length
	 *            the length of the byte array
	 * @return false on success, true on failure
	 */
	private native boolean tox_load(long messengerPointer, byte[] data,
			int length);

	/**
	 * Load the specified data into this tox instance.
	 * 
	 * @param data
	 *            a byte array containing the data to load
	 * @throws ToxException
	 *             if the instance has been killed, or an error occurred while
	 *             loading
	 */
	public void load(byte[] data) throws ToxException {
		this.lock.lock();
		try {
			checkPointer();

			if (tox_load(this.messengerPointer, data, data.length)) {
				throw new ToxException(ToxError.TOX_UNKNOWN);
			}
		} finally {
			this.lock.unlock();
		}
	}

	/**
	 * Native call to tox_get_friendlist
	 * 
	 * @param messengerPointer
	 *            pointer to the internal messenger struct
	 * @return array containing valid friend numbers
	 */
	private native int[] tox_get_friendlist(long messengerPointer);

	/**
	 * Get a list of all currently valid friend numbers.
	 * 
	 * @return ArrayList containing the
	 * @throws ToxException
	 *             if the instance was killed, or the list was not retrieved
	 */
	public ArrayList<Integer> getFriendList() throws ToxException {
		this.lock.lock();
		int[] ids;
		try {
			checkPointer();

			ids = tox_get_friendlist(this.messengerPointer);
		} finally {
			this.lock.unlock();
		}

		if (ids == null) {
			throw new ToxException(ToxError.TOX_UNKNOWN);
		} else {

			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int i : ids) {
				list.add(i);
			}
			return list;
		}
	}
}
