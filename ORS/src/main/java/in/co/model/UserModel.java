package in.co.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.bean.RoleBean;
import in.co.bean.UserBean;
import in.co.exception.ApplicationException;
import in.co.exception.DataBaseException;
import in.co.exception.DuplicateRecordException;
import in.co.exception.RecordNotFoundException;
import in.co.util.EmailBuilder;
import in.co.util.EmailMessage;
import in.co.util.EmailUtility;
import in.co.util.JDBCDataSource;


/**
 * The Class UserModel
 */
public class UserModel {

	/** The log. */
	private static Logger log = Logger.getLogger(UserModel.class);

	/**
	 * Find next PK of user.
	 *
	 */
	private long rollId;

	/**
	 * Gets the role id.
	 *
	 * @return the roleId
	 */
	
	  public long getRollId() { return rollId; }
	  
	 /**
		 * Sets the role id.
		 *
		 * @param roleId the roleId to set
		 */

	
	  public void setRollId(long rollId) { this.rollId = rollId; }
	  
	  /**
		 * Next PK.
		 *
		 * @return the int
		 * @throws DataBaseException the data base exception
		 */
	public int nextPK() throws DataBaseException {
		log.debug("Model nextpk started");
		int pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(ID) from ST_USER");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);

			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk = pk + 1;
	}

