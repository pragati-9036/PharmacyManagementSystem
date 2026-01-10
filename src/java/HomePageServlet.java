import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HomePageServlet")
public class HomePageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
        response.setHeader("Pragma", "no-cache"); 
        response.setHeader("Expires", "0"); 

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Home - Pharmacy Management System</title>");
        out.println("<style>");
 
        out.println("body {");
        out.println("    font-family: Arial, sans-serif;");
        out.println("    margin: 0;");
        out.println("    padding: 0;");
        out.println("    background-image: url('images/pharmacybg.jpg');");
        out.println("    background-size: cover;");
        out.println("    background-position: center;");
        out.println("    color: #333;");
        out.println("    line-height: 1.6;");
        out.println("}");

        out.println(".sidebar {");
        out.println("    height: 100%;");
        out.println("    width: 250px;");
        out.println("    position: fixed;");
        out.println("    top: 0;");
        out.println("    left: 0;");
        out.println("    background-color: #4CAF50;");
        out.println("    padding-top: 20px;");
        out.println("    color: white;");
        out.println("    text-align: center;");
        out.println("}");

        out.println(".sidebar h2 {");
        out.println("    font-size: 1.8em;");
        out.println("    margin-bottom: 30px;");
        out.println("}");

        out.println(".sidebar a {");
        out.println("    display: block;");
        out.println("    padding: 15px;");
        out.println("    text-decoration: none;");
        out.println("    color: white;");
        out.println("    font-size: 18px;");
        out.println("    border-bottom: 1px solid #ddd;");
        out.println("}");

        out.println(".sidebar a:hover {");
        out.println("    background-color: #45a049;");
        out.println("}");

        /* Content section styles */
        out.println(".content {");
        out.println("    margin-left: 260px;");
        out.println("    padding: 40px;");
        out.println("    background-color: rgba(255, 255, 255, 0.8);");
        out.println("    border-radius: 10px;");
        out.println("    margin-top: 20px;");
        out.println("}");

        out.println(".content h1 {");
        out.println("    color: #333;");
        out.println("    font-size: 2em;");
        out.println("}");

        out.println(".content p {");
        out.println("    font-size: 18px;");
        out.println("    line-height: 1.6;");
        out.println("    margin-bottom: 20px;");
        out.println("}");

        out.println(".content ul {");
        out.println("    list-style-type: none;");
        out.println("    padding: 0;");
        out.println("}");

        out.println(".content ul li {");
        out.println("    margin: 10px 0;");
        out.println("}");

        out.println(".content ul li a {");
        out.println("    text-decoration: none;");
        out.println("    color: #3498db;");
        out.println("    font-size: 18px;");
        out.println("    font-weight: bold;");
        out.println("}");

        out.println(".content ul li a:hover {");
        out.println("    color: #2980b9;");
        out.println("}");

        /* Footer styles */
        out.println(".footer {");
        out.println("    text-align: center;");
        out.println("    padding: 10px;");
        out.println("    background-color: #4CAF50;");
        out.println("    color: white;");
        out.println("    position: fixed;");
        out.println("    bottom: 0;");
        out.println("    width: 100%;");
        out.println("}");

        /* Logout button styles */
        out.println(".logout {");
        out.println("    margin-top: 20px;");
        out.println("    padding: 10px;");
        out.println("    background-color: #f44336;");
        out.println("    color: white;");
        out.println("    border: none;");
        out.println("    cursor: pointer;");
        out.println("    width: 100%;");
        out.println("    font-size: 18px;");
        out.println("}");

        out.println(".logout:hover {");
        out.println("    background-color: #e53935;");
        out.println("}");
        
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        // Sidebar
        out.println("<div class='sidebar'>");
        out.println("<h2>MyPharmacy</h2>");
        out.println("<a href='home.html'>Home</a>");
        out.println("<a href='products.html'>Product Management</a>");
        out.println("<a href='sales.html'>Sales Management</a>");
        out.println("<a href='ReportServlet'>Reports</a>");
        
        out.println("<button class='logout' onclick='window.location.href=\"LogoutServlet\"'>Logout</button>");
        out.println("</div>");

        // Content
        out.println("<div class='content'>");
        out.println("<h1>Welcome to MyPharmacy</h1>");
        out.println("<p>Welcome to the Admin Dashboard of MyPharmacy! From here, you can manage products, view Sales, generate reports, and update settings for your pharmacy system.</p>");
        
        out.println("<h3>Quick Links</h3>");
        out.println("<ul>");
        out.println("<li><a href='products.html'>Manage Products</a></li>");
        out.println("<li><a href='sales.html'>View Sales</a></li>");
        out.println("<li><a href='ReportServlet'>View Reports</a></li>");
        
        out.println("</ul>");
        out.println("</div>");

        // Footer
        out.println("<div class='footer'>");
        out.println("<p>© 2024 MyPharmacy - All rights reserved.</p>");
        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }
}
