import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewProductsServlet")
public class ViewProductsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL =  "jdbc:mysql://maglev.proxy.rlwy.net:34443/pharmacy_db_ajava?useSSL=false&serverTimezone=UTC";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "qqpGDcFpUwEIxHDXKsplMbfznBdbBEye";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        StringBuilder jsonOutput = new StringBuilder();
        jsonOutput.append("[");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                String sql = "SELECT product_id, product_name, category, quantity, price, expiry_date FROM products";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    boolean first = true;
                    while (resultSet.next()) {
                        if (!first) jsonOutput.append(",");
                        first = false;

                        jsonOutput.append("{")
                                  .append("\"product_id\":").append(resultSet.getInt("product_id")).append(",")
                                  .append("\"product_name\":\"").append(resultSet.getString("product_name")).append("\",")
                                  .append("\"category\":\"").append(resultSet.getString("category")).append("\",")
                                  .append("\"quantity\":").append(resultSet.getInt("quantity")).append(",")
                                  .append("\"price\":").append(resultSet.getBigDecimal("price")).append(",")
                                  .append("\"expiry_date\":\"").append(resultSet.getDate("expiry_date")).append("\"")
                                  .append("}");
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            out.print("{\"error\":\"MySQL JDBC Driver not found\"}");
            e.printStackTrace();
            return;
        } catch (SQLException e) {
            out.print("{\"error\":\"Database error: " + e.getMessage() + "\"}");
            e.printStackTrace();
            return;
        }

        jsonOutput.append("]");
        out.print(jsonOutput.toString());
        out.flush();
    }
}

