package in.co.util;

import java.util.Date;

/**
 * This class validates input data
 */
public class DataValidator {
	/**
	 * Checks if value is Null
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isNull(String val) {

		if (val == null || val.trim().length() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean isNotNull(String val) {
		return !isNull(val);
	}

	/**
	 * Checks if value is an Integer
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isInteger(String val) {
		// String s = val.trim(); // Modified

		if (isNotNull(val)) {
			try {
				int i = Integer.parseInt(val);
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Checks if value is Long
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isLong(String val) {

		// String s = val.trim(); // Modified

		if (isNotNull(val)) {
			try {
				long l = Long.parseLong(val);
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Checks if value is valid Email ID
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isEmail(String val) {
		String emailreg = "^[_a-zA-Z0-9+]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		// modified
		if (isNotNull(emailreg)) {
			return val.matches(emailreg);

		}

		else {
			return false;
		}

	}
	/**
	 * Checks if value is valid Date
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isDate(String val) {

		Date d = null;

		if (isNotNull(val)) {

			d = DataUtility.getDate(val);
			long d1 = d.getTime();
			System.out.println("date is" + d);
			System.out.println("date is" + d1);

		}
		return d != null;
	}

	/**
	 * Checks if value is valid Name
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isName(String name) { // my method created

		//String namereg = "^[a-zA-Z]+$";

		//String namere = "^[^-\\s][\\p{L} .']+$";
		// String sname = name.trim();

		// String namer = "^/S+/w/S";
		String namer = "^[a-zA-Z_-]+$";
		if (isNotNull(name) && name.matches(namer)) {

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if value is valid Name
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isName1(String name) { // my method created

		String namere = "^[^-\\s][\\p{L} .']+$";

		if (isNotNull(name) && name.matches(namere)) {

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if value is valid Password
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isPassword(String pass) { // my method created

		System.out.println("validate pass");
		String passreg = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})";
		// String passreg="^[0-9a-zA-Z]{5}$";
		// String spass = pass.trim();
		// int checkIndex = spass.indexOf(" ");
		// checkIndex==-1
		if (isNotNull(pass) && pass.matches(passreg)) {
			System.out.println("true");
			return true;
		}

		else {
			return false;
		}
	}

	/**
	 * Checks if value is valid Address
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isAddress(String pass) { // my method created

		System.out.println("validate pass");
	//	String passreg = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20})";

//		String passreg = "^[0-9a-zA-Z/s,-]+$";
		String passreg = "^[0-9a-zA-Z\\s,-]+$";
		// String passreg="^[0-9a-zA-Z]{5}$";
		// String spass = pass.trim();
		// int checkIndex = spass.indexOf(" ");
		// checkIndex==-1
		if (isNotNull(pass) && pass.matches(passreg)) {
			System.out.println("true");
			return true;
		}

		else {
			return false;
		}
	}
	/**
	 * Checks if value is valid Roll No
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isRollNo(String roll) { // my method created
		// System.out.println("rollno");
		// String rollreg =
		// "((?=.*\\d).{1,4}(?=.*[A-Z]).{1,2}(?=.*\\d).{1,6})$";
		// String rollreg="^[a-zA-z\\s]+$";
		String rollreg = "^[0-9]{2}[A-Za-z]{2}[0-9]{2,6}$";
		// String sroll = roll.trim();
		if (DataValidator.isNotNull(roll)) {

			boolean check = roll.matches(rollreg);
			System.out.println(check);
			return check;
		}

		else {

			return false;
		}
	}

	/**
	 * Checks if value is valid Mobile No
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isMobileNo(String mobile) {

		String mobilereg = "^[6-9][0-9]{9}$";

		if (isNotNull(mobile) && mobile.matches(mobilereg)) {

			return true;
		} else {
			return false;
		}

	}

	/**
	 * Checks if value is valid PhoneNo
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isPhoneNo(String phone) {

		String mobilereg = "^[0-9][0-9]{10}$";

		if (isNotNull(phone) && phone.matches(mobilereg)) {

			return true;
		} else {
			return false;
		}

	}

	/**
	 * Checks if value is valid Age
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isValidAge(String val) {

		Date today = new Date();

		System.out.println(today);

		Date enterDate = DataUtility.getDate(val);

		System.out.println(enterDate);

		int age = today.getYear() - enterDate.getYear();
		System.out.println("age=" + age);

		if (age > 18 && isNotNull(val)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Test above methods
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DataValidator d = new DataValidator();
		// System.out.println(d.isNull(" ds "));
		// System.out.println(d.isNotNull(" "));
		// System.out.println(d.isInteger(" as d"));
		// System.out.println(d.isEmail("_ankur@gmailcom"));
		// System.out.println(d.isPassword("DIVkitwe@12"));
		// System.out.println(d.isName("Acdchj"));
		// System.out.println(d.isRollNo("01IT12"));
		//System.out.println(d.isValidAge("18-06-2000"));
		System.out.println(d.isAddress("23 c sunder Nagar"));

		// System.out.println(d.isMobileNo("98933382275"));
	}

}
