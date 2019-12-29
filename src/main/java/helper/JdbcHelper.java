package helper;

import java.sql.*;

/**
 * 提供JDBC连接对象和释放资源
 */
public final class JdbcHelper {
	private static String url = "jdbc:mysql://localhost:3306/kcsj?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
	private static String user = "root";
	private static String password = "hyk123456";

	private JdbcHelper() {}
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("未找到驱动程序类");
		}
	}

	/**
	 * @return 连接对象
	 * @throws SQLException
	 */
	public static Connection getConn() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}
	public static void close(ResultSet rs, Statement stmt, Connection conn) {
		try {
			if (rs != null) {	rs.close();	}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null){	stmt.close();}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null){	conn.close();}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void close(Statement stmt, Connection conn) {
		JdbcHelper.close(null,stmt,conn);
	}
}