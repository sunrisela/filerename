package club.youtee.filerename.domain;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static club.youtee.filerename.constants.CommonConstants.RESERVE_END_SEG;
import static club.youtee.filerename.constants.CommonConstants.RESERVE_FRONT_SEG;
import static club.youtee.filerename.constants.CommonConstants.RESERVE_NONE;
import static club.youtee.filerename.constants.CommonConstants.RESERVE_ORIGINAL;

/**
 * @author Xinglong.Li
 * @date 2024-10-17
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RenameOptionDTO {

    /**
     * 主体，video 视频；sub 字幕
     */
    private String refer;

    /**
     * 补充的文件名前缀
     */
    private String prefix;

    /**
     * 补充的文件名后缀
     */
    private String suffix;

    /**
     * 补充的字幕文件名后缀
     */
    private String subSuffix;

    /**
     * 是否保留原始文件名
     */
    private int reserveType;

    /**
     * 允许视频与字幕匹配时，出现不存在情况
     */
    private boolean allowMissing;

    /**
     * 允许的视频文件格式
     */
    private Set<String> videoExtnames;

    /**
     * 允许的字幕文件格式
     */
    private Set<String> subExtnames;

    /**
     * 视频文件名集数匹配正则
     */
    private String videoEpReg;

    /**
     * 字幕文件名集数匹配正则
     */
    private String subEpReg;

    /**
     * 第几季，若为空表示集数命名上不包含季
     */
    private Integer season;

    /**
     * 重新排序开始集数，若为空表示不重新排序
     */
    private Integer resortEp;

    public boolean isReserveOriginalName() {
        return reserveType == RESERVE_ORIGINAL;
    }

    public boolean isReserveFrontSegment() {
        return reserveType == RESERVE_FRONT_SEG;
    }

    public boolean isReserveEndSegment() {
        return reserveType == RESERVE_END_SEG;
    }

    public boolean isReserveNone() {
        return reserveType == RESERVE_NONE;
    }

}
