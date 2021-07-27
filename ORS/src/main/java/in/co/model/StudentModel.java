package in.co.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.bean.CollegeBean;
import in.co.bean.StudentBean;
import in.co.exception.ApplicationException;
import in.co.exception.DuplicateRecordException;
import in.co.util.JDBCDataSource;

/**
 * The Class StudentModel.
 */
public class StudentModel {

	/** The log. */
	Logger log = Logger.getLogger(StudentModel.class);

	/**
	 * Next pk.
	 *
	 * @return the integer
	 * @throws ApplicationException the application exception
	 */
	public Integer nextPk() throws ApplicationException {
		log.debug("Model nextPK Started");
		int pk = 0;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_student");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApplicationException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk = pk + 1;
	}

	/**
	 * Find by email.
	 *
	 * @param email the email
	 * @return the student bean
	 * @throws ApplicationException the application exception
	 */
	public StudentBean findByEmail(String email) throws ApplicationException {

		log.debug("Model findBy Email Begin");
		StudentBean bean = new StudentBean();

		StringBuffer sql = new StringBuffer("select * from st_student where email=?");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setAddress(rs.getString(8));
				bean.setEmail(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreateDateTime(rs.getTimestamp(12));
				bean.setModifiedDateTime(rs.getTimestamp(13));
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting User by Email");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBy Email End");
		return bean;
	}

	/**
	 * Adds the.
	 *
	 * @param bean the bean
	 * @return the long
	 * @throws ApplicationException the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 */
	public long add(StudentBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");
		Connection conn = null;

		CollegeModel cmodel = new CollegeModel();
		CollegeBean cbean = cmodel.findByPK(bean.getCollegeId());
		System.out.println("college bean "+cbean);
		bean.setCollegeName(cbean.getName());

		StudentBean duplicateName = findByEmail(bean.getEmail());
		System.out.println("duplicate name is"+duplicateName);
		
        if(duplicateName!=null && duplicateName.getEmail()!=null){
        	throw new DuplicateRecordException("record already exist");
        }
		int pk = 0;
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			pk = nextPk();
			System.out.println("next pk called" + pk);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_STUDENT VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setLong(2, bean.getCollegeId());
			pstmt.setString(3, bean.getCollegeName());
			pstmt.setString(4, bean.getFirstName());
			pstmt.setString(5, bean.getLastName());
			pstmt.setDate(6, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(7, bean.getMobileNo());
			pstmt.setString(8,bean.getAddress());
			pstmt.setString(9, bean.getEmail());
			pstmt.setString(10, bean.getCreatedBy());
			pstmt.setString(11, bean.getModifiedBy());
			pstmt.setTimestamp(12, bean.getCreateDateTime());
			pstmt.setTimestamp(13, bean.getModifiedDateTime());

			pstmt.executeUpdate();
			System.out.println("record inserted");
			conn.commit();
			pstmt.close();
			System.out.println("record inserted");
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in add Student");
		} 
		
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add end");
		return pk;
	}

	/**
	 * Delete.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 */
	public void delete(StudentBean bean) throws ApplicationException {
		log.debug("model delete begin");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_student where id=?");
			pstmt.setLong(1, bean.getId());
			int i=pstmt.executeUpdate();
			System.out.println("i="+i);
			conn.commit();
			pstmt.close();
			

		} catch (Exception e) {
			System.out.println("record exce deleted");
			log.error("asdfghj");
			e.printStackTrace();
			throw new ApplicationException("Exception while deleting" + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("model delete end");
	}

	/**
	 * Find by pk.
	 *
	 * @param pk the pk
	 * @return the student bean
	 * @throws ApplicationException the application exception
	 */
	public StudentBean findByPk(long pk) throws ApplicationException {
		
	System.out.println("inside find by pk");	
	log.debug("Model findByPK Started");
    StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT WHERE ID=?");
    StudentBean bean = null;
    Connection conn = null;
    try {
        conn = JDBCDataSource.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        pstmt.setLong(1, pk);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            bean = new StudentBean();
            bean.setId(rs.getLong(1));
            bean.setCollegeId(rs.getLong(2));
            bean.setCollegeName(rs.getString(3));
            bean.setFirstName(rs.getString(4));
            bean.setLastName(rs.getString(5));
            bean.setDob(rs.getDate(6));
            bean.setMobileNo(rs.getString(7));
            bean.setAddress(rs.getString(8));
            bean.setEmail(rs.getString(9));
            bean.setCreatedBy(rs.getString(10));
            bean.setModifiedBy(rs.getString(11));
            bean.setCreateDateTime(rs.getTimestamp(12));
            bean.setModifiedDateTime(rs.getTimestamp(13));
        }
        rs.close();
    } catch (Exception e) {
    	e.printStackTrace();
        log.error("Database Exception..", e);
        throw new ApplicationException(
                "Exception : Exception in getting User by pk");
    } finally {
        JDBCDataSource.closeConnection(conn);
    }
    log.debug("Model findByPK End");
    return bean;
	}

