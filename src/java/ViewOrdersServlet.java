import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ViewOrdersServlet")
public class ViewOrdersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/pharmacy_db_ajava?useSSL=false&serverTimezone=UTC";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Orders - Pharmacy Management System</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; margin: 0; padding: 0; }");
        out.println(".container { width: 80%; margin: 20px auto; background: white; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px 0px #ccc; }");
        out.println(".header { display: flex; align-items: center; justify-content: flex-end; padding: 10px; }");
        out.println(".back-btn { background-color: #f44336; color: white; border: none; padding: 8px 12px; cursor: pointer; border-radius: 5px; text-decoration: none; font-size: 14px; }");
        out.println(".back-btn:hover { background-color: #e53935; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        out.println("table, th, td { border: 1px solid black; }");
        out.println("th, td { padding: 10px; text-align: center; }");
        out.println("th { background-color: #4CAF50; color: white; }");
        out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
        out.println("tr:hover { background-color: #ddd; }");
        out.println(".error-message { color: red; font-weight: bold; margin-top: 10px; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        
        

        out.println("<div class='container'>");
        out.println("<h2>Sales List</h2>");
        out.println("<div class='header'>");
        out.println("<a href='sales.html' class='back-btn'>Back</a>");
        out.println("</div>");
        out.println("<table>");
        out.println("<thead>");
        out.println("<tr><th>Order ID</th><th>Product Name</th><th>Quantity</th><th>Total Price (₹)</th><th>Order Date</th></tr>");
        out.println("</thead>");
        out.println("<tbody>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                 Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT o.id AS order_id, p.product_name, o.quantity, o.total_price, o.order_date FROM orders o JOIN products p ON o.product_id = p.product_id ORDER BY o.order_date DESC")) {

                if (!rs.isBeforeFirst()) { 
                    out.println("<tr><td colspan='5' class='error-message'>No orders found.</td></tr>");
                } else {
                    while (rs.next()) {
                        out.println("<tr>");
                        out.println("<td>" + rs.getInt("order_id") + "</td>");
                        out.println("<td>" + rs.getString("product_name") + "</td>");
                        out.println("<td>" + rs.getInt("quantity") + "</td>");
                        out.println("<td>" + rs.getBigDecimal("total_price") + "</td>");
                        out.println("<td>" + rs.getTimestamp("order_date") + "</td>");
                        out.println("</tr>");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println("<tr><td colspan='5' class='error-message'>Error: Could not retrieve data.</td></tr>");
            e.printStackTrace(); 
        }

        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
