 import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/AddProductServlet")
public class AddProductServlet extends HttpServlet {
    
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String productName = request.getParameter("productName");
    String category = request.getParameter("category");
    int quantity = Integer.parseInt(request.getParameter("quantity"));
    double price = Double.parseDouble(request.getParameter("price"));
    String expiryDate = request.getParameter("expiryDate"); 
    response.setContentType("text/html");
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
        String dbURL = "jdbc:mysql://localhost:3306/pharmacy_db_ajava";
        String dbUsername = "root";
        String dbPassword = ""; 
        Class.forName("com.mysql.cj.jdbc.Driver");

        
        connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

       
        String sql = "INSERT INTO products (product_name, category, quantity, price, expiry_date) VALUES (?, ?, ?, ?, ?)";

        
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, productName);
        preparedStatement.setString(2, category);
        preparedStatement.setInt(3, quantity);
        preparedStatement.setDouble(4, price);
        preparedStatement.setString(5, expiryDate);

        
        int result = preparedStatement.executeUpdate();

        
        PrintWriter out = response.getWriter();
        if (result > 0) {
            out.println("<h3>Product inserted successfully!</h3>");
            out.println("<a href='products.html'><button>Back to Product Management</button></a>");
        } else {
            out.println("<h3>Error inserting product record!</h3>");
            out.println("<a href='products.html'><button>Back to Product Management</button></a>");
        }
    } catch (SQLException | ClassNotFoundException e) {
        response.getWriter().println("<h3>Error: " + e.getMessage() + "</h3>");
        response.getWriter().println("<a href='products.html'><button>Back to Product Management</button></a>");
    } finally {
        
        try {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            
        }
    }
}
}

