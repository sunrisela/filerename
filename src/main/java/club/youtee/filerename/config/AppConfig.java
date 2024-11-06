package club.youtee.filerename.config;

import club.youtee.filerename.domain.entity.PreferenceDO;
import lombok.Getter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

/**
 * @author Xinglong.Li
 * @date 2024-11-06
 */
public class AppConfig {

    @Getter
    private static final AppProperties appProperties;

    static {
        Yaml yaml = instanceYaml(AppProperties.class);
        appProperties = yaml.load(AppConfig.class.getResourceAsStream("/application.yml"));
    }

    private static Yaml instanceYaml(Class<?> clazz) {
        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setTagInspector(tag -> true);

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        options.setIndentWithIndicator(true);
        options.setIndicatorIndent(2);
        Representer representer = new Representer(options);
        representer.addClassTag(clazz, Tag.MAP);
        representer.getPropertyUtils().setSkipMissingProperties(true);

        return new Yaml(new Constructor(clazz, loaderOptions), representer, options);
    }
}
