package club.youtee.filerename.service.impl;

import java.time.LocalDateTime;

import javafx.scene.control.ListView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static club.youtee.filerename.constants.CommonConstants.NORM_TIME_FORMATTER;

/**
 * @author Xinglong.Li
 * @date 2024-10-18
 */
@Getter
@RequiredArgsConstructor
public abstract class BasePreviewProcessor {

    private static final String LOG_PATTERN = "%s %s > %s";

    private static final String INFO = " INFO";

    private static final String WARN = " WARN";

    private static final String ERROR = "ERROR";

    private final ListView<String> preview;

    public abstract void run(String path);

    public void info(String msg) {
        log(INFO, msg);
    }

    public void warn(String msg) {
        log(WARN, msg);
    }

    public void error(String msg) {
        log(ERROR, msg);
    }

    private void log(String level, String msg) {
        preview.getItems().add(String.format(LOG_PATTERN, LocalDateTime.now().format(NORM_TIME_FORMATTER), level, msg));
        preview.scrollTo(preview.getItems().size() - 1);
    }

}
