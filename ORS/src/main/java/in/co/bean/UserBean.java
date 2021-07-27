package in.co.bean;

import java.sql.Timestamp;
import java.util.Date;

public class UserBean extends BaseBean {
	
	/** First Name of User. */
	private String firstName;

	/** Last Name of User. */
	private String lastName;

	/** Login of User. */
	private String login;

	/** password of User. */
	private String password;

	/** Confirm password of User. */

	private String confirmPassword;

	/** DOB of User. */

	private String address;

	/** address of User. */

	private Date dob;

	/** Mobile No of User. */

	private String mobileNo;

	/** roll of User. */

	private long rollId;

	/** Number of unsuccessful login attempt. */
	private String rollName;

	/** gender of User. */

	private String gender;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public long getRollId() {
		return rollId;
	}

	public void setRollId(long rollId) {
		this.rollId = rollId;
	}

	public String getRollName() {
		return rollName;
	}

	public void setRollName(String rollName) {
		this.rollName = rollName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Gets the key.
	 *
	 * 
	 */
	public String getKey() {
		// TODO Auto-generated method stub
		return id + "";
	}

	/**
	 * Gets the Value.
	 *
	 * 
	 */
	public String getValue() {
		// TODO Auto-generated method stub
		return firstName + "" + lastName;
	}

}
