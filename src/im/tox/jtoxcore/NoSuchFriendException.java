package im.tox.jtoxcore;

/**
 * Exception to be thrown when lookup for a friend failed.
 * 
 * @author sonOfRa
 * 
 */
public class NoSuchFriendException extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Create an Exception with the specified message
	 * @param message message for this exception
	 */
	public NoSuchFriendException(String message) {
		super(message);
	}
}