	/**
	 * Add a User.
	 *
	 * @param bean the bean
	 * @return the long
	 * @throws DuplicateRecordException the duplicate record exception
	 * @throws ApplicationException     the application exception @ return :long
	 */
	public long add(UserBean bean) throws DuplicateRecordException, ApplicationException {
		log.debug("Model add started");
		int pk = 0;
		Connection conn = null;

		RoleModel rmodel = new RoleModel();
		RoleBean rolebean = rmodel.findByPk(bean.getRollId());
		bean.setRollName(rolebean.getName());

		UserBean beanexist = findByLogin(bean.getLogin());

		if (beanexist != null) {
			throw new DuplicateRecordException("already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn
					.prepareStatement("insert into ST_USER values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, nextPK());
			pstmt.setString(2, bean.getFirstName());
			pstmt.setString(3, bean.getLastName());
			pstmt.setString(4, bean.getLogin());
			pstmt.setString(5, bean.getPassword());
			pstmt.setString(6, bean.getConfirmPassword());
			pstmt.setString(7, bean.getAddress());
			pstmt.setDate(8, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(9, bean.getMobileNo());
			System.out.println("in model roll" + bean.getRollId());
			pstmt.setLong(10, bean.getRollId());
			System.out.println("in model roll" + bean.getRollName());
			pstmt.setString(11, bean.getRollName());
			pstmt.setString(12, bean.getGender());
			pstmt.setString(13, bean.getCreatedBy());
			pstmt.setString(14, bean.getModifiedBy());
			pstmt.setTimestamp(15, bean.getCreateDateTime());
			pstmt.setTimestamp(16, bean.getModifiedDateTime());
			pstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);

			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new ApplicationException("Exception : Exception in add User" + e.getMessage());
			}
			// e.printStackTrace();
			throw new ApplicationException("Exception : Exception in add User" + e.getMessage());

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model end add");
		return pk;
	}

	/**
	 * Delete a User.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 */
	public void delete(UserBean bean) throws ApplicationException {
		log.debug("Model delete started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from ST_USER where ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			System.out.println("record deleted");
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete end");
	}

	/**
	 * Search User.
	 *
	 * @param bean : Search Parameters
	 * @return the list
	 * @throws ApplicationException the application exception
	 */
	public List search(UserBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Search User with pagination.
	 *
	 * @param bean     : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @return list : List of Users
	 * @throws ApplicationException the application exception
	 */

	public List search(UserBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" AND FIRSTNAME like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" AND LASTNAME like '" + bean.getLastName() + "%'");
			}
			if (bean.getLogin() != null && bean.getLogin().length() > 0) {
				sql.append(" AND LOGIN like '" + bean.getLogin() + "%'");
			}
			/*
			 * if (bean.getPassword() != null && bean.getPassword().length() > 0) {
			 * sql.append(" AND PASSWORD like '" + bean.getPassword() + "%'"); } if
			 * (bean.getDob() != null && bean.getDob().getDate() > 0) {
			 * sql.append(" AND DOB = '" + bean.getDob() + "'"); } if (bean.getMobileNo() !=
			 * null && bean.getMobileNo().length() > 0) { sql.append(" AND MOBILENO = " +
			 * bean.getMobileNo()); } if (bean.getRollId() > 0) {
			 * sql.append(" AND ROLLID = " + bean.getRollId()); } if (bean.getRollName() !=
			 * null && bean.getRollName().length() > 0) { sql.append(" AND ROLLNAME like '"
			 * + bean.getRollName() + "%'"); } if (bean.getGender() != null &&
			 * bean.getGender().length() > 0) { sql.append(" AND GENDER like '" +
			 * bean.getGender() + "%'"); }
			 */

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println(sql);

		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setConfirmPassword(rs.getString(6));
				bean.setAddress(rs.getString(7));
				bean.setDob(rs.getDate(8));
				bean.setMobileNo(rs.getString(9));
				bean.setRollId(rs.getLong(10));
				bean.setRollName(rs.getString(11));
				bean.setGender(rs.getString(12));
				bean.setCreatedBy(rs.getString(13));
				bean.setModifiedBy(rs.getString(14));
				bean.setCreateDateTime(rs.getTimestamp(15));
				bean.setModifiedDateTime(rs.getTimestamp(16));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search user");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;
	}

	/**
	 * Find User by Login.
	 *
	 * @param login : get parameter
	 * @return bean
	 * @throws ApplicationException the application exception
	 */
	public UserBean findByLogin(String login) throws ApplicationException {
		log.debug("Model findByLogin Begin");
		Connection conn = null;
		UserBean bean = null;

		StringBuffer sql = new StringBuffer("select * from ST_USER where LOGIN=?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, login);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("rs" + rs);
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setConfirmPassword(rs.getString(6));
				bean.setAddress(rs.getString(7));
				bean.setDob(rs.getDate(8));
				bean.setMobileNo(rs.getString(9));
				bean.setRollId(rs.getLong(10));
				bean.setRollName(rs.getString(11));
				bean.setGender(rs.getString(12));
				bean.setCreatedBy(rs.getString(13));
				bean.setModifiedBy(rs.getString(14));
				bean.setCreateDateTime(rs.getTimestamp(15));
				bean.setModifiedDateTime(rs.getTimestamp(16));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by login" + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByLogin End");
		return bean;
	}

	/**
	 * Find User by PK.
	 *
	 * @param pk : get parameter
	 * @return bean
	 * @throws ApplicationException the application exception
	 */
	public UserBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE ID=?");
		UserBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setConfirmPassword(rs.getString(6));
				bean.setAddress(rs.getString(7));
				bean.setDob(rs.getDate(8));
				bean.setMobileNo(rs.getString(9));
				bean.setRollId(rs.getLong(10));
				bean.setRollName(rs.getString(11));
				bean.setGender(rs.getString(12));
				bean.setCreatedBy(rs.getString(13));
				bean.setModifiedBy(rs.getString(14));
				bean.setCreateDateTime(rs.getTimestamp(15));
				bean.setModifiedDateTime(rs.getTimestamp(16));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return bean;
	}

	/**
	 * Update a user.
	 *
	 * @param bean the bean
	 * @throws ApplicationException     the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 */
	public void update(UserBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("model update started");
		Connection conn = null;

		UserBean beanexist = findByPK(bean.getId());
		System.out.println("bean exist" + beanexist);

		RoleModel rmodel = new RoleModel();
		RoleBean rolebean = rmodel.findByPk(bean.getRollId());
		bean.setRollName(rolebean.getName());

		// && !(beanexist.getId() == bean.getId())
		/*
		 * if (beanexist != null ) { throw new
		 * DuplicateRecordException("Loginid already exist"); }
		 */
		System.out.println(bean.getLogin());
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_USER SET FIRSTNAME=?,LASTNAME=?,LOGIN=?,PASSWORD=?,CONFIRMPASSWORD=?,ADDRESS=?,DOB=?,MOBILENO=?,ROLLID=?,ROLLNAME=?,GENDER=?,CREATEDBY=?,MODIFIEDBY=?,CREATEDATETIME=?,MODIFIEDDATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getFirstName());
			System.out.println("first name is" + bean.getFirstName());
			pstmt.setString(2, bean.getLastName());
			pstmt.setString(3, bean.getLogin());
			pstmt.setString(4, bean.getPassword());
			pstmt.setString(5, bean.getConfirmPassword());
			pstmt.setString(6, bean.getAddress());
			pstmt.setDate(7, new Date(bean.getDob().getTime()));
			pstmt.setString(8, bean.getMobileNo());
			pstmt.setLong(9, bean.getRollId());
			pstmt.setString(10, bean.getRollName());
			pstmt.setString(11, bean.getGender());
			pstmt.setString(12, bean.getCreatedBy());
			pstmt.setString(13, bean.getModifiedBy());
			pstmt.setTimestamp(14, bean.getCreateDateTime());
			pstmt.setTimestamp(15, bean.getModifiedDateTime());
			pstmt.setLong(16, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e.printStackTrace());
			// conn.rollback();

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update end");
	}

	/**
	 * Get List of User.
	 *
	 * @return list : List of User
	 * @throws ApplicationException the application exception
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List of User with pagination.
	 *
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @return list : List of users
	 * @throws ApplicationException the application exception
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("model list started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_USER");
		// if page size is greater than zero then apply pagination

		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				UserBean bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setConfirmPassword(rs.getString(6));
				bean.setAddress(rs.getString(7));
				bean.setDob(rs.getDate(8));
				bean.setMobileNo(rs.getString(9));
				bean.setRollId(rs.getLong(10));
				bean.setRollName(rs.getString(11));
				bean.setGender(rs.getString(12));
				bean.setCreatedBy(rs.getString(13));
				bean.setModifiedBy(rs.getString(14));
				bean.setCreateDateTime(rs.getTimestamp(15));
				bean.setModifiedDateTime(rs.getTimestamp(16));
				list.add(bean);
				System.out.println("outside rs");
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("" + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model list end");
		return list;
	}

	/**
	 * Authenticate.
	 *
	 * @param login    the login
	 * @param password the password
	 * @return the user bean
	 * @throws ApplicationException the application exception
	 */
	public UserBean authenticate(String login, String password) throws ApplicationException {
		System.out.println("authenticate DONE");
		log.debug("Model authenticate begin");
		UserBean bean = null;
		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from ST_USER where LOGIN=? AND PASSWORD=?");

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			conn.setAutoCommit(false);
			pstmt.setString(1, login);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("dATA IS AVAILABLE");
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setConfirmPassword(rs.getString(6));
				bean.setAddress(rs.getString(7));
				bean.setDob(rs.getDate(8));
				bean.setMobileNo(rs.getString(9));
				bean.setRollId(rs.getLong(10));
				bean.setRollName(rs.getString(11));
				bean.setGender(rs.getString(12));
				bean.setCreatedBy(rs.getString(13));
				bean.setModifiedBy(rs.getString(14));
				bean.setCreateDateTime(rs.getTimestamp(15));
				bean.setModifiedDateTime(rs.getTimestamp(16));
			}
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception.." + e.getMessage());
			// throw new ApplicationException("Exception : Exception in get roles");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model authenticate end");
		return bean;

	}

