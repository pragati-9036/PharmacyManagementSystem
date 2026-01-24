import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteOrderServlet")
public class DeleteOrderServlet extends HttpServlet {
     @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://maglev.proxy.rlwy.net:34443/pharmacy_db_ajava?useSSL=false&serverTimezone=UTC", "root", "qqpGDcFpUwEIxHDXKsplMbfznBdbBEye")) {
                Statement stmt = con.createStatement();
                String query = "DELETE FROM orders WHERE id = " + id;
                int result = stmt.executeUpdate(query);
                if (result > 0) {
                    out.println("<h3>Order deleted successfully for ID: " + id + "</h3>");
                } else {
                    out.println("<h3>Failed to delete order. Check the ID.</h3>");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}
