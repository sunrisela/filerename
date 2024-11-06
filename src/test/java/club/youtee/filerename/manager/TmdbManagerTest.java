package club.youtee.filerename.manager;

import info.movito.themoviedbapi.model.core.TvSeriesResultsPage;
import org.junit.jupiter.api.Test;

/**
 * @author Xinglong.Li
 * @date 2024-11-06
 */
public class TmdbManagerTest {

    private final TmdbManager tmdbManager = new TmdbManager();

    @Test
    public void searchTv() {
        TvSeriesResultsPage resultsPage = tmdbManager.searchTv("柳舟记", 2024);
        System.out.println(resultsPage);
    }
}
