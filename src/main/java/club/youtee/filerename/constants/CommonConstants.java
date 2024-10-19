package club.youtee.filerename.constants;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * @author Xinglong.Li
 * @date 2024-10-18
 */
public interface CommonConstants {

    /**
     * 默认的集数匹配正则
     */
    Pattern[] DEFAULT_EP_PATTERNS = new Pattern[] {
        Pattern.compile("(?i)(.*\\[)(\\d+)(?:v\\d+)?(].*)"),
        Pattern.compile("(?i)(.*\\s+)(\\d+)(?:v\\d+)?(\\s+.*|$)"),
        Pattern.compile("(?i)(.*)S\\d+E(\\d+)(?:v\\d+)?(.*)"),
        Pattern.compile("(?i)(.*)[E#](\\d+)(?:v\\d+)?((?:\\.|\\s+).*|$)"),
        Pattern.compile("(.*)第\\s*(\\d+)\\s*(?=[話话集])(.*)"),
        Pattern.compile("(?i)(.*OVA_?)(\\d+)(.*)"),
    };

    String VIDEO_FILE_TYPE = "video";

    String SUB_FILE_TYPE = "sub";

    String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss
     */
    DateTimeFormatter NORM_DATETIME_FORMATTER = DateTimeFormatter.ofPattern(NORM_DATETIME_PATTERN);

    String NORM_TIME_PATTERN = "HH:mm:ss";
    /**
     * 标准时间格式，精确到秒：HH:mm:ss
     */
    DateTimeFormatter NORM_TIME_FORMATTER = DateTimeFormatter.ofPattern(NORM_TIME_PATTERN);

    /**
     * 保留原文件名
     */
    int RESERVE_ORIGINAL = 0;

    /**
     * 保留原文件名前段
     */
    int RESERVE_FRONT_SEG = 1;

    /**
     * 保留原文件名末段
     */
    int RESERVE_END_SEG = 2;

    /**
     * 不保留原文件名
     */
    int RESERVE_NONE = 3;
}
