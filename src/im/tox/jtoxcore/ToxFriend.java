package im.tox.jtoxcore;

/**
 * Class representing a friend in Tox. This is a pure data class, it has no
 * logic, and should be used solely as a container. If you need functionality of
 * any kind, extend this class, and pass the subtypes to all methods that
 * require an instance of this class.
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

	/**
	 * Construct a {@link ToxFriend} with the specified data
	 * 
	 * @param name
	 *            the name
	 * @param nickName
	 *            the nickName
	 * @param id
	 *            the id
	 * @param friendnumber
	 *            the friendnumber
	 * @param status
	 *            the status
	 * @param statusMessage
	 *            the statusMessage
	 * @param sendReceipts
	 *            whether or not to send receipts
	 */
	public ToxFriend(String name, String nickName, String id, int friendnumber,
			ToxUserStatus status, String statusMessage, boolean sendReceipts) {
		this.name = name;
		this.nickName = nickName;
		this.id = id;
		this.friendnumber = friendnumber;
		this.status = status;
		this.statusMessage = statusMessage;
		this.sendReceipts = sendReceipts;
	}

	/**
	 * Default constructor for a ToxFriend. The default friend number is 0, the
	 * default value for sending receipts is <code>true</code>, and the default
	 * status is {@link ToxUserStatus#TOX_USERSTATUS_NONE}
	 */
	public ToxFriend() {
		this.sendReceipts = true;
		this.status = ToxUserStatus.TOX_USERSTATUS_NONE;
		this.friendnumber = 0;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the friendnumber
	 */
	public int getFriendnumber() {
		return friendnumber;
	}

	/**
	 * @param friendnumber
	 *            the friendnumber to set
	 */
	public void setFriendnumber(int friendnumber) {
		this.friendnumber = friendnumber;
	}

	/**
	 * @return the status
	 */
	public ToxUserStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(ToxUserStatus status) {
		this.status = status;
	}

	/**
	 * @return the statusMessage
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	/**
	 * @param statusMessage
	 *            the statusMessage to set
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	/**
	 * @return the sendReceipts
	 */
	public boolean isSendReceipts() {
		return sendReceipts;
	}

	/**
	 * @param sendReceipts
	 *            the sendReceipts to set
	 */
	public void setSendReceipts(boolean sendReceipts) {
		this.sendReceipts = sendReceipts;
	}
}
