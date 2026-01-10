import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/PurchaseServlet")
public class PurchaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/pharmacy_db_ajava?useSSL=false&serverTimezone=UTC";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int productId = Integer.parseInt(request.getParameter("product_id"));
        int quantity = Integer.parseInt(request.getParameter("quantity_" + productId));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                String insertOrder = "INSERT INTO orders (product_id, quantity, order_date) VALUES (?, ?, NOW())";
                try (PreparedStatement orderStmt = connection.prepareStatement(insertOrder)) {
                    orderStmt.setInt(1, productId);
                    orderStmt.setInt(2, quantity);
                    orderStmt.executeUpdate();
                }
                String updateProduct = "UPDATE products SET quantity = quantity - ? WHERE product_id = ?";
                try (PreparedStatement updateStmt = connection.prepareStatement(updateProduct)) {
                    updateStmt.setInt(1, quantity);
                    updateStmt.setInt(2, productId);
                    updateStmt.executeUpdate();
                }

                out.println("<html><body><h3>Order added successfully!</h3>");
                out.println("<a href='ViewOrdersServlet'>Back to Products</a></body></html>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }
    }
}
