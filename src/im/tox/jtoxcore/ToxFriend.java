package im.tox.jtoxcore;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Class representing a friend in Tox. This is a pure data class, it has no
 * logic, and should be used solely as a container.
 * 
 * We are keeping a global list of these, so they can be interacted with from
 * callbacks as well as the main JTox class.
 * 
 * All actions on this are synchronized, and the lock will be the same that is
 * used for the Tox instance that this Friend belongs to, in order to ensure
 * that we have no race conditions
 * 
 * @author sonOfRa
 * 
 */
public class ToxFriend {
	private String name;
	private String nickName;
	private String id;
	private int friendnumber;
	private ToxUserStatus status;
	private String statusMessage;
	private boolean sendReceipts;
	private final ReentrantLock lock;

	/**
	 * Constructor to be used when adding a new friend
	 * 
	 * @param friendnumber
	 *            the friendnumber for the new friend
	 * @param ReentrantLock
	 *            the lock to use
	 */
	protected ToxFriend(int friendnumber, ReentrantLock lock) {
		this.sendReceipts = true;
		this.status = ToxUserStatus.TOX_USERSTATUS_NONE;
		this.friendnumber = friendnumber;
		this.lock = lock;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		lock.lock();
		try {
			return name;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		lock.lock();
		try {
			this.name = name;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		lock.lock();
		try {
			return nickName;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		lock.lock();
		try {
			this.nickName = nickName;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * @return the id
	 */
	public String getId() {
		lock.lock();
		try {
			return id;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		lock.lock();
		try {
			this.id = id;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * @return the friendnumber
	 */
	public int getFriendnumber() {
		lock.lock();
		try {
			return friendnumber;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * @param friendnumber
	 *            the friendnumber to set
	 */
	public void setFriendnumber(int friendnumber) {
		lock.lock();
		try {
			this.friendnumber = friendnumber;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * @return the status
	 */
	public ToxUserStatus getStatus() {
		lock.lock();
		try {
			return status;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(ToxUserStatus status) {
		lock.lock();
		try {
			this.status = status;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * @return the statusMessage
	 */
	public String getStatusMessage() {
		lock.lock();
		try {
			return statusMessage;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * @param statusMessage
	 *            the statusMessage to set
	 */
	public void setStatusMessage(String statusMessage) {
		lock.lock();
		try {
			this.statusMessage = statusMessage;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * @return the sendReceipts
	 */
	public boolean isSendReceipts() {
		lock.lock();
		try {
			return sendReceipts;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * @param sendReceipts
	 *            the sendReceipts to set
	 */
	public void setSendReceipts(boolean sendReceipts) {
		lock.lock();
		try {
			this.sendReceipts = sendReceipts;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * @return the lock
	 */
	public ReentrantLock getLock() {
		return lock;
	}
}
