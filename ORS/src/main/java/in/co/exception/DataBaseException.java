package in.co.exception;

// TODO: Auto-generated Javadoc
/**
 * DatabaseException is propogated by DAO classes when an unhandled Database
 * exception occurred.
 */
public class DataBaseException extends Exception {
	
	/**
	 * Instantiates a new data base exception.
	 *
	 * @param msg            : Error message
	 */
	public DataBaseException(String msg) {
		super(msg);
	}
}
