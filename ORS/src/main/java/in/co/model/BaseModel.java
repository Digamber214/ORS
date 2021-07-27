package in.co.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import in.co.exception.ApplicationException;
import in.co.exception.DataBaseException;
import in.co.util.DataUtility;
import in.co.util.JDBCDataSource;

// TODO: Auto-generated Javadoc
/**
 * The Class BaseModel.
 */
public abstract class BaseModel implements Comparable<BaseModel> {

	/** The log. */
	private static Logger log = Logger.getLogger(BaseModel.class);
	
	/** Non Business primary key. */
	protected long id;
	/**
	 * User name that creates this record.
	 */
	protected String createdBy;
	/**
	 * User name that modifies this record.
	 */
	protected String modifiedBy;
	
	/** Created timestamp of record. */
	protected Timestamp createDateTime;
	
	/** Modified timestamp of record. */

	protected Timestamp modifiedDateTime;

	/**
	 * accessor methods.
	 *
	 * @return the id
	 */

	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the created date time.
	 *
	 * @return the created date time
	 */
	public Timestamp getCreateDateTime() {
		return createDateTime;
	}

	/**
	 * Sets the created date time.
	 *
	 * @param createdDateTime the new created date time
	 */
	public void setCreateDateTime(Timestamp createDateTime) {
		this.createDateTime = createDateTime;
	}

	/**
	 * Gets the modified date time.
	 *
	 * @return the modified date time
	 */
	public Timestamp getModifiedDateTime() {
		return modifiedDateTime;
	}

	/**
	 * Sets the modified date time.
	 *
	 * @param modifiedDateTime the new modified date time
	 */
	public void setModifiedDateTime(Timestamp modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the modified by.
	 *
	 * @return the modified by
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * Sets the modified by.
	 *
	 * @param modifiedBy the new modified by
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * Compares IDs ( Primary Key). If keys are equals then objects are equals.
	 *
	 * @param next the next
	 * @return the int
	 */
	public int compareTo(BaseModel next) {

		int n = (int) (id - next.getId());
		return n;

	}

	/**
	 * created next PK of record.
	 *
	 * @return the long
	 * @throws DataBaseException the data base exception
	 */
	public long nextPK() throws DataBaseException {

		log.debug("Model nextPK Started");
		long pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from" + getTableName());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DataBaseException("Exception : Exception in getting PK");
		}

		log.debug("Model nextPK end");
		return pk = pk + 1;
	}

	/**
	 * Gets table name of Model.
	 *
	 * @return the table name
	 */
	public abstract String getTableName();

	/**
	 * Updates created by info.
	 *
	 * @throws ApplicationException the application exception
	 */
	public void updateCreatedInfo() throws ApplicationException {

		log.debug("model update info started" + createdBy);
		Connection conn = null;
		String sql = "update" + " " + getTableName()+" " + "set createdBy=? ,createDateTime=? where id=?";

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, createdBy);
			pstmt.setTimestamp(2, DataUtility.getCurrentTimestamp());
			pstmt.setLong(3, id);

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			JDBCDataSource.trnRollback(conn);
			throw new ApplicationException(e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update info end");
	}

	/**
	 * Updates modified by info.
	 *
	 * @throws ApplicationException the application exception
	 */
	public void updateModifiedInfo() throws ApplicationException {

		log.debug("Update modified info started");
		Connection conn = null;
		String sql =  "update" + getTableName() + "set modifiedby =?,modifiedDateTime=? where id=?";

		log.debug(sql);

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, modifiedBy);
			pstmt.setTimestamp(2, DataUtility.getCurrentTimestamp());
			pstmt.setLong(3, id);

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception " + e);
			JDBCDataSource.trnRollback(conn);
			throw new ApplicationException(e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Update modified info end");
	}

	/**
	 * Populate Model from ResultSet.
	 *
	 * @param <T> the generic type
	 * @param rs the rs
	 * @param model the model
	 * @return the t
	 * @throws SQLException the SQL exception
	 */
	protected <T extends BaseModel> T populateModel(ResultSet rs, T model) throws SQLException {

		model.setId(rs.getLong("ID"));
		model.setCreatedBy(rs.getString("CREATEDBY"));
		model.setModifiedBy(rs.getString("MODIFIEDBY"));
		model.setCreateDateTime(rs.getTimestamp("CREATEDATETIME"));
		model.setModifiedDateTime(rs.getTimestamp("MODIFIEDDATETIME"));
		return model;
	}
}
