package club.youtee.filerename.support;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import club.youtee.filerename.constants.CommonConstants;
import club.youtee.filerename.domain.entity.PreferenceDO;

/**
 * 配置上下文信息
 * @author Xinglong.Li
 * @date 2024-11-03
 */
public class PreferenceContext {

    private static final PreferenceContext INSTANCE = new PreferenceContext();

    private static final PreferenceFile PREFERENCE_FILE = new PreferenceFile();

    private List<Pattern> epPatterns;

    static {
        getInstance().load();
    }

    public static PreferenceContext getInstance() {
        return INSTANCE;
    }

    public static List<Pattern> getEpPatterns() {
        return getInstance().epPatterns;
    }

    public static void setEpPatterns(List<Pattern> epPatterns) {
        getInstance().epPatterns = epPatterns;
    }

    public static void reset() {
        getInstance().epPatterns = Arrays.asList(CommonConstants.DEFAULT_EP_PATTERNS);
    }

    /**
     * 持久化配置
     */
    public static void commit() {
        PREFERENCE_FILE.write(getInstance().convert2Entity());
    }

    private void load() {
        if (PREFERENCE_FILE.exists()) {
            loadPreferenceFile();
        }
        if (epPatterns == null) {
            reset();
        }
    }

    private void loadPreferenceFile() {
        PreferenceDO preferenceDO = PREFERENCE_FILE.readObject();
        if (preferenceDO == null) {
            return;
        }
        epPatterns = preferenceDO.getEpPatterns().stream().map(Pattern::compile).toList();
    }

    private PreferenceDO convert2Entity() {
        PreferenceDO preferenceDO = new PreferenceDO();
        preferenceDO.setEpPatterns(epPatterns.stream().map(Pattern::pattern).toList());
        return preferenceDO;
    }
}
