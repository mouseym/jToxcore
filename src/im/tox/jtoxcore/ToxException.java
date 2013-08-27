/* ToxException.java
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
 * Exception class used in the Tox project. Carries an Errorcode that signals
 * the error that occurred during the execution of native code
 * 
 * @author sonOfRa
 * 
 */
public class ToxException extends Exception {
	private static final long serialVersionUID = 1L;

	private ToxError error;

	/**
	 * Create a new ToxException with the given error code
	 * 
	 * @param error
	 *            the error code to create the new Exception with
	 */
	public ToxException(ToxError error) {
		super();
		this.error = error;
	}

	/**
	 * Create a new ToxException with an error code as int
	 * 
	 * @param errcode
	 *            the error code, as an integer
	 */
	public ToxException(int errcode) {
		super();
		switch (errcode) {
		case -1:
			this.error = ToxError.TOX_FAERR_TOOLONG;
			break;
		case -2:
			this.error = ToxError.TOX_FAERR_NOMESSAGE;
			break;
		case -3:
			this.error = ToxError.TOX_FAERR_OWNKEY;
			break;
		case -4:
			this.error = ToxError.TOX_FAERR_ALREADYSENT;
			break;
		case -6:
			this.error = ToxError.TOX_FAERR_BADCHECKSUM;
			break;
		case -7:
			this.error = ToxError.TOX_FAERR_SETNEWNOSPAM;
			break;
		case -8:
			this.error = ToxError.TOX_FAERR_NOMEM;
			break;
		default:
			this.error = ToxError.TOX_FAERR_UNKNOWN;
			break;
		}
	}

	/**
	 * @return The error code for this Exception
	 */
	public ToxError getError() {
		return this.error;
	}
}
