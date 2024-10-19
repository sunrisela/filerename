package club.youtee.filerename.service.impl;

import club.youtee.filerename.domain.RenameOptionDTO;
import club.youtee.filerename.service.FileRenameService;
import javafx.scene.control.ListView;
import org.springframework.stereotype.Service;

/**
 * @author Xinglong.Li
 * @date 2024-10-17
 */
@Service
public class FileRenameServiceImpl implements FileRenameService {

    @Override
    public void preview(String path, RenameOptionDTO optionDTO, ListView<String> preview) {
        SeriesRenameProcessor processor = new SeriesRenameProcessor(optionDTO, preview, true);
        processor.run(path);
    }

    @Override
    public void execute(String path, RenameOptionDTO optionDTO, ListView<String> preview) {
        SeriesRenameProcessor processor = new SeriesRenameProcessor(optionDTO, preview, false);
        processor.run(path);
    }

}
