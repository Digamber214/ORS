package in.co.bean;

public class RoleBean extends BaseBean {
	public static final int ADMIN = 1;
	public static final int FACULTY = 2;
	public static final int STUDENT = 3;
	public static final int KIOSK = 4;
	public static final int COLLEGE = 5;
	
	/**
	 * Role Name
	 */
	private String name;
	private String description;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Role Description
	 */
	
	
	public String getKey() {
		// TODO Auto-generated method stub
		return id+" ";
	}
	public String getValue() {
		// TODO Auto-generated method stub
		return name;
	}

}
