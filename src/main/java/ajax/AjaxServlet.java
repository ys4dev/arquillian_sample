package ajax;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sakura on 2014/07/19.
 */
@WebServlet("/ajax")
public class AjaxServlet extends HttpServlet {

    @Inject CandidateSearcher searcher;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf8");
        String name = request.getParameter("name");

        String candidate = searcher.candidate(name);

        response.getWriter().write(candidate);
    }
}
