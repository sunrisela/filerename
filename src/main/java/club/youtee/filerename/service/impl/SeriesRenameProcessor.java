package club.youtee.filerename.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import club.youtee.filerename.domain.EpisodeFile;
import club.youtee.filerename.domain.RenameOptionDTO;
import javafx.scene.control.ListView;
import org.apache.commons.io.FilenameUtils;

import static club.youtee.filerename.constants.CommonConstants.SUB_FILE_TYPE;
import static club.youtee.filerename.constants.CommonConstants.VIDEO_FILE_TYPE;

/**
 * 剧集文件重命名处理实现
 *
 * @author Xinglong.Li
 * @date 2024-10-18
 */
public class SeriesRenameProcessor extends BasePreviewProcessor {

    private final RenameOptionDTO option;

    /**
     * 是否仅预览
     */
    private final boolean isPreview;

    private int currentEp = -1;

    public SeriesRenameProcessor(RenameOptionDTO option, ListView<String> preview, boolean isPreview) {
        super(preview);
        this.option = option;
        this.isPreview = isPreview;
    }

    @Override
    public void run(String path) {
        info("开始执行...");
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            error("文件目录不存在");
            return;
        }

        Map<Integer, EpisodeFile> sourceMap = new TreeMap<>();
        Map<Integer, EpisodeFile> targetMap = new HashMap<>();
        Map<Integer, EpisodeFile> videoFileMap;
        Map<Integer, EpisodeFile> subFileMap;
        if (VIDEO_FILE_TYPE.equals(option.getRefer())) {
            videoFileMap = sourceMap;
            subFileMap = targetMap;
        } else {
            subFileMap = sourceMap;
            videoFileMap = targetMap;
        }

        // TODO 存在相同集数的，多个视频文件、或多个字幕文件处理
        for (File file : dir.listFiles()) {
            if (!file.isFile()) {
                continue;
            }
            String extname = FilenameUtils.getExtension(file.getName());
            if (option.getVideoExtnames().contains(extname)) {
                EpisodeFile episodeFile = new EpisodeFile(file, VIDEO_FILE_TYPE);
                if (episodeFile.getEpisode(option.getVideoEpReg()) != null) {
                    videoFileMap.put(episodeFile.getEpisode(), episodeFile);
                } else {
                    warn("存在无法匹配集数的视频文件: " + episodeFile.getFile().getName());
                }
            } else if (option.getSubExtnames().contains(extname)) {
                EpisodeFile episodeFile = new EpisodeFile(file, SUB_FILE_TYPE);
                if (episodeFile.getEpisode(option.getSubEpReg()) != null) {
                    subFileMap.put(episodeFile.getEpisode(), episodeFile);
                } else {
                    warn("存在无法匹配集数的字幕文件: " + episodeFile.getFile().getName());
                }
            } else {
                info("忽略的文件: " + file.getName());
            }
        }

        if (videoFileMap.isEmpty() && subFileMap.isEmpty()) {
            warn("没有找到视频或字幕文件");
            return;
        } else if (videoFileMap.isEmpty()) {
            warn("没有找到视频文件");
            if (!option.isAllowMissing()) {
                return;
            }
        } else if (subFileMap.isEmpty()) {
            warn("没有找到字幕文件");
            if (!option.isAllowMissing()) {
                return;
            }
        } else {
            Set<Integer> mergedEpSet = new HashSet<>(videoFileMap.keySet());
            mergedEpSet.addAll(subFileMap.keySet());
            if (mergedEpSet.size() > subFileMap.size()) {
                warn("部分视频未找到匹配的字幕文件");
                if (!option.isAllowMissing()) {
                    return;
                }
            }
            if (mergedEpSet.size() > videoFileMap.size()) {
                warn("部分字幕未找到匹配的视频文件");
                if (!option.isAllowMissing()) {
                    return;
                }
            }
        }

        // 执行重命名
        if (option.getResortEp() != null) {
            currentEp += option.getResortEp();
        }
        int count = 0;
        for (Map.Entry<Integer, EpisodeFile> entry : sourceMap.entrySet()) {
            EpisodeFile source = entry.getValue();
            EpisodeFile target = targetMap.get(entry.getKey());
            if (option.getResortEp() == null) {
                currentEp = source.getEpisode();
            } else {
                currentEp++;
            }
            String newName = this.getNewName(source);
            if (!this.doRename(source, newName)) {
                return;
            }
            count++;
            if (target != null) {
                newName = this.getNewName(target, newName);
                if (!this.doRename(target, newName)) {
                    return;
                }
                count++;
            }
        }
        if (isPreview) {
            info("（预览）执行完成，共处理 " + count + " 个文件。");
        } else {
            info("执行完成，共处理 " + count + " 个文件。");
        }
    }

    private boolean doRename(EpisodeFile target, String newName) {
        boolean success = isPreview || target.rename(newName);
        if (success) {
            info("重命名: " + target.getFile().getName() + " => " + newName + '.' + target.getExtname());
        } else {
            error("重命名失败: " + target.getFile().getName());
        }
        return success;
    }

    private String getNewName(EpisodeFile target, String sourceName) {
        if (option.getSubSuffix() != null) {
            if (target.isSubtitle()) {
                return sourceName + option.getSubSuffix();
            } else {
                int index = sourceName.lastIndexOf(option.getSubSuffix());
                if (index > -1) {
                    return sourceName.substring(0, index);
                }
            }
        }
        return sourceName;
    }

    private String getNewName(EpisodeFile source) {
        StringBuilder sb = new StringBuilder();
        // prefix
        if (option.getPrefix() != null) {
            if (option.isReserveOriginalFilename()) {
                if (!source.getBasename().startsWith(option.getPrefix())) {
                    sb.append(option.getPrefix());
                }
            } else {
                sb.append(option.getPrefix());
            }
        }
        if (option.isReserveOriginalFilename()) {
            sb.append(source.getBeforeEpisodeFragment());
        }

        // season
        if (option.getSeason() != null) {
            sb.append("S").append(String.format("%02d", option.getSeason())).append("E");
        }
        // episode
        // TODO 高位补0位数控制
        sb.append(String.format("%02d", currentEp));

        if (option.isReserveOriginalFilename()) {
            sb.append(source.getAfterEpisodeFragment());
        }
        // suffix
        String suffix = null;
        if (option.getSubSuffix() != null && source.isSubtitle()) {
            suffix = option.getSubSuffix();
        } else if (option.getSuffix() != null) {
            suffix = option.getSuffix();
        }
        if (suffix != null) {
            if (option.isReserveOriginalFilename()) {
                if (!source.getBasename().endsWith(suffix)) {
                    sb.append(suffix);
                }
            } else {
                sb.append(suffix);
            }
        }

        return sb.toString();
    }

}
