/* ToxError.java
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
 * Enum for Tox Error codes while adding friends
 * @author sonOfRa
 *
 */
public enum ToxError {
	TOX_FAERR_TOOLONG,
	TOX_FAERR_NOMESSAGE,
	TOX_FAERR_OWNKEY,
	TOX_FAERR_ALREADYSENT,
	TOX_FAERR_UNKNOWN,
	TOX_FAERR_BADCHECKSUM,
	TOX_FAERR_SETNEWNOSPAM,
	TOX_FAERR_NOMEM,
	TOX_KILLED_INSTANCE,
	TOX_SEND_FAILED;
}