	/**
	 * Lock User for certain time duration.
	 *
	 * @param login : User Login
	 * @return boolean : true if success otherwise false
	 * @throws ApplicationException     the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 * @throws RecordNotFoundException  : if user not found
	 */
	/*
	 * public boolean lock(String login) throws ApplicationException,
	 * DuplicateRecordException, RecordNotFoundException {
	 * log.debug("service lock  begin"); boolean flag = false; UserBean beanexist =
	 * null; try { beanexist = findByLogin(login);
	 * 
	 * if (beanexist != null) { //beanexist.setLock(UserBean.ACTIVE);
	 * update(beanexist); flag = true; } else { throw new
	 * RecordNotFoundException("LoginId not exist"); } } catch
	 * (DuplicateRecordException e) { log.error("Application Exception..", e); throw
	 * new ApplicationException("Database Exception"); }
	 * log.debug("service lock end"); return flag; }
	 */

	/**
	 * Get User Roles.
	 *
	 * @param bean the bean
	 * @return List : User Role List
	 * @throws ApplicationException the application exception
	 */
	public List getRoles(UserBean bean) throws ApplicationException {
		log.debug("Model roles started");
		ArrayList<UserBean> list = new ArrayList();
		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from ST_USER where ROLLID=?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, bean.getRollId());

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();

