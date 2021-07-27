package in.co.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.bean.CourseBean;
import in.co.exception.ApplicationException;
import in.co.exception.DuplicateRecordException;
import in.co.util.JDBCDataSource;

/**
 * The Class CourseModel.
 *
 */
public class CourseModel {
	/** The log. */
	private static Logger log = Logger.getLogger(CourseModel.class);
	
	/**
	 * Next pk.
	 *
	 * @return the integer
	 * @throws SQLException the SQL exception
	 */
	public Integer nextPK() throws Exception {
		log.debug("Next PK has started ");
		int pk = 0;
		Connection conn = null;
		try {
			conn= JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_COURSE");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return pk = pk + 1 ;
		
	}
	/**
	 * Adds the.
	 *
	 * @param bean the bean
	 * @return the long
	 * @throws Exception the exception
	 */
	public long add(CourseBean bean) throws DuplicateRecordException , ApplicationException {
		log.debug(" Course add has started");
		int pk = 0;
		
		CourseBean beanExist = null;
		try {
			beanExist = findByName(bean.getcName());
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		if(beanExist!= null) {
			throw new DuplicateRecordException("course name already exist!!! enter new course");
		}
		Connection conn = null;
		try {
			pk = nextPK();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_course values(?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getcName());
			pstmt.setString(3, bean.getDuration());
			pstmt.setString(4, bean.getDescription());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreateDateTime());
			pstmt.setTimestamp(8, bean.getModifiedDateTime());
			pstmt.executeUpdate();
			System.out.println("record inserted");
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new ApplicationException("Exception in roll back"+e1.getMessage());
			}
			throw new ApplicationException("Exception in add course");
		} finally {
			JDBCDataSource.closeConnection(conn);	
		}
		log.debug("");
		return pk;
	}
	/**
	 * Delete.
	 *
	 * @param bean the bean
	 * @throws Exception the exception
	 */
	
	public void delete(CourseBean bean) throws Exception {

		log.debug("Course delete method has started");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_course where id=?");
			pstmt.setLong(1, bean.getId());

			int i = pstmt.executeUpdate();
			System.out.println("record deleted");
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		log.debug("");
	}
	/**
	 * Update.
	 *
	 * @param bean the bean
	 * @throws Exception the exception
	 */
	public void update(CourseBean bean) throws Exception {

		log.debug("Course Update has started");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_course set cName=?,duration=?,description=?,createdby=?,modifiedby=?,createdatetime=?,modifieddatetime=? where id=?");

			pstmt.setString(1, bean.getcName());
			pstmt.setString(2, bean.getDuration());
			pstmt.setString(3, bean.getDescription());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreateDateTime());
			pstmt.setTimestamp(7, bean.getModifiedDateTime());
			pstmt.setLong(8, bean.getId());

			int i = pstmt.executeUpdate();
			System.out.println("record updated"+i);
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		log.debug("");
	}
	/**
	 * Find by pk.
	 *
	 * @param pk the pk
	 * @return the CourseBean
	 * @throws Exception the exception
	 */
	public CourseBean findByPk(long pk) throws Exception {

		log.debug("Course findByPk has started");
        System.out.println("inside pk");
		CourseBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_course where id=?");
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();
			conn.commit();
			while (rs.next()) {
				bean = new CourseBean();

				bean.setId(rs.getLong(1));
				bean.setcName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreateDateTime(rs.getTimestamp(7));
				bean.setModifiedDateTime(rs.getTimestamp(8));
			}

			//System.out.println("course name is"+bean.getcName());
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return bean;
	}
	/**
	 * Find by Name.
	 *
	 * @param cName the cName
	 * @return the CourseBean
	 * @throws Exception the exception
	 */
	public CourseBean findByName(String cName) throws Exception {
		log.debug("CourseModel findByName has started");

		CourseBean bean = null;
		Connection conn = null;
       
		StringBuffer sql = new StringBuffer("select * from st_course where cName=?");
       
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1,cName);

			ResultSet rs = pstmt.executeQuery();
			conn.commit();
			while (rs.next()) {
				bean = new CourseBean();

				bean.setId(rs.getLong(1));
				bean.setcName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreateDateTime(rs.getTimestamp(7));
				bean.setModifiedDateTime(rs.getTimestamp(8));
			}

			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}

		return bean;
	}
	public List search(CourseBean bean) throws Exception{
		return search(bean,0,0);
	}
	/**
	 * Search.
	 *
	 * @param bean the bean
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the list
	 * @throws Exception the exception
	 */
	
