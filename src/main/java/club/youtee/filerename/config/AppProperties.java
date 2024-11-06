package club.youtee.filerename.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Xinglong.Li
 * @date 2024-11-06
 */
@Getter
@Setter
@ToString
public class AppProperties {

    private Tmdb tmdb;

    @Getter
    @Setter
    @ToString
    public static class Tmdb {

        private String apiKey;

        private String apiReadAccessToken;
    }
}