				bean.setId(rs.getInt(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setConfirmPassword(rs.getString(6));
				bean.setAddress(rs.getString(7));
				bean.setDob(rs.getDate(8));
				bean.setMobileNo(rs.getString(9));
				bean.setRollId(rs.getLong(10));
				bean.setRollName(rs.getString(11));
				bean.setGender(rs.getString(12));
				bean.setCreatedBy(rs.getString(13));
				bean.setModifiedBy(rs.getString(14));
				bean.setCreateDateTime(rs.getTimestamp(15));
				bean.setModifiedDateTime(rs.getTimestamp(16));
				list.add(bean);
			}
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in get roles");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Role end");
		return list;
	}

	/**
	 * Change password.
	 *
	 * @param id          : long id
	 * @param oldPassword the old password
	 * @param newPassword the new password
	 * @return true, if successful
	 * @throws ApplicationException    the application exception
	 * @throws RecordNotFoundException the record not found exception
	 */
	public boolean changePassword(Long id, String oldPassword, String newPassword)
			throws ApplicationException, RecordNotFoundException {
		log.debug("model changePassword begin");

		System.out.println("cg pass");
		UserBean beanexist = new UserBean();
		boolean flag = false;

		beanexist = findByPK(id);

		System.out.println("bean id " + beanexist.id);

		if (beanexist != null && beanexist.getPassword().equals(oldPassword)) {
			beanexist.setPassword(newPassword);

			try {
				update(beanexist);
			} catch (DuplicateRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			flag = true;
		} else {
			throw new RecordNotFoundException("Login not exist");
		}

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", beanexist.getLogin());
		map.put("password", beanexist.getPassword());
		map.put("firstName", beanexist.getFirstName());
		map.put("lastName", beanexist.getLastName());

		String message = EmailBuilder.getChangePasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(beanexist.getLogin());
		msg.setSubject("DNT ORS Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		log.debug("model changePassword end");
		return flag;
	}

	/**
	 * Update access.
	 *
	 * @param bean the bean
	 * @return the user bean
	 * @throws ApplicationException the application exception
	 */
	/*
	 * public UserBean updateAccess(UserBean bean) throws ApplicationException {
	 * return null; }
	 */
	/**
	 * Register a user.
	 *
	 * @param bean the bean
	 * @return the long
	 * @throws Exception the exception
	 */
	public long registerUser(UserBean bean) throws Exception {
		log.debug("");
		long pk = add(bean);
		System.out.println("pk value" + pk); // value is 0 why?
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", bean.getLogin());
		map.put("password", bean.getPassword());

		System.out.println("login id " + bean.getLogin());
		System.out.println("pass is " + bean.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(bean.getLogin());
		msg.setSubject("Registration is successful for ORS Project SunilOS");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return pk;
	}

	/**
	 * Reset Password of User with auto generated Password.
	 *
	 * @param bean the bean
	 * @return boolean : true if success otherwise false
	 * @throws ApplicationException the application exception
	 */
	/*
	 * public boolean resetPassword(UserBean bean) throws ApplicationException {
	 * log.debug(""); boolean flag = false; // changed String newPassword =
	 * String.valueOf(new java.util.Date().getTime()).substring(9, 13); // changed
	 * UserBean userData = findByPK(bean.getId());
	 * 
	 * userData.setPassword(newPassword); System.out.println("new pass is" +
	 * userData); try { update(userData);
	 * 
	 * } catch (DuplicateRecordException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * flag = true; // If I use userData then what?
	 * 
	 * HashMap<String, String> map = new HashMap<String, String>(); map.put("login",
	 * bean.getLogin()); map.put("password", bean.getPassword());
	 * map.put("firstName", bean.getFirstName()); map.put("lastName",
	 * bean.getLastName());
	 * 
	 * String message = EmailBuilder.getForgetPasswordMessage(map);
	 * 
	 * EmailMessage msg = new EmailMessage();
	 * 
	 * msg.setTo(bean.getLogin()); msg.setSubject("Password has been reset");
	 * msg.setMessage(message); msg.setMessageType(EmailMessage.HTML_MSG);
	 * 
	 * EmailUtility.sendMail(msg);
	 * 
	 * return flag; }
	 */

	/**
	 * Send the password of User to his Email.
	 *
	 * @param login : User Login
	 * @return boolean : true if success otherwise false
	 * @throws RecordNotFoundException : if user not found
	 * @throws ApplicationException    the application exception
	 */
	public boolean forgetPassword(String login) throws RecordNotFoundException, ApplicationException {

		System.out.println("inside forget password model");

		log.debug("");
		boolean flag = false;
		UserBean userData = findByLogin(login);

		if (userData == null) {
			throw new RecordNotFoundException("Emailid does not exist");
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", userData.getLogin());
		map.put("password", userData.getPassword());
		map.put("firstName", userData.getFirstName());
		map.put("lastName", userData.getLastName());

		String message = EmailBuilder.getForgetPasswordMessage(map);
		EmailMessage msg = new EmailMessage();
		msg.setTo(login);
		msg.setSubject("DNT ORS Password reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		EmailUtility.sendMail(msg);
		flag = true;

		return flag;

	}

}
