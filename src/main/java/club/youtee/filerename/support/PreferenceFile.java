package club.youtee.filerename.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import club.youtee.filerename.domain.entity.PreferenceDO;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

/**
 * @author Xinglong.Li
 * @date 2024-11-03
 */
public class PreferenceFile {

    private static final Path DEFAULT_CONFIG_PATH = Path.of(System.getProperty("user.home"), ".config", "filerename");

    private static final String DEFAULT_PREFERENCE_FILE_NAME = "preference.yml";

    private final Yaml yaml;

    public PreferenceFile() {
        yaml = this.instanceYaml();
    }

    public void write(PreferenceDO preferenceDO) {
        DEFAULT_CONFIG_PATH.toFile().mkdirs();
        File yamlFile = this.getFile();
        FileWriter writer;
        try {
            writer = new FileWriter(yamlFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        yaml.dump(preferenceDO, writer);
    }

    public boolean exists() {
        return this.getFile().exists();
    }

    public PreferenceDO readObject() {
        File yamlFile = this.getFile();
        if (!yamlFile.exists()) {
            return null;
        }
        FileInputStream is;
        try {
            is = new FileInputStream(yamlFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return yaml.load(is);
    }

    private Yaml instanceYaml() {
        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setTagInspector(tag -> true);

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        options.setIndentWithIndicator(true);
        options.setIndicatorIndent(2);
        Representer representer = new Representer(options);
        representer.addClassTag(PreferenceDO.class, Tag.MAP);

        return new Yaml(new Constructor(PreferenceDO.class, loaderOptions), representer, options);
    }

    private File getFile() {
        return DEFAULT_CONFIG_PATH.resolve(DEFAULT_PREFERENCE_FILE_NAME).toFile();
    }
}
