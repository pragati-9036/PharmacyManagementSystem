import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/UpdateProductServlet")
public class UpdateProductServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pharmacy_db_ajava?useSSL=false&serverTimezone=UTC";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = ""; 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head><title>Update Product Stock</title></head>");
            out.println("<body>");
            out.println("<h2>Update Product Stock</h2>");
            out.println("<form action='UpdateProductServlet' method='POST'>");
            out.println("Product ID: <input type='text' name='productId' required><br><br>");
            out.println("New Quantity: <input type='number' name='newQuantity' required><br><br>");
            out.println("<button type='submit'>Update Stock</button>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    try (PrintWriter out = response.getWriter()) {
        String productIdStr = request.getParameter("productId");
        String newQuantityStr = request.getParameter("newQuantity");

        if (productIdStr == null || newQuantityStr == null || productIdStr.isEmpty() || newQuantityStr.isEmpty()) {
            out.println("<h3>Error: All fields are required.</h3>");
            out.println("<button onclick=\"window.history.back();\">Go Back</button>");
            return;
        }

        try {
            int productId = Integer.parseInt(productIdStr);
            int newQuantity = Integer.parseInt(newQuantityStr);

            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                String sql = "UPDATE products SET quantity = ? WHERE product_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, newQuantity);
                    pstmt.setInt(2, productId);
                    int rowsUpdated = pstmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        out.println("<h3>Stock updated successfully for Product ID: " + productId + "</h3>");
                    } else {
                        out.println("<h3>No product found with ID: " + productId + "</h3>");
                    }
                }
            }
        } catch (NumberFormatException e) {
            out.println("<h3>Error: Invalid input. Please enter numeric values for Product ID and Quantity.</h3>");
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h3>Error: Database error occurred - " + e.getMessage() + "</h3>");
        } catch (ClassNotFoundException e) {
            out.println("<h3>Error: MySQL JDBC Driver not found.</h3>");
        }
        out.println("<button onclick=\"window.location.href='products.html';\">Back to Product Management</button>");
    }
}
}
