package in.co.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.bean.CollegeBean;
import in.co.exception.ApplicationException;
import in.co.exception.DataBaseException;
import in.co.exception.DuplicateRecordException;
import in.co.util.JDBCDataSource;

/**
 * The Class CollegeModel
 *
 */
public class CollegeModel {
	
	/** The log. */
	private static Logger log = Logger.getLogger(CollegeModel.class);
	
	/**
	 * Next pk.
	 *
	 * @return the integer
	 * @throws SQLException the SQL exception
	 */
	public Integer nextPK() throws DataBaseException{
		log.debug("Model nextPK() started");
		 Connection conn= null;
		int pk=0;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_college");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				pk = rs.getInt(1);
				}
			rs.close();
			}
		catch (Exception e) {
			log.error("DatatrBase Exception" , e );
			e.printStackTrace();
			throw new DataBaseException("Exception : Exception in getting PK");
		} 
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk = pk + 1;
		
	}

	/**
	 * Find by Name.
	 *
	 * @param name the name
	 * @return the CollegeBean
	 * @throws Exception the exception
	 */

public CollegeBean findByName(String name) throws ApplicationException{
	log.debug("Model findByName begin");
	StringBuffer sql = new StringBuffer("select * from st_college where name=?");
	CollegeBean bean = null;
	Connection conn=null;
	try
	{
		conn=JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		pstmt.setString(1, name);
		//System.out.println("find by name========>"+sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean= new CollegeBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setAddress(rs.getString(3));
			bean.setState(rs.getString(4));
			bean.setCity(rs.getString(5));
			bean.setPhoneNo(rs.getString(6));
			bean.setCreatedBy(rs.getString(7));
			bean.setModifiedBy(rs.getString(8));
			bean.setCreateDateTime(rs.getTimestamp(9));
			bean.setModifiedDateTime(rs.getTimestamp(10));
          }
		rs.close();
	}catch (Exception e) {
		e.printStackTrace();
		log.error("DataBase Exception"+ e.getMessage());
		throw new ApplicationException("Exception: Exception in getting college by name");
	}finally {
		JDBCDataSource.closeConnection(conn);
		}
	log.debug("Model find by name end");
	return bean;
	}
/**
 * Find by pk.
 *
 * @param pk the pk
 * @return the  CollegeBean
 * @throws Exception the exception
 */
public CollegeBean findByPK(long pk) throws ApplicationException {
    log.debug("Model findByPK Started");
    System.out.println("Model findByPK Started");
    
    StringBuffer sql = new StringBuffer(
            "SELECT * FROM ST_COLLEGE WHERE ID=?");
    CollegeBean bean = null;
    Connection conn = null;
    
    System.out.println("sql value="+sql);
    try {

        conn = JDBCDataSource.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        pstmt.setLong(1, pk);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            bean = new CollegeBean();
            bean.setId(rs.getLong(1));
            bean.setName(rs.getString(2));
            bean.setAddress(rs.getString(3));
            bean.setState(rs.getString(4));
            bean.setCity(rs.getString(5));
            bean.setPhoneNo(rs.getString(6));
            bean.setCreatedBy(rs.getString(7));
            bean.setModifiedBy(rs.getString(8));
            bean.setCreateDateTime(rs.getTimestamp(9));
            bean.setModifiedDateTime(rs.getTimestamp(10));

        }
        rs.close();
    } catch (Exception e) {
    	e.printStackTrace();
        //log.error("Database Exception..", e);
        //throw new ApplicationException(
          //      "Exception : Exception in getting College by pk");
    } finally {
        JDBCDataSource.closeConnection(conn);
    }
    log.debug("Model findByPK End");
    return bean;
}

/**
 * Adds the.
 *
 * @param bean the bean
 * @return the long
 * @throws Exception the exception
 */

