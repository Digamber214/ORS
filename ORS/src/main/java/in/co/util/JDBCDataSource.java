package in.co.util;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import in.co.exception.ApplicationException;

// TODO: Auto-generated Javadoc
/**
 * The Class JDBCDataSource.
 */
public final class JDBCDataSource {

	/** JDBC Database connection pool ( DCP ). */
	private static JDBCDataSource datasource;

	/**
	 * Instantiates a new JDBC data source.
	 */
	private JDBCDataSource() {
	}

	/** The cpds. */
	private ComboPooledDataSource cpds = null;

	/**
	 * Create instance of Connection Pool.
	 *
	 * @return single instance of JDBCDataSource
	 */
	public static JDBCDataSource getInstance() {
		if (datasource == null) {

			//ResourceBundle rb = ResourceBundle.getBundle("in.co.bundle.system");

			datasource = new JDBCDataSource();
			datasource.cpds = new ComboPooledDataSource();
			try {
				datasource.cpds.setDriverClass(PropertyReader.getValue("driver"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			//datasource.cpds.setJdbcUrl(rb.getString("url"));
			datasource.cpds.setJdbcUrl(PropertyReader.getValue("url"));
			datasource.cpds.setUser(PropertyReader.getValue("username"));
			datasource.cpds.setPassword(PropertyReader.getValue("password"));
			datasource.cpds.setInitialPoolSize(DataUtility.getInt(PropertyReader.getValue("initialpoolSize")));
			datasource.cpds.setAcquireIncrement(DataUtility.getInt(PropertyReader.getValue("acquireIncrement")));
			datasource.cpds.setMaxPoolSize(DataUtility.getInt(PropertyReader.getValue("maxPoolSize")));
			datasource.cpds.setMaxIdleTime(DataUtility.getInt(PropertyReader.getValue("timeout")));
			datasource.cpds.setMinPoolSize(DataUtility.getInt(PropertyReader.getValue("minPoolSize")));

		}
		return datasource;
	}

	/**
	 * Gets the connection from ComboPooledDataSource.
	 *
	 * @return connection
	 * @throws Exception the exception
	 */
	public static Connection getConnection() throws Exception {
		return getInstance().cpds.getConnection();
	}

	/**
	 * Closes a connection.
	 *
	 * @param connection the connection
	 */
	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Trn rollback.
	 *
	 * @param connection the connection
	 * @throws ApplicationException the application exception
	 */
	public static void trnRollback(Connection connection) throws ApplicationException {
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
				throw new ApplicationException(ex.toString());
			}
		}
	}

}
