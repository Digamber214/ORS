package in.co.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.bean.MarksheetBean;
import in.co.bean.StudentBean;
import in.co.exception.ApplicationException;
import in.co.exception.DuplicateRecordException;
import in.co.util.JDBCDataSource;

/**
 * The Class MarksheetModel.
 */
public class MarksheetModel {

	/** The log. */
	Logger log = Logger.getLogger(MarksheetModel.class);

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
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_marksheet");

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			conn.commit();
			pstmt.close();
		}

		catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw new ApplicationException("Exception in Marksheet getting PK");
		}

		return pk = pk + 1;
	}

	/**
	 * Find by roll no.
	 *
	 * @param rollNo the roll no
	 * @return the marksheet bean
	 * @throws ApplicationException the application exception
	 */
	public MarksheetBean findByRollNo(String rollNo) throws ApplicationException {
		log.debug("Model findByRollNo Begin");
		
		System.out.println("find by rolllnoooooooooooo  "+rollNo);
		
		MarksheetBean bean = new MarksheetBean();
		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from st_marksheet where rollNo=?");
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, rollNo);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("findByRollNo  "+rollNo);
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setName(rs.getString(3));
				bean.setPhysics(rs.getInt(4));
				bean.setChemistry(rs.getInt(5));
				bean.setMaths(rs.getInt(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreateDateTime(rs.getTimestamp(9));
				bean.setModifiedDateTime(rs.getTimestamp(10));

			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception" + e);
			e.printStackTrace();
			throw new ApplicationException("Exception in getting marksheet by roll no");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByRollNo End");
		return bean;
	}

	/**
	 * Adds the.
	 *
	 * @param bean the bean
	 * @return the long
	 * @throws Exception the exception
	 */
	public long add(MarksheetBean bean) throws Exception {
		log.debug("");
		Connection conn = null;
		int pk = 0;
		System.out.println("Student id"+bean.getStudentId());
		StudentModel smodel = new StudentModel();
		StudentBean sbean = smodel.findByPk(bean.getStudentId());
		System.out.println("Student bean"+sbean.getFirstName());
		System.out.println("Student bean"+sbean.getLastName());
		bean.setName(sbean.getFirstName() + " " + sbean.getLastName());

		System.out.println("add rollllll  "+bean.getRollNo());
		
		MarksheetBean duplicateBean = findByRollNo(bean.getRollNo());

		System.out.println("roll dupl"+duplicateBean);
		
		if (duplicateBean!=null && duplicateBean.getRollNo()!=null) {
			throw new Exception("Roll no already exist");
			
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_MARKSHEET VALUES(?,?,?,?,?,?,?,?,?,?)");
			pk = nextPk();
			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getRollNo());
			pstmt.setString(3, bean.getName());
			pstmt.setInt(4, bean.getPhysics());
			pstmt.setInt(5, bean.getChemistry());
			pstmt.setInt(6, bean.getMaths());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreateDateTime());
			pstmt.setTimestamp(10, bean.getModifiedDateTime());
			pstmt.executeUpdate();
			conn.commit();
			System.out.println("record inserted");
			pstmt.close();
		}

		catch (Exception e) {
			e.printStackTrace();
			log.error("Database exception " + e);
			conn.rollback();
						//throw new ApplicationException("Exception in add marksheet");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	/**
	 * Delete.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 * @throws SQLException the SQL exception
	 */
	public void delete(MarksheetBean bean) throws ApplicationException, SQLException {
		log.debug("Model delete begin");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_marksheet where id=?");
			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			System.out.println("record deleted");
			pstmt.close();

		} catch (Exception e) {
			log.error("Database exception" + e);
			e.printStackTrace();
			conn.rollback();
			throw new ApplicationException("Exception in delete marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete End");
	}

	/**
	 * Find by pk.
	 *
	 * @param pk the pk
	 * @return the marksheet bean
	 * @throws ApplicationException the application exception
	 */
	public MarksheetBean findByPk(long pk) throws ApplicationException {
		log.debug("Model findByPK begin");
		System.out.println("inside findByPK begin");
		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from st_marksheet where id=?");

		MarksheetBean bean = new MarksheetBean();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setName(rs.getString(3));
				bean.setPhysics(rs.getInt(4));
				bean.setChemistry(rs.getInt(5));
				bean.setMaths(rs.getInt(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreateDateTime(rs.getTimestamp(9));
				bean.setModifiedDateTime(rs.getTimestamp(10));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			log.error("Database exception" + e);
			e.printStackTrace();
			throw new ApplicationException("Exception in getting marksheet by pk");
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
	 * @throws SQLException the SQL exception
	 */
	public void update(MarksheetBean bean) throws ApplicationException, DuplicateRecordException, SQLException {
		log.debug("Model update End");
		Connection conn = null;

		System.out.println("insidse update model");
		MarksheetBean duplicateBean = findByRollNo(bean.getRollNo());

		if (duplicateBean.getRollNo() == bean.getRollNo()) {
			throw new DuplicateRecordException("Rollno already exist");
		}

		StudentModel smodel = new StudentModel();
		StudentBean sbean = smodel.findByPk(bean.getStudentId());
		//System.out.println("bean student"+sbean);
		bean.setName(sbean.getFirstName() + " " + sbean.getLastName());
       
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_MARKSHEET SET rollNo=?,studentID=?,NAME=?,PHYSICS=?,CHEMISTRY=?,MATHS=?,createdBY=?,modifiedBY=?,createDateTime=?,modifiedDateTime=? WHERE ID=? ");

			pstmt.setLong(1, bean.getId());
			pstmt.setString(2, bean.getRollNo());
			pstmt.setString(3, bean.getName());
			pstmt.setInt(4, bean.getPhysics());
			pstmt.setInt(5, bean.getChemistry());
			pstmt.setInt(6, bean.getMaths());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreateDateTime());
			pstmt.setTimestamp(10, bean.getModifiedDateTime());
			
			pstmt.executeUpdate();
			System.out.println("record updated");
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			log.error("Database exc"+e);
			conn.rollback();
			e.printStackTrace();
			throw new ApplicationException("Exception in updating Marksheet");
		} finally {
          JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
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
	public List search(MarksheetBean bean,int pageNo,int pageSize) throws ApplicationException{
		log.debug("Model  search begin");
		StringBuffer sql = new StringBuffer("select * from st_marksheet where 1=1");
		
		if(bean!=null){
			
			
			if(bean.getId()>0){
				sql.append(" And id = '"+bean.getId()+"'");
			}
			/*if(bean.getRollNo()!=null && bean.getRollNo().length()>0){
				sql.append(" AND roll_no like '" + bean.getRollNo() + "%'");
			}*/
			if(bean.getRollNo()!=null && bean.getRollNo().length()>0){
				sql.append(" AND rollno = '" + bean.getRollNo() + "'");
			}
			if(bean.getName()!=null && bean.getName().length()>0){
				sql.append(" And name like '"+bean.getName()+"%'");
				//sql.append(" AND name like '" + bean.getName() + "%'");
			}
			if(bean.getPhysics()>0){
				sql.append(" AND physics = '" + bean.getPhysics() +"'");
			}
			if(bean.getChemistry()>0){
				sql.append(" And chemistry = '"+bean.getChemistry()+"'");
			}
			if(bean.getMaths()>0){
				sql.append(" And maths = '"+bean.getMaths()+"'");
			}
			/*if (bean.getMaths() != null && bean.getMaths() > 0) {
                sql.append(" AND maths = '" + bean.getMaths());
            }*/
		}
		
		if(pageSize>0){
			pageNo = (pageNo-1)*pageSize;
			sql.append(" limit "+pageNo+","+pageSize);
		}
		
		System.out.println("query is "+sql);
		List<MarksheetBean> list = new ArrayList();
		
		Connection conn= null;
		try{
			conn= JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			
			ResultSet rs = pstmt.executeQuery();
			MarksheetBean bean1;
			while(rs.next()){
				bean1 = new MarksheetBean();
				bean1.setId(rs.getLong(1));
				bean1.setRollNo(rs.getString(2));
                bean1.setName(rs.getString(3));
                bean1.setPhysics(rs.getInt(4));
                bean1.setChemistry(rs.getInt(5));
                bean1.setMaths(rs.getInt(6));
                bean1.setCreatedBy(rs.getString(7));
                bean1.setModifiedBy(rs.getString(8));
                bean1.setCreateDateTime(rs.getTimestamp(9));
                bean1.setModifiedDateTime(rs.getTimestamp(10));
			    list.add(bean1);
			}
			rs.close();
			pstmt.close();
		}
		catch(Exception e){
			log.error(e);
			e.printStackTrace();
			throw new ApplicationException("Exception in search"+e.getMessage());
		} 
		finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  search End");
		return list;
		
	}

	/**
	 * Search.
	 *
	 * @param bean the bean
	 * @return the list
	 * @throws ApplicationException the application exception
	 */
	public List search(MarksheetBean bean) throws ApplicationException{
		return search(bean,0,0);
	}
	
	/**
	 * List.
	 *
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the list
	 * @throws ApplicationException the application exception
	 */
	public List list(int pageNo,int pageSize) throws ApplicationException{
		log.debug("Model  list begin");
		
		StringBuffer sql = new StringBuffer("select * from st_marksheet");
		
		
		if(pageSize>0){
			pageNo= (pageNo-1)*pageSize;
			sql.append(" limit "+pageNo+","+pageSize);
			
		}
		List<MarksheetBean> list = new ArrayList();
		
	
		
		Connection conn= null;
		try{
			conn= JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			//System.out.println("pssss");
			ResultSet rs = pstmt.executeQuery();
			System.out.println("inside rs"+rs);
			while(rs.next()){
				MarksheetBean bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
                bean.setName(rs.getString(3));
                bean.setPhysics(rs.getInt(4));
                bean.setChemistry(rs.getInt(5));
                bean.setMaths(rs.getInt(6));
                bean.setCreatedBy(rs.getString(7));
                bean.setModifiedBy(rs.getString(8));
                bean.setCreateDateTime(rs.getTimestamp(9));
                bean.setModifiedDateTime(rs.getTimestamp(10));
                list.add(bean);
			}
			rs.close();
			pstmt.close();
		}
		catch(Exception e){
			//log.error(e.getMessage());
			e.printStackTrace();
			//throw new ApplicationException("Exception in getting list of Marksheet");
		}
		finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  list End");
		return list;
	}
	
	/**
	 * List.
	 *
	 * @return the list
	 * @throws ApplicationException the application exception
	 */
	public List list() throws ApplicationException{
		return list(0,0);
	}
	
	/**
	 * Gets the merit list.
	 *
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the merit list
	 * @throws ApplicationException the application exception
	 */
	public List getMeritList(int pageNo,int pageSize) throws ApplicationException{
		System.out.println("model is");
		
		log.debug("Model  MeritList begin");
		StringBuffer sql = new StringBuffer
				("SELECT ID,ROLLNO,NAME,PHYSICS,CHEMISTRY,MATHS"
						+ ",(PHYSICS +CHEMISTRY +MATHS) as total from ST_MARKSHEET WHERE NOT (PHYSICS<33 OR MATHS<33 OR CHEMISTRY<33) ORDER BY"
						+ " total DESC");
//				 "SELECT `ID`,`ROLLNO`, `NAME`, `PHYSICS`, `CHEMISTRY`, `MATHS`"
//				 + " , (PHYSICS + CHEMISTRY + MATHS) as total from `ST_MARKSHEET` order by total desc");
		if(pageSize>0){
			pageNo= (pageNo-1)*pageSize;
			sql.append(" LIMIT "+pageNo+","+pageSize);
		}
		
		System.out.println("sql is   "+sql);
		Connection conn= null;
		List<MarksheetBean> list = new ArrayList<MarksheetBean>();
		try{
		  conn= JDBCDataSource.getConnection();
		  PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		  ResultSet rs = pstmt.executeQuery();
		  while(rs.next()){
			  MarksheetBean bean= new MarksheetBean();
			  bean.setId(rs.getLong(1));
			  bean.setRollNo(rs.getString(2));
              bean.setName(rs.getString(3));
              bean.setPhysics(rs.getInt(4));
              bean.setChemistry(rs.getInt(5));
              bean.setMaths(rs.getInt(6));
              list.add(bean);
              System.out.println(bean.getName());
		  }
		}
		catch(Exception e){
		//	log.error(e);
			e.printStackTrace();
			//throw new ApplicationException("Exception in getting merit list of Marksheet");
		}
		finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  MeritList End");
		return list;
	}
}
