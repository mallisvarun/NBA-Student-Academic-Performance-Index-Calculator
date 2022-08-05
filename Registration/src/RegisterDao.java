import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class RegisterDao {
	private String dbUrl="jdbc:mysql://localhost:3306/userdb";
	private String dbUname="root";
	private String dbPassword="admin";
	private String dbDriver="com.mysql.cj.jdbc.Driver";
	
	public void loadDriver(String dbDriver) {
		try {
		Class.forName(dbDriver);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection() {
		Connection con=null;
		try {
			con=DriverManager.getConnection(dbUrl,dbUname,dbPassword);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return con;
	}
	public String insert(Member member)
	{
		loadDriver(dbDriver);
		Connection con=getConnection();
		String result="DATA ENTERED!";
		String sql = "insert into student2 values(?,?,?,?,?,?)";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1,member.getSname());
			ps.setString(2,member.getSroll());
			ps.setString(3,member.getS1());
			ps.setString(4,member.getS2());
			ps.setString(5,member.getS3());
			ps.setString(6,member.getS4());
			ps.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
			result="data not entered";
		}
		return result;
		
	}
	
}
