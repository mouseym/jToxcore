package im.tox.jtoxcore;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Wrapper class for the Tox Friendlist. This class is iterable, however the
 * iterated dataset might be out of date, if manipulations are made to the list
 * from the outside while iterating.
 * 
 * @author sonOfRa
 * 
 */
public class ToxFriendList implements Iterable<ToxFriend> {
	
	/**
	 * Underlying friend list
	 */
	private ArrayList<ToxFriend> friends;
	
	/**
	 * Count modifications for the underlying list to ensure 
	 */
	private transient int modCount;
	
	/**
	 * Create a new, empty ToxFriendList instance
	 */
	public ToxFriendList() {
		this.friends = new ArrayList<ToxFriend>();
	}

	/**
	 * Create a new ToxFriendList instance with the given List of Friends as an
	 * underlying structure
	 * 
	 * @param friends
	 *            initial list of friends to use
	 */
	public ToxFriendList(ArrayList<ToxFriend> friends) {
		this.friends = friends;
	}

	@Override
	public Iterator<ToxFriend> iterator() {
		return new Iterator<ToxFriend>() {
			private int cursor = 0;
			private int expectedModCount = ToxFriendList.this.modCount;

			@Override
			public boolean hasNext() {
				checkForComodification();
				return this.cursor != ToxFriendList.this.friends.size();
			}

			@Override
			public ToxFriend next() {
				checkForComodification();
				if(this.cursor >= ToxFriendList.this.friends.size()) {
					throw new NoSuchElementException();
				}
				
				Object[] elements = ToxFriendList.this.friends.toArray();
				if(this.cursor >= elements.length) {
					throw new ConcurrentModificationException();
				}
				
				return (ToxFriend) elements[this.cursor++];
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
	        final void checkForComodification() {
	            if (ToxFriendList.this.modCount != this.expectedModCount)
	                throw new ConcurrentModificationException();
	        }
		};
	}

}
