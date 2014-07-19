package ajax;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.stream.JsonGenerator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sakura on 2014/07/19.
 */
@WebServlet("/ajax")
public class AjaxServlet extends HttpServlet {

    @Resource(lookup="jdbc/index_sample")
    private DataSource dataSource;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf8");
        String name = request.getParameter("name");

        StringWriter writer = new StringWriter();
        JsonGenerator generator = Json.createGenerator(writer).writeStartArray();

        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement("select with_index from index_sample1 where with_index like ? limit 10")
        ) {
            pstmt.setString(1, name + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String withIndex = rs.getString(1);
                    generator.write(withIndex);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        generator.writeEnd().flush();

        response.getWriter().write(writer.toString());
    }
}
