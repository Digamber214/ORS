package in.co.bean;

import java.util.Date;

public class TimetableBean extends BaseBean{
	/** The course id. */
	private int courseId;
	
	/** The course name. */
	private String courseName;
	
	/** The subject id. */
	private int subjectId;
	
	/** The subject name. */
	private String subjectName;
	
	/** The semester. */
	private String semester;
	
	/** The exam time. */
	private String examTime;
	
	/** The exam date. */
	private Date examDate;

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

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getExamTime() {
		return examTime;
	}

	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
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
		return courseName;
	}

}
