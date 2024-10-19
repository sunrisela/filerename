package club.youtee.filerename.controller;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import club.youtee.filerename.domain.RenameOptionDTO;
import club.youtee.filerename.service.FileRenameService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.DirectoryChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainController {

    @FXML
    private TextField filePathField;

    @FXML
    private ToggleGroup refer;

    @FXML
    private TextField prefix;

    @FXML
    private TextField suffix;

    @FXML
    private TextField subSuffix;

    @FXML
    private ToggleGroup reserveOriginalFilename;

    @FXML
    private ToggleGroup allowMissing;

    @FXML
    private TextField videoEpReg;

    @FXML
    private TextField subEpReg;

    @FXML
    private ListView<String> previewView;

    @FXML
    private FlowPane videoExtnamesCont;

    @FXML
    private FlowPane subExtnamesCont;

    @FXML
    private TextField season;

    @FXML
    private TextField resortEp;

    private List<CheckBox> videoExtnames;

    private List<CheckBox> subExtnames;

    @Autowired
    private FileRenameService fileRenameService;

    @FXML
    public void initialize() {
        videoExtnames = videoExtnamesCont.lookupAll("CheckBox").stream()
            .map( o -> (CheckBox)o)
            .collect(Collectors.toList());
        subExtnames = subExtnamesCont.lookupAll("CheckBox").stream()
            .map( o -> (CheckBox)o)
            .collect(Collectors.toList());
    }

    @FXML
    protected void onSelectFileButtonClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择目录");
        File selectedFile = directoryChooser.showDialog(filePathField.getScene().getWindow());

        //FileChooser fileChooser = new FileChooser();
        //fileChooser.setTitle("选择文件");
        //File selectedFile = fileChooser.showOpenDialog(filePathField.getScene().getWindow());

        if (selectedFile != null) {
            filePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    protected void onExecuteButtonClick() {
        fileRenameService.execute(filePathField.getText(), this.buildRenameOption(), previewView);
    }

    @FXML
    protected void onPreviewButtonClick() {
        fileRenameService.preview(filePathField.getText(), this.buildRenameOption(), previewView);
    }

    @FXML
    protected void onCleanupButtonClick() {
        previewView.getItems().clear();
    }

    private RenameOptionDTO buildRenameOption() {
        Set<String> selectedVideoExtnames = videoExtnames.stream()
            .filter(CheckBox::isSelected)
            .map(Labeled::getText)
            .collect(Collectors.toSet());
        Set<String> selectedSubExtnames = subExtnames.stream()
            .filter(CheckBox::isSelected)
            .map(Labeled::getText)
            .collect(Collectors.toSet());
        return RenameOptionDTO.builder()
            .refer(refer.getSelectedToggle().getUserData().toString())
            .prefix(prefix.getText().isBlank() ? null : prefix.getText().trim())
            .suffix(suffix.getText().isBlank() ? null : suffix.getText().trim())
            .subSuffix(subSuffix.getText().isBlank() ? null : subSuffix.getText().trim())
            .reserveOriginalFilename(Boolean.parseBoolean(reserveOriginalFilename.getSelectedToggle().getUserData().toString()))
            .allowMissing(Boolean.parseBoolean(allowMissing.getSelectedToggle().getUserData().toString()))
            .videoEpReg(videoEpReg.getText().isBlank() ? null : videoEpReg.getText().trim())
            .subEpReg(subEpReg.getText().isBlank() ? null : subEpReg.getText().trim())
            .videoExtnames(selectedVideoExtnames)
            .subExtnames(selectedSubExtnames)
            .season(season.getText().isBlank() ? null : Integer.parseInt(season.getText()))
            .resortEp(resortEp.getText().isBlank() ? null : Integer.parseInt(resortEp.getText()))
            .build();
    }
}