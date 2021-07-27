package in.co.exception;


/**
 * ApplicationException is propogated from Service classes when an business
 * logic exception occured.
 */
public class ApplicationException extends Exception {
	
	/**
	 * Instantiates a new application exception.
	 *
	 * @param msg            : Error message
	 */
	public ApplicationException(String msg) {
		super(msg);
		
		
	}

}
