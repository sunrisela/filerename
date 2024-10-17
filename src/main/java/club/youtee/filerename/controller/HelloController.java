package club.youtee.filerename.controller;

import java.io.File;

import club.youtee.filerename.domain.RenameOptionDTO;
import club.youtee.filerename.service.FileRenameService;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloController {

    @FXML
    private TextField filePathField;

    @FXML
    private ToggleGroup refer;

    @FXML
    private ListView<String> previewView;

    @Autowired
    private FileRenameService fileRenameService;

    @FXML
    protected void onSelectFileButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择目录或文件");
        File selectedFile = fileChooser.showOpenDialog(filePathField.getScene().getWindow());
        if (selectedFile != null) {
            filePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    protected void onPreviewButtonClick() {
        RenameOptionDTO optionDTO = RenameOptionDTO.builder()
            .refer(refer.getSelectedToggle().getUserData().toString())
            .build();
        fileRenameService.preview(filePathField.getText(), optionDTO, previewView);
    }

}