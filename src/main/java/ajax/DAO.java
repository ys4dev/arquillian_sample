package ajax;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sakura on 2014/07/20.
 */
@RequestScoped
public class DAO {
    @Resource(lookup="jdbc/index_sample")
    private DataSource dataSource;

    public List<String> searchCandidate(String hint) {
        List<String> result = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement("select with_index from index_sample1 where with_index like ? limit 10")
        ) {
            pstmt.setString(1, hint + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String withIndex = rs.getString(1);
                    result.add(withIndex);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
