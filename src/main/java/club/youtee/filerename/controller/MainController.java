package club.youtee.filerename.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import club.youtee.filerename.domain.RenameOptionDTO;
import club.youtee.filerename.service.FileRenameService;
import club.youtee.filerename.service.impl.FileRenameServiceImpl;
import club.youtee.filerename.support.PreferenceContext;
import com.sun.javafx.PlatformUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static javafx.scene.input.KeyCode.C;
import static javafx.scene.input.KeyCode.COMMA;

public class MainController {

    @FXML
    private VBox root;

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
    private ToggleGroup reserveType;

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

    //@Autowired
    private FileRenameService fileRenameService;

    private Stage preferenceStage;

    private PreferenceController preferenceController;

    @FXML
    public void initialize() {
        fileRenameService = new FileRenameServiceImpl();

        videoExtnames = videoExtnamesCont.lookupAll("CheckBox").stream()
            .map( o -> (CheckBox)o)
            .collect(Collectors.toList());
        subExtnames = subExtnamesCont.lookupAll("CheckBox").stream()
            .map( o -> (CheckBox)o)
            .collect(Collectors.toList());
        this.addKeyListeners();
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
        //System.out.println(PreferenceContext.getEpPatterns());
        fileRenameService.preview(filePathField.getText(), this.buildRenameOption(), previewView);
    }

    @FXML
    protected void onCleanupButtonClick() {
        previewView.getItems().clear();
    }

    @FXML
    public void onPreferenceMenuItemClick() {
        if (preferenceStage == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/preference.fxml"));
            Parent parent;
            try {
                parent = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            preferenceController = loader.getController();
            Scene scene = new Scene(parent);
            preferenceStage = new Stage();
            preferenceStage.setTitle("设置");
            preferenceStage.setScene(scene);
            preferenceStage.initOwner(root.getScene().getWindow());
            preferenceStage.initModality(Modality.APPLICATION_MODAL);
        } else {
            preferenceController.reload();
        }
        preferenceStage.show();
    }

    private void addKeyListeners() {
        previewView.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == C && this.isMetaDown(event)) {
                // copy selected item to clipboard
                String selectedItem = previewView.getSelectionModel().getSelectedItem();
                //System.out.println("selected item: " + selectedItem);
                if (selectedItem != null) {
                    ClipboardContent content = new ClipboardContent();
                    content.putString(selectedItem);
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    clipboard.setContent(content);
                }
            }
        });
        root.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if (event.getGestureSource() != root) {
                if (db.hasFiles() && db.getFiles().get(0).isDirectory()) {
                    // allow for both copying and moving, whatever user chooses
                    event.acceptTransferModes(TransferMode.COPY);
                }
            }
            event.consume();
        });
        root.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles() && db.getFiles().get(0).isDirectory()) {
                filePathField.setText(db.getFiles().get(0).getAbsolutePath());
                success = true;
            }
            // let the source know whether the string was successfully transferred and used
            event.setDropCompleted(success);
            event.consume();
        });
        root.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == COMMA && this.isMetaDown(event)) {
                this.onPreferenceMenuItemClick();
            }
        });
    }

    private boolean isMetaDown(KeyEvent event) {
        return event.isMetaDown() || (event.isControlDown() && (PlatformUtil.isWindows() || PlatformUtil.isLinux()));
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
            .reserveType(Integer.parseInt(reserveType.getSelectedToggle().getUserData().toString()))
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