public long  add(CollegeBean bean) throws ApplicationException , DuplicateRecordException{
	log.debug("Model add started");
	Connection conn= null;
	int pk = 0;
	CollegeBean duplicateRecord = findByName(bean.getName());
	System.out.println("Check for duplicate Record" +duplicateRecord);
	if (duplicateRecord != null) {
		throw new DuplicateRecordException("College Name already exists");
	}
	try {
		pk = nextPK();
		// Get auto-generated next primary key

		conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false); // Begin transaction
		PreparedStatement pstmt = conn.prepareStatement("insert into st_college values(?,?,?,?,?,?,?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getName());
		pstmt.setString(3, bean.getAddress());
		pstmt.setString(4, bean.getState());
		pstmt.setString(5, bean.getCity());
		pstmt.setString(6, bean.getPhoneNo());
		pstmt.setString(7, bean.getCreatedBy());
		pstmt.setString(8, bean.getModifiedBy());
		pstmt.setTimestamp(9, bean.getCreateDateTime());
		pstmt.setTimestamp(10, bean.getModifiedDateTime());
		
		pstmt.executeUpdate();
		conn.commit();
		System.out.println("1 record inserted");
		pstmt.close();
     } catch (Exception e) {
		log.error("Database exception" + e);
		try {
			conn.rollback();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		throw new ApplicationException("Exception : add rollback exception " + e.getMessage());
	} finally {
		JDBCDataSource.closeConnection(conn);
	}
	log.debug("Model add end");
	return pk;
  }
/**
 * Delete.
 *
 * @param bean the bean
 * @throws Exception the exception
 */
public void delete(CollegeBean bean) throws ApplicationException, SQLException {
	log.debug("Model delete Started");
	Connection conn = null;

	try {
		//bean = new CollegeBean();
		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement("delete from st_college where id =?");
		pstmt.setLong(1, bean.getId());

		pstmt.executeUpdate();
		conn.commit();
		System.out.println("1 record deleted");
	} catch (Exception e) {
		e.printStackTrace();
		conn.rollback();
		log.error("Database Exception..", e);
		throw new ApplicationException("Exception : Exception in delete college");
	} finally {
		JDBCDataSource.closeConnection(conn);
	}
	log.debug("Model delete end");
}
/**
 * Update.
 *
 * @param bean the bean
 * @throws Exception the exception
 */
public void update(CollegeBean bean) throws DuplicateRecordException, ApplicationException, SQLException{
	
	log.debug("Model update Started");
	Connection conn= null;
	
	try{
		conn= JDBCDataSource.getConnection();
        conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement("update st_college set NAME=?,ADDRESS=?,STATE=?,CITY=?,PHONENO=?,CREATEDBY=?,MODIFIEDBY=?,CREATEDATETIME=?,MODIFIEDDATETIME=? where ID=?");
		
		pstmt.setString(1,bean.getName());
		 pstmt.setString(2, bean.getAddress());
            pstmt.setString(3, bean.getState());
            pstmt.setString(4, bean.getCity());
            pstmt.setString(5, bean.getPhoneNo());
            pstmt.setString(6, bean.getCreatedBy());
            pstmt.setString(7, bean.getModifiedBy());
            pstmt.setTimestamp(8, bean.getCreateDateTime());
            pstmt.setTimestamp(9, bean.getModifiedDateTime());
            pstmt.setLong(10, bean.getId());
		    
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
           
	}
	catch(Exception e){
		e.printStackTrace();
		conn.rollback();
		throw new ApplicationException("Exception in updating College ");
	}
	finally{
		JDBCDataSource.closeConnection(conn);
	}
	log.debug("Model update end");
}

public List search(CollegeBean bean) throws ApplicationException{
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
public List search(CollegeBean bean,int pageNo,int pageSize) throws ApplicationException{
	
	
	log.debug("Model search Begin");
	StringBuffer sql = new StringBuffer
			("select * from st_college where 1=1");
	
	if(bean!=null){
		if(bean.getId()>0){
		sql.append("And id "+bean.getId());
		}
		if(bean.getName()!=null && bean.getName().length()>0){
			sql.append(" AND NAME like '" + bean.getName() + "%'");
		}
		if(bean.getAddress()!=null && bean.getAddress().length()>0){
			sql.append(" And Address like '"+bean.getAddress()+"%'");
		}
		if(bean.getCity()!=null && bean.getCity().length()>0){
			sql.append(" And city like '"+bean.getCity()+"%'");
		}
		if(bean.getState()!=null && bean.getState().length()>0){
			sql.append(" And state like '"+bean.getState()+"%'");
		}
		if(bean.getPhoneNo()!=null && bean.getPhoneNo().length()>0){
			sql.append(" And phoneno like "+bean.getPhoneNo());
		}
		
	}
	
	if(pageSize>0){
		pageNo= (pageNo-1)*pageSize;
		sql.append(" Limit " + pageNo + ", " + pageSize);
	}
	ArrayList<CollegeBean> list = new ArrayList<CollegeBean>();
	Connection conn= null;
	try{
		
		conn= JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	    ResultSet rs=pstmt.executeQuery();
	    CollegeBean bean1;
	    while(rs.next()){
	    	bean1= new CollegeBean();
	    	bean1.setId(rs.getLong(1));
	    	bean1.setName(rs.getString(2));
	    	bean1.setAddress(rs.getString(3));
	    	bean1.setCity(rs.getString(4));
	    	bean1.setState(rs.getString(5));
	    	bean1.setPhoneNo(rs.getString(6));
	    	bean1.setCreatedBy(rs.getString(7));
	    	bean1.setModifiedBy(rs.getString(8));
	    	bean1.setCreateDateTime(rs.getTimestamp(9));
	    	bean1.setModifiedDateTime(rs.getTimestamp(10));
	    	list.add(bean1);
	    }
	    pstmt.close();
	}
	catch(Exception e){
		e.printStackTrace();
		log.error("Database Exception..", e);
		throw new ApplicationException
		    ("Exception : Exception in search college");
	}
	finally{
		JDBCDataSource.closeConnection(conn);
	}
	log.debug("Model search End");
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
public List list(int pageNo,int pageSize){
	log.debug("");
	
	List<CollegeBean> list = new ArrayList<CollegeBean>();
	StringBuffer sql = new StringBuffer
			 ("select * from st_college");
	//System.out.println("test query "+sql);
	Connection conn= null;
	
	if(pageSize>0){
		pageNo= (pageNo-1)*pageSize;
		sql.append(" limit " +pageNo+ ","+pageSize);
		//sql.append(" limit " + pageNo + "," + pageSize);
	}
	System.out.println("sql is"+sql);
	CollegeBean bean;
	try{
		conn = JDBCDataSource.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            bean = new CollegeBean();
            bean.setId(rs.getLong(1));
            bean.setName(rs.getString(2));
            bean.setAddress(rs.getString(3));
            bean.setState(rs.getString(4));
            bean.setCity(rs.getString(5));
            bean.setPhoneNo(rs.getString(6));
            bean.setCreatedBy(rs.getString(7));
            bean.setModifiedBy(rs.getString(8));
            bean.setCreateDateTime(rs.getTimestamp(9));
            bean.setModifiedDateTime(rs.getTimestamp(10));
            list.add(bean);
	}
	}
	catch(Exception e){
		e.printStackTrace();
	}
	finally{
		JDBCDataSource.closeConnection(conn);
	}
	
	return list;
}
/**
 * List.
 *
 * @return the list
 * @throws Exception the exception
 */

public List list()throws ApplicationException{
	return list(0,0);
}
}

