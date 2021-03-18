package com.abc.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class DisplayRecords extends TagSupport {

	String table;
	Connection connection = null;

	public DisplayRecords() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql:///abc";
			String username = "root";
			String password = "root123";

			connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	public void setTable(String table) {
		this.table = table;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {

		if (connection != null) {

			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("select * from " + table);
				ResultSetMetaData metaData = resultSet.getMetaData();
				int columnCount = metaData.getColumnCount();

				JspWriter out = pageContext.getOut();
				out.println("<html><body>");
				out.println("<h1 style='color:red; text-align:center;'>Aradhya Brilliance Center</h1>");
				out.println("<h2 style='color:blue; text-align:center;'>" + table + " details are </h2>");

				out.println("<table border='1' align='center' bgcolor='cyan'>");

				// 1st row contains heading,since columns count is not know run a for loop
				out.println("<tr>");
				for (int i = 1; i <= columnCount; i++)
					out.println("<th>" + metaData.getColumnName(i) + "</th>");
				out.println("</tr>");

				// 2nd row contains data,how many rows in table is not know so run while loop

				while (resultSet.next()) {
					out.println("<tr>");

					// Every row how many columns is not known so run while loop
					for (int i = 1; i <= columnCount; i++)
						out.println("<td>" + resultSet.getString(i) + "</td>");
					
					
					out.println("</tr>");

				}
				out.println("</table>");
				out.println("</body></html>");

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return SKIP_BODY;
	}

}
