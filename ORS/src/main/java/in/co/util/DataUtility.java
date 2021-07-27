package in.co.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class DataUtility.
 */
public class DataUtility {
	
	/** Application Date Format. */

	public static final String APP_DATE_FORMAT = "dd-MM-yyyy";
	
	/** The Constant APP_TIME_FORMAT. */
	public static final String APP_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";
	
	/** Date formatter. */
	public static final SimpleDateFormat formatter = new SimpleDateFormat(APP_DATE_FORMAT );
	
	/** The Constant timeFormatter. */
	public static final SimpleDateFormat timeFormatter = new SimpleDateFormat(APP_TIME_FORMAT);

	/**
	 * Trims and trailing and leading spaces of a String.
	 *
	 * @param val the val
	 * @return the string
	 */
	public static String getString(String val) {
		if (DataValidator.isNotNull(val)) {
			return val.trim();
		} else {
			return val;
		}

	}

	/**
	 * Converts and Object to String.
	 *
	 * @param val the val
	 * @return the string data
	 */
	 /**
     * Converts and Object to String
     * 
     * @param val
     * @return
     */
    public static String getStringData(Object val) {
        if (val != null) {
            return val.toString();
        } else {
            return "";
        }
    }

	/**
	 * Converts String into Integer.
	 *
	 * @param val the val
	 * @return the int
	 */
	public static int getInt(String val) {

		//String trimNum = val.trim(); // Modified

		if (DataValidator.isInteger(val)) {

			int i = Integer.parseInt(val);
			return i;
		}

		else {
			return 0;
		}
	}


    /*public static int getint(String val) {
    	if(DataValidator.isInteger(val)) {
    		return Integer.parseInt(val);
    	}else {
    		return 0;
    	}
    }
	*/
	/**
     * Converts String into Long.
     *
     * @param val the val
     * @return the long
     */
	public static long getLong(String val) {
	
		if (DataValidator.isLong(val)) {
			System.out.println("value in data utitlity getlong"+val);
			return Long.parseLong(val);
		} 
		
		
		else {
			return 0;
		}
	}

	/**
	 * Converts String into Date.
	 *
	 * @param val the val
	 * @return the date
	 */
	public static Date getDate(String val) {
		Date d = null;
		try {
			d = formatter.parse(val);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * Converts Date into String.
	 *
	 * @param d the d
	 * @return the date string
	 */
	public static String getDateString(Date d) {
		try {
			return formatter.format(d);
		} catch (Exception e) {
          return "";
		}
	
	}

	/**
	 * Gets date after n number of days.
	 *
	 * @param date the date
	 * @param day the day
	 * @return the date
	 */
	public static Date getDate(Date date, int day) { // Not Implemented & used

		return null;
	}

	/**
	 * Converts String into Time.
	 *
	 * @param val the val
	 * @return the time stamp
	 */
	public static Timestamp getTimeStamp(String val) {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(timeFormatter.parse(val).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return timeStamp;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @param l the l
	 * @return the timestamp
	 */
	public static Timestamp getTimestamp(long l) {

		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(l);
		} catch (Exception e) {
			return null;
		}
		return timeStamp;
	}

	/**
	 * Gets the current timestamp.
	 *
	 * @return the current timestamp
	 */
	public static Timestamp getCurrentTimestamp() {
		Timestamp timeStamp = null;
		try {

			timeStamp = new Timestamp(new Date().getTime());

		} catch (Exception e) {
		}

		return timeStamp;

	}

	/**
	 * Gets the timestamp.
	 *
	 * @param tm the tm
	 * @return the timestamp
	 */
	public static long getTimestamp(Timestamp tm) {
		try {
			return tm.getTime();
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		// System.out.println(getInt(" 1@sd23 "));
		// System.out.println("This is GetTimeStamp Current =
		// "+getCurrentTimestamp());
		// String s = "Hewl";
		// Date d = new Date();
		// System.out.println(new Timestamp(new Date().getTime()));

		// System.out.println("Today's date "+getDateString(new Date()));
		// getCurrentTimestamp();
		// System.out.println( getCurrentTimestamp());
		// Timestamp st = new Timestamp(1552150570128L);
		// System.out.println(getTimestamp(new Date().getTime()));
		// Date date = new Date();
		// System.out.println(date.getTime());

		// Calendar cal = Calendar.getInstance();
		// System.out.println(getDateString(d));

		// String s = " 1234 ";
		// String tr = s.trim();
		// int rs = tr.indexOf(' ');
		// System.out.println(rs);
		// if(tr !=null && rs == -1){
		// System.out.println(tr);
		// }else {
		// System.out.println("00");
		// }
		//
		// }

	}

}
