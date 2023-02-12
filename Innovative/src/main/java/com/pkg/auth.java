package com.pkg;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.util.*;
import java.nio.file.Paths;
import java.nio.file.Files;

public class auth extends HttpServlet {
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		System.out.println("posted");		
		try {
			String url = "jdbc:mysql://localhost:4000/aj";
			String sql_uname = "root";
			String sql_pass = "root";
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url,sql_uname,sql_pass);			
			Statement st = con.createStatement();
			String auth_type = req.getParameter("auth_type");
			if(auth_type.equals("signup"))
			{
				ResultSet rs = st.executeQuery("select * from user_data where email='"+req.getParameter("email")+"'");
				int line=0;
				while(rs.next())line++;
				if(line>0)
				{
					System.out.println("Account Already exists with same email ID");
				}
				else
				{
					String user_type = req.getParameter("user_type");
					rs = st.executeQuery("select user_id from user_data");
					
					List<String> curr_ids = new ArrayList<>();
					while(rs.next()) {curr_ids.add(rs.getString(1).substring(1));}
					
					
					Random rd = new Random();				
					String rndm_id = Integer.toString(rd.nextInt(10000,99999));
					while(curr_ids.contains(rndm_id))rndm_id = Integer.toString(rd.nextInt(10000,99999));
					
					
					st.executeUpdate("insert into user_data values('"+user_type.charAt(0)+rndm_id+"','"+req.getParameter("email")+"','"+req.getParameter("firstname")+"','"+req.getParameter("lastname")+"','"+req.getParameter("password")+"')");
					System.out.println("Data Saved");																																																								
				}
			}
			else 
			{
				String username = req.getParameter("username");
				ServletContext context = req.getServletContext();
				System.out.println(context.getRealPath("consumer.html"));
				System.out.println(context.getRealPath("supplier.html"));
				
				ResultSet rs = st.executeQuery("select * from user_data where user_id='"+username+"' OR email='"+username+"'");
				if(rs.next()){
					if(rs.getString("password").equals(req.getParameter("password")))
					{
						System.out.println("Success.."+rs.getString("first_name")+" "+rs.getString("last_name"));
						if(rs.getString(1).charAt(0)=='C')
							{
							String html = new String(Files.readAllBytes(Paths.get(context.getRealPath("consumer.html"))));
							html = html.replace("{{Name}}",rs.getString("first_name")+" "+rs.getString("last_name"));
							res.setContentType("text/html");
							PrintWriter out = res.getWriter();
							
							
							
							out.print(html);
							
							
							
							}
						else if(rs.getString(1).charAt(0)=='S')
							{
							res.sendRedirect("supplier.html");
							}
						else if(rs.getString(1).charAt(0)=='M') 
							{
							res.sendRedirect("manufacturer.html");
							}
						else res.sendRedirect("index.html");
						
					}
					else 
					{
						System.out.println("Invalid Creadiantials");
						res.sendRedirect("index.html");
						
					}
					}
				else 
				{
					System.out.println("Account doesn't exist");
					res.sendError(101,"No Account Found");
//					res.sendRedirect("index.html");				
				}
			}
						
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
}

