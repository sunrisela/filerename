package club.youtee.filerename.domain;

import java.io.File;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import org.apache.commons.io.FilenameUtils;

import static club.youtee.filerename.constants.CommonConstants.DEFAULT_EP_PATTERNS;
import static club.youtee.filerename.constants.CommonConstants.SUB_FILE_TYPE;
import static club.youtee.filerename.constants.CommonConstants.VIDEO_FILE_TYPE;

/**
 * @author Xinglong.Li
 * @date 2024-10-18
 */
@Getter
public class EpisodeFile {

    private final File file;

    private final String fileType;

    private final String basename;

    private final String extname;

    private Integer episode;

    private String beforeEpisodeFragment;

    private String afterEpisodeFragment;

    public EpisodeFile(String filePath) {
        this(new File(filePath));
    }

    public EpisodeFile(String filePath, String fileType) {
        this(new File(filePath), fileType);
    }

    public EpisodeFile(File file) {
        this(file, VIDEO_FILE_TYPE);
    }

    public EpisodeFile(File file, String fileType) {
        this.file = file;
        this.fileType = fileType;
        this.basename = FilenameUtils.getBaseName(file.getName());
        this.extname = FilenameUtils.getExtension(file.getName());
    }

    public Integer getEpisode() {
        return getEpisode(null);
    }

    public Integer getEpisode(String customReg) {
        if (episode == null) {
            this.parseEpisode(customReg);
        }
        return episode;
    }

    private boolean parseEpisode(String customReg) {
        Matcher m = null;
        for (Pattern p : DEFAULT_EP_PATTERNS) {
            m = p.matcher(this.basename);
            if (m.matches()) {
                break;
            }
        }
        if (!m.matches() && customReg != null) {
            m = Pattern.compile(customReg).matcher(this.basename);
        }
        if (m.matches()) {
            episode = Integer.parseInt(m.group(2));
            beforeEpisodeFragment = m.group(1);
            afterEpisodeFragment = m.group(3);
        }
        return m.matches();
    }

    public boolean rename(String newBasename) {
        Path newPath = Path.of(file.getParent(), newBasename + '.' + extname);
        return file.renameTo(newPath.toFile());
    }

    public boolean isVideo() {
        return VIDEO_FILE_TYPE.equals(fileType);
    }

    public boolean isSubtitle() {
        return SUB_FILE_TYPE.equals(fileType);
    }

}
