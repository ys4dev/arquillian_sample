package ajax;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by sakura on 2014/07/20.
 */
@RequestScoped
public class CandidateSearcher {

    @Inject DAO dao;

    public String candidate(String hint) {
        List<String> candidates = dao.searchCandidate(hint);

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(candidates);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
