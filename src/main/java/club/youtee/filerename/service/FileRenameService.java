package club.youtee.filerename.service;

import club.youtee.filerename.domain.RenameOptionDTO;
import javafx.scene.control.ListView;

/**
 * @author Xinglong.Li
 * @date 2024-10-17
 */
public interface FileRenameService {

    void preview(String path, RenameOptionDTO optionDTO, ListView<String> preview);

    void execute(String path, RenameOptionDTO optionDTO, ListView<String> preview);
}
