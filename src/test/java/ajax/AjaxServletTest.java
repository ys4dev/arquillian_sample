package ajax;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Created by sakura on 2014/07/21.
 */
public class AjaxServletTest {

    HttpServletRequest request;
    HttpServletResponse response;
    AjaxServlet servlet = new AjaxServlet();
    CandidateSearcher searcher;
    PrintWriter writer;

    @Before
    public void setUp() throws Exception {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        searcher = mock(CandidateSearcher.class);
        servlet.searcher = searcher;
        writer = mock(PrintWriter.class);

        when(response.getWriter()).thenReturn(writer);
        when(searcher.candidate(anyString())).thenReturn("result");
    }

    @Test
    public void testGet() throws Exception {
        servlet.doGet(request, response);

        verify(request).setCharacterEncoding("utf-8");
        verify(response).setContentType("application/json; charset=utf8");
        verify(writer).write("result");
    }
}
