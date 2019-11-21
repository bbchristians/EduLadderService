
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;

public class DBConnection {

	public static Connection getConnection() throws SQLException {
		Connection conn = getPostgressDataSource().getConnection();
		return conn;
	}

	public static DataSource getPostgressDataSource() {
		PGSimpleDataSource dataSource = new PGSimpleDataSource();

		// Set dataSource Properties
		dataSource.setServerName("ec2-174-129-253-63.compute-1.amazonaws.com");
		dataSource.setPortNumber(5432);
		dataSource.setDatabaseName("d334oe73gmbr7c");
		dataSource.setUser("vkppgdtlutnedj");
		dataSource.setPassword("1964c833cfcf97395c4f31d3b0663a164f25dce503bf532b81acfe6877f9dfce");
		dataSource.setSslMode("require");

		return dataSource;
	}

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = getPostgressDataSource().getConnection();
			System.out.println(conn.getCatalog());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
