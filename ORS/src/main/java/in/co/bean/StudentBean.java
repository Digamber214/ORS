package in.co.bean;

import java.util.Date;

public class StudentBean extends BaseBean {
	
	/**
	 * First Name of Student
	 */

	private String firstName;
	/**
	 * Last Name of Student
	 */

	private String lastName;
	/**
	 * Date of birth of Student
	 */

	private Date dob;
	/**
	 * Mobile no of Student
	 */

	private String mobileNo;
	
	/**
	 * Address of Student
	 */
	private String address;
	
	
	/**
	 * Email id of Student
	 */
	private String email;
	/**
	 * College Id of Student
	 */

	private long collegeId;
	/**
	 * College Name of Student
	 */

	private String collegeName;
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getCollegeId() {
		return collegeId;
	}
	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	/**
	 * Gets the key.
	 *
	 * 
	 */
	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	/**
	 * Gets the Value.
	 *
	 * 
	 */
	public String getValue() {
		// TODO Auto-generated method stub
		return firstName+" "+lastName;
	}

}
