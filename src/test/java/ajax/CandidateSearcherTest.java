package ajax;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by sakura on 2014/07/20.
 */
public class CandidateSearcherTest {
    @Test
    public void testCandidate() {
        DAO dao = mock(DAO.class);
        CandidateSearcher searcher = new CandidateSearcher();
        searcher.dao = dao;

        when(dao.searchCandidate("hint")).thenReturn(Arrays.asList("xxx", "yyy", "zzz"));

        String result = searcher.candidate("hint");

        Assert.assertEquals("[\"xxx\",\"yyy\",\"zzz\"]", result);
    }
}
