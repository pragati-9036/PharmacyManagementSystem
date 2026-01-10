import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {
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
        out.println("<title>Report - Product & Order</title>");
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
        
        // Header section with a back button
        out.println("<div class='container'>");
       
        out.println("<h2>Product and Order Report</h2>");
         out.println("<div class='header'>");
        out.println("<a href='home.html' class='back-btn'>Back</a>");
        out.println("</div>");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                String query = "SELECT p.product_id, p.product_name, p.category, p.quantity AS stock_quantity, p.price, " +
                               "COALESCE(SUM(o.quantity), 0) AS total_quantity_sold, " +
                               "(p.quantity - COALESCE(SUM(o.quantity), 0)) AS remaining_stock, " +
                               "COALESCE(SUM(o.quantity * p.price), 0) AS total_sales " +
                               "FROM products p " +
                               "LEFT JOIN orders o ON p.product_id = o.product_id " +
                               "GROUP BY p.product_id, p.product_name, p.category, p.quantity, p.price";

                try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                    out.println("<table>");
                    out.println("<thead>");
                    out.println("<tr><th>Product Name</th><th>Category</th><th>Price (₹)</th><th>Remaining Stock</th><th>Total Quantity Sold</th><th>Total Sales (₹)</th></tr>");
                    out.println("</thead>");
                    out.println("<tbody>");

                    if (!rs.isBeforeFirst()) {
                        out.println("<tr><td colspan='6' class='error-message'>No data available</td></tr>");
                    } else {
                        while (rs.next()) {
                            out.println("<tr>");
                            out.println("<td>" + rs.getString("product_name") + "</td>");
                            out.println("<td>" + rs.getString("category") + "</td>");
                            out.println("<td>" + rs.getDouble("price") + "</td>");
                            out.println("<td>" + rs.getInt("remaining_stock") + "</td>");
                            out.println("<td>" + rs.getInt("total_quantity_sold") + "</td>");
                            out.println("<td>" + rs.getDouble("total_sales") + "</td>");
                            out.println("</tr>");
                        }
                    }
                    out.println("</tbody>");
                    out.println("</table>");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
