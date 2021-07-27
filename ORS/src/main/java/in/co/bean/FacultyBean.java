package in.co.bean;

import java.util.Date;

public class FacultyBean extends BaseBean {
	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;
	
	/** The gender. */
	private String gender;
	
	/** The login id. */
	private String loginId;
	
	/** The Address. */
	private String address;

	/** The date of joining. */
	private Date dateOfJoining;
	
	/** The qualification. */
	private String qualification;
	
	/** The mobile no. */
	private String mobileNo;
	
	/** The college id. */
	private int collegeId;
	
	/** The college name. */
	private String collegeName;
	
	/** The course id. */
	private int courseId;
	
	/** The course name. */
	private String courseName;
	
	/** The subject id. */
	private int subjectId;
	
	/** The subject name. */
	private String subjectName;

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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public int getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(int collegeId) {
		this.collegeId = collegeId;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getKey() {
		// TODO Auto-generated method stub
		return id +"";
	}

	/**
	 * Gets the value. 
	 */
	public String getValue() {
		// TODO Auto-generated method stub
		return firstName+" "+lastName;
	}

}
