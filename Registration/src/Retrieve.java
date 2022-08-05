

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class Retrieve
 */
@WebServlet("/Retrieve")

public class Retrieve extends HttpServlet {
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
	
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Retrieve() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		loadDriver(dbDriver);
		Connection con=getConnection();
		String sql= "select * from student2";
		PreparedStatement stat;
		float mean=0;float cnt=0;float cnt2=0;float mean2=0;
		try {
			stat = con.prepareStatement(sql);
			ResultSet result=stat.executeQuery();
			while(result.next()) {
				float s1=result.getFloat(3);
				float s2=result.getFloat(4);
				float s3=result.getFloat(5);
				float s4=result.getFloat(6);
				if(s1!=0 && s2!=0 && s3!=0 && s4!=0) {
					mean+=(s1+s2+s3+s4)/4;
					cnt++;
				}
				mean2+=(s1+s2+s3+s4)/4;
				cnt2++;
			}
			float temp=cnt/cnt2;
//			float api=mean*temp;
			float temp2=mean/mean2;
			float temp3=temp2*temp;
			float per=temp3*100;
			double api=per/9.5;
			PrintWriter out=response.getWriter();
			String table="<table style='margin-left:auto;margin-right:auto;margin-top:40px;border:1px solid black;border-collapse:collapse;border-radius:10px'>"
					+ "<tr><td style='border:2px solid black;padding:10px'><b>MEAN OF CGPA(X)</b></td><td style='border:2px solid black;padding:10px;'>"+mean+"</td></tr>"
					+ "<tr><td style='border:2px solid black;padding:10px;'><b>No. of Successful Students(Y)</b></td><td style='border:2px solid black;padding:10px;'>"+cnt+"</td></tr>"
					+ "<tr><td style='border:2px solid black;padding:10px;'><b>No. of Appeared Students(Z)</b></td><td style='border:2px solid black;padding:10px;'>"+cnt2+"</td></tr>"
					+"<tr><td style='border:2px solid black;padding:10px;'><b>Academic Performance Index (Scale:10)</b></td><td style='border:2px solid black;padding:10px;'>"+api+"</td></tr></table>";
			out.print(table);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