public List search(CourseBean bean,int pageNo,int pageSize) throws Exception{
		
		log.debug("CourseModel search has started");
		System.out.println("inside search");
		
		Connection conn= null;
		StringBuffer sql = new StringBuffer
				("select * from st_course where 1=1");
		
		if(bean!=null){
			
			if(bean.getId()>0){
				sql.append(" And id "+bean.getId());
			}
			
			if(bean.getcName()!=null && bean.getcName().length()>0){
				sql.append(" AND cNAME like '" + bean.getcName()+ "%'");
			}
		
			if(bean.getDuration()!=null && bean.getDuration().length()>0){
				sql.append(" AND DURATION like '" + bean.getDuration()+ "%'");
			}
			
			if(bean.getDescription()!=null && bean.getDescription().length()>0){
				sql.append(" AND DESCRIPTION like '" + bean.getDescription()+ "%'");
			}
			
		}
		
		if(pageSize>0){
			pageNo= (pageNo-1)*pageSize;
			sql.append(" LIMIT "+pageNo+ " ," +pageSize);
		}
		
		List<CourseBean> list = new ArrayList<CourseBean>();
		
		try{
			
			conn= JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			CourseBean bean1;
			while(rs.next()){
				bean1= new CourseBean();
				
				bean1.setId(rs.getLong(1));
				bean1.setcName(rs.getString(2));
				bean1.setDuration(rs.getString(3));
				bean1.setDescription(rs.getString(4));
				bean1.setCreatedBy(rs.getString(5));
				bean1.setModifiedBy(rs.getString(6));
				bean1.setCreateDateTime(rs.getTimestamp(7));
				bean1.setModifiedDateTime(rs.getTimestamp(8));
				
				list.add(bean1);
			}
			rs.close();
			pstmt.close();
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
		finally{
			conn.close();
		}
		return list;
	}
/**
 * List.
 *
 * @param pageNo the page no
 * @param pageSize the page size
 * @return the list
 * @throws Exception the exception
 */	
public List list(int pageNo,int pageSize) throws Exception{
	
	log.debug("");
	
	List<CourseBean> list = new ArrayList<CourseBean>();
	
	StringBuffer sql = new StringBuffer("select * from st_course");
	
	if(pageSize>0){
		pageNo= (pageNo-1)*pageSize;
		sql.append(" limit "+pageNo+" ,"+pageSize);
	}
	
	Connection conn=null;
	
	try{
		conn= JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		
		ResultSet rs = pstmt.executeQuery();
		CourseBean bean1;
		while(rs.next()){
			bean1 = new CourseBean();
			
			bean1.setId(rs.getLong(1));
			bean1.setcName(rs.getString(2));
			bean1.setDuration(rs.getString(3));
			bean1.setDescription(rs.getString(4));
			bean1.setCreatedBy(rs.getString(5));
			bean1.setModifiedBy(rs.getString(6));
			bean1.setCreateDateTime(rs.getTimestamp(7));
			bean1.setModifiedDateTime(rs.getTimestamp(8));
			
			list.add(bean1);
		}
		rs.close();
		pstmt.close();
	}
	catch(Exception e){
		e.printStackTrace();
	}
	finally{
		conn.close();
	}
	return list;
}
/**
 * List.
 *
 * @return the list
 * @throws Exception the exception
 */
public List list() throws Exception{
	return list(0,0);
}

}