	/**
	 * Update.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 */
	public void update(StudentBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;

		//StudentBean beanExist = findByEmail(bean.getEmail());
		// Checks if updated Roll no already exist
		/*if (beanExist != null && beanExist.getId() == bean.getId()) {
			throw new DuplicateRecordException("Emailid already exist");
		}*/

		CollegeModel cmodel = new CollegeModel();
		CollegeBean cbean = cmodel.findByPK(bean.getId());
		bean.setCollegeName(cbean.getName());

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_STUDENT SET COLLEGEID=?,COLLEGENAME=?,FIRSTNAME=?,LASTNAME=?,DOB=?,MOBILENO=?,ADDRESS=?,EMAIL=?,CREATEDBY=?,MODIFIEDBY=?,CREATEDATETIME=?,MODIFIEDDATETIME=? WHERE ID=?");

			pstmt.setLong(1, bean.getCollegeId());
			pstmt.setString(2, bean.getCollegeName());
			pstmt.setString(3, bean.getFirstName());
			pstmt.setString(4, bean.getLastName());
			pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(6, bean.getMobileNo());
			pstmt.setString(7,bean.getAddress());
			pstmt.setString(8, bean.getEmail());
			pstmt.setString(9, bean.getCreatedBy());
			pstmt.setString(10, bean.getModifiedBy());
			pstmt.setTimestamp(11, bean.getCreateDateTime());
			pstmt.setTimestamp(12, bean.getModifiedDateTime());
			pstmt.setLong(13, bean.getId());
			pstmt.executeUpdate();
			System.out.println("record updated");
			conn.commit();
			
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception" + e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new ApplicationException("exception in rollback");
			}
			throw new ApplicationException("Exception in getting update");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	/**
	 * Search.
	 *
	 * @param bean the bean
	 * @return the list
	 * @throws ApplicationException the application exception
	 */
	public List search(StudentBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Search.
	 *
	 * @param bean the bean
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the list
	 * @throws ApplicationException the application exception
	 */
	public List search(StudentBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Begin");

		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from st_student where 1=1");

		if (bean != null) {
			
		   if (bean.getId() > 0) {
			   sql.append(" And id= '" + bean.getId()+"'"); }
			 
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" AND FIRSTNAME like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" AND LASTNAME like '" + bean.getLastName() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + bean.getDob());
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND MOBILENO like '" + bean.getMobileNo() + "%'");
			}
			if (bean.getEmail() != null && bean.getEmail().length() > 0) {
				sql.append(" AND EMAIL like '" + bean.getEmail() + "%'");
			}
			if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
				sql.append(" AND COLLEGENAME = " + bean.getCollegeName());
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		System.out.println("sql query of student is"+sql);
		
		List<StudentBean> list = new ArrayList();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setAddress(rs.getString(8));
				bean.setEmail(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreateDateTime(rs.getTimestamp(12));
				bean.setModifiedDateTime(rs.getTimestamp(13));
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database ecxeption " + e);
			throw new ApplicationException("Exception : Exception in search Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;
	}

	/**
	 * List.
	 *
	 * @return the list
	 * @throws ApplicationException the application exception
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * List.
	 *
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the list
	 * @throws ApplicationException the application exception
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Begin");
		Connection conn = null;
		List<StudentBean> list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_student");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				StudentBean bean = new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setAddress(rs.getString(8));
				bean.setEmail(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreateDateTime(rs.getTimestamp(12));
				bean.setModifiedDateTime(rs.getTimestamp(13));
				list.add(bean);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			log.error("database exception" + e);
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting list of Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list end");
		return list;

	}

}
