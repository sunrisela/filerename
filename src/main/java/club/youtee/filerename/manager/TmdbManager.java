package club.youtee.filerename.manager;

import club.youtee.filerename.config.AppConfig;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.core.TvSeriesResultsPage;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;

/**
 * @author Xinglong.Li
 * @date 2024-11-06
 */
public class TmdbManager {

    private final TmdbApi tmdbApi;

    public TmdbManager() {
        tmdbApi = new TmdbApi(AppConfig.getAppProperties().getTmdb().getApiReadAccessToken());
    }

    public TvSeriesResultsPage searchTv(String keyword, Integer year) {
        try {
            return tmdbApi.getSearch().searchTv(keyword, year, false, "zh-CN", 1, null);
        } catch (TmdbException e) {
            throw new RuntimeException(e);
        }
    }

    public TvSeriesDb getTvSeriesInfo(int seriesId) {
        try {
            return tmdbApi.getTvSeries().getDetails(seriesId, "zh-CN");
        } catch (TmdbException e) {
            throw new RuntimeException(e);
        }
    }

}
