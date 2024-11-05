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

    private List<String> epRegs;

    static {
        INSTANCE.load();
    }

    public static PreferenceContext getInstance() {
        return INSTANCE;
    }

    public static List<Pattern> getEpPatterns() {
        return INSTANCE.epPatterns;
    }

    public static List<String> getEpRegs() {
        return INSTANCE.epRegs;
    }

    public static void setEpPatterns(List<String> epRegs) {
        INSTANCE.epRegs = epRegs;
        INSTANCE.epPatterns = epRegs.stream().map(Pattern::compile).toList();
    }

    public static void reset() {
        INSTANCE.epPatterns = Arrays.asList(CommonConstants.DEFAULT_EP_PATTERNS);
        INSTANCE.epRegs = INSTANCE.epPatterns.stream().map(Pattern::pattern).toList();
    }

    /**
     * 持久化配置
     */
    public static void commit() {
        PREFERENCE_FILE.write(INSTANCE.convert2Entity());
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
        if (preferenceDO == null || preferenceDO.getEpRegs() == null) {
            return;
        }
        setEpPatterns(preferenceDO.getEpRegs());
    }

    private PreferenceDO convert2Entity() {
        PreferenceDO preferenceDO = new PreferenceDO();
        preferenceDO.setEpRegs(epRegs);
        return preferenceDO;
    }
}
