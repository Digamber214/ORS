package in.co.bean;

public class CourseBean extends BaseBean{
	/** The cname. */
	private String cName;

	
	/** The duration. */
	private String duration;

	/** The description. */
	private String description;
	

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	/**
	 * Gets the value.
	 *
	 * 
	 */
	public String getValue() {
		// TODO Auto-generated method stub
		return cName;
	}

}
