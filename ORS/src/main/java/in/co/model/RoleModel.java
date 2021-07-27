package in.co.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import in.co.bean.RoleBean;
import in.co.exception.ApplicationException;
import in.co.exception.DuplicateRecordException;
import in.co.util.JDBCDataSource;

/**
 * The Class RoleModel.
 */
public class RoleModel {

	/** The log. */
	Logger log = Logger.getLogger(RoleModel.class);

	/**
	 * Next pk.
	 *
	 * @return the integer
	 * @throws ApplicationException the application exception
	 */
	public Integer nextPk() throws ApplicationException {
		log.debug("Model nextPK Begin");
		int pk = 0;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_role");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception" + e);
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting PK");
		}
		log.debug("Model nextPK End");
		return pk = pk + 1;
	}

	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the role bean
	 * @throws ApplicationException the application exception
	 */
	public RoleBean findByName(String name) throws ApplicationException {
		log.debug("Model findByName Started");
		RoleBean bean = new RoleBean();

		StringBuffer sql = new StringBuffer("select * from st_role where name=?");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int pk = 0;
				pk = nextPk();
				// can we take pk
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreateDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));

			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting User by findByName");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByName Started");
		return bean;
	}

	/**
	 * Adds the.
	 *
	 * @param bean the bean
	 * @return the long
	 * @throws ApplicationException the application exception
	 * @throws SQLException the SQL exception
	 * @throws DuplicateRecordException the duplicate record exception
	 */
	public long add(RoleBean bean) throws ApplicationException, SQLException, DuplicateRecordException {
		log.debug("Model add Begin");
		Connection conn = null;

		RoleBean duplicateBean = findByName(bean.getName());
		// Check if create Role already exist
		if (duplicateBean.getName()!=null) {
			throw new DuplicateRecordException("already exists");
		}
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_role values(?,?,?,?,?,?,?)");

			pk = nextPk();
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getDescription());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreateDateTime());
			pstmt.setTimestamp(7, bean.getModifiedDateTime());
			pstmt.executeUpdate();
			System.out.println("record inserted");
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception" + e);
			conn.rollback();
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in add Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	/**
	 * Find by pk.
	 *
	 * @param pk the pk
	 * @return the role bean
	 * @throws ApplicationException the application exception
	 */
	public RoleBean findByPk(long pk) throws ApplicationException {
		log.error("Model findByPK Started");
		StringBuffer sql = new StringBuffer("select * from st_role where id=?");

		Connection conn = null;
		RoleBean bean = new RoleBean();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreateDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));

			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			log.error("Databade exception" + e);
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK end");
		return bean;
	}

	/**
	 * Delete.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 * @throws SQLException the SQL exception
	 */
	public void delete(RoleBean bean) throws ApplicationException, SQLException {
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_role where id=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			log.error("Database exception" + e);
			conn.rollback();
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in delete Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete end");
	}

	/**
	 * Update.
	 *
	 * @param bean the bean
	 * @throws DuplicateRecordException the duplicate record exception
	 * @throws ApplicationException the application exception
	 */
	public void update(RoleBean bean) throws DuplicateRecordException, ApplicationException {
		log.debug("Model update Started");

		Connection conn = null;

		/*RoleBean duplicate = findByName(bean.getName());

		if (duplicate != null ) {
			throw new DuplicateRecordException("Role already exists");
		}*/
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_ROLE SET NAME=?,DESCRIPTION=?,CREATEDBY=?,MODIFIEDBY=?,CREATEDATETIME=?,MODIFIEDDATETIME=? WHERE ID=?");

			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getDescription());
			pstmt.setString(3, bean.getCreatedBy());
			pstmt.setString(4, bean.getModifiedBy());
			pstmt.setTimestamp(5, bean.getCreateDateTime());
			pstmt.setTimestamp(6, bean.getModifiedDateTime());
			pstmt.setLong(7, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			System.out.println("record updated");
			pstmt.close();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw new ApplicationException("");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		log.debug("Model update end");
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
	public List search(RoleBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		
	        StringBuffer sql = new StringBuffer("SELECT * FROM ST_ROLE WHERE 1=1");

	        if (bean != null) {
	            if (bean.getId() > 0) {
	                sql.append(" AND id = " + bean.getId());
	            }
	            if (bean.getName() != null && bean.getName().length() > 0) {
	                sql.append(" AND NAME like '" + bean.getName() + "%'");
	            }
	            if (bean.getDescription() != null
	                    && bean.getDescription().length() > 0) {
	                sql.append(" AND DESCRIPTION like '" + bean.getDescription()
	                        + "%'");
	            }

	        }

	        // if page size is greater than zero then apply pagination
	        if (pageSize > 0) {
	            // Calculate start record index
	            pageNo = (pageNo - 1) * pageSize;

	            sql.append(" Limit " + pageNo + ", " + pageSize);
	            // sql.append(" limit " + pageNo + "," + pageSize);
	        }

	        ArrayList list = new ArrayList();
	        Connection conn = null;
	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                bean = new RoleBean();
	                bean.setId(rs.getLong(1));
	                bean.setName(rs.getString(2));
	                bean.setDescription(rs.getString(3));
	                bean.setCreatedBy(rs.getString(4));
	                bean.setModifiedBy(rs.getString(5));
	                bean.setCreateDateTime(rs.getTimestamp(6));
	                bean.setModifiedDateTime(rs.getTimestamp(7));
	                list.add(bean);
	            }
	            rs.close();
	        } catch (Exception e) {
	            log.error("Database Exception..", e);
	            throw new ApplicationException(
	                    "Exception : Exception in search Role");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }

	        log.debug("Model search End");
	        return list;
	    }	
	

	/**
	 * Search.
	 *
	 * @param bean the bean
	 * @return the list
	 * @throws ApplicationException the application exception
	 */
	public List search(RoleBean bean) throws ApplicationException {
		return search(bean, 0, 0);
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
		log.debug("Model list begin");

		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from ST_ROLE");

		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		List<RoleBean> list = new ArrayList();

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				RoleBean bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreateDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));
				list.add(bean);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting list of Role");
		}
		log.debug("Model list End");
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
}
