package ajax;

import org.junit.After;
import org.junit.Assert;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


/**
 * Created by sakura on 2014/07/20.
 */
public class DAOTest {
    JdbcDataSource dataSource;
    DAO dao;
    Connection c;

    @Before
    public void setUp() throws Exception {
        dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:testdb");
        Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement("create table index_sample1(id int primary key, with_index text)");
        stmt.execute();

        stmt = conn.prepareStatement("insert into index_sample1(id, with_index) values (?, ?)");
        String[] candidates = {"ああああ", "あいうえ", "いあいう", "いいいあ"};
        for (int i = 0; i < candidates.length; i++) {
            stmt.setInt(1, i);
            stmt.setString(2, candidates[i]);
            stmt.addBatch();
        }
        stmt.executeBatch();
        conn.commit();
        c = conn;

        conn = dataSource.getConnection();
        stmt = conn.prepareStatement("select count(*) from index_sample1");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int count = rs.getInt(1);
            System.out.println(count);
        }

        dao = new DAO();
        dao.dataSource = dataSource;
    }

    @After
    public void tearDown() throws Exception {
        Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement("drop table index_sample1");
        stmt.executeUpdate();
        conn.close();
        c.close();
    }

    @Test
    public void testSelect() throws Exception {
        List<String> candidates = dao.searchCandidate("あ");
        Assert.assertEquals(Arrays.asList("ああああ", "あいうえ"), candidates);
    }

    @Test
    public void testSelectEmpty() {
        List<String> candidates = dao.searchCandidate("か");
        Assert.assertEquals(Arrays.asList(), candidates);
    }

    @Test
    public void testConnectionFail() throws Exception {
        DataSource ds = mock(DataSource.class);
        when(ds.getConnection()).thenThrow(new SQLException());
        dao.dataSource = ds;

        List<String> result = dao.searchCandidate(null);
        Assert.assertEquals(Arrays.asList(), result);
    }

    @Test
    public void testQueryFailure() throws Exception {
        DataSource ds = mock(DataSource.class);
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        when(ds.getConnection()).thenReturn(conn);
        when(conn.prepareStatement(any())).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(new SQLException());

        dao.dataSource = ds;

        List<String> result = dao.searchCandidate(null);
        Assert.assertEquals(Arrays.asList(), result);
    }
}
