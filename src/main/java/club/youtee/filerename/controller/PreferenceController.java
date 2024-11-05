package club.youtee.filerename.controller;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import club.youtee.filerename.support.PreferenceContext;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import static club.youtee.filerename.constants.CommonConstants.DEFAULT_EP_PATTERNS;

/**
 * @author Xinglong.Li
 * @date 2024-10-31
 */
public class PreferenceController {

    @FXML
    private ListView<String> epRegs;

    @FXML
    public void initialize() {
        this.reload();
        epRegs.setOnEditCancel(event -> {
            System.out.println("fire event edit cancel ...");
        });
        epRegs.setOnEditCommit(event -> {
            System.out.println("fire event edit commit, index: " + event.getIndex());
            if (event.getIndex() != -1) {
                //String oldValue = event.getSource().getItems().get(event.getIndex());
                event.getSource().getItems().set(event.getIndex(), event.getNewValue());
            }
        });
    }

    public void reload() {
        List<String> regs = PreferenceContext.getEpPatterns().stream().map(Pattern::pattern)
            .toList();
        epRegs.getItems().clear();
        epRegs.getItems().addAll(regs);
    }

    @FXML
    protected void onAddEpRegButtonClick() {
        epRegs.getItems().add("");
        int selectIndex = epRegs.getItems().size() - 1;
        epRegs.getSelectionModel().select(selectIndex);
        epRegs.edit(selectIndex);
    }

    @FXML
    protected void onDelEpRegButtonClick() {
        if (epRegs.getSelectionModel().getSelectedIndex() > -1) {
            epRegs.getItems().remove(epRegs.getSelectionModel().getSelectedIndex());
        }
    }

    @FXML
    protected void onSaveButtonClick() {
        // TODO only changed to save
        PreferenceContext.setEpPatterns(epRegs.getItems().stream().map(Pattern::compile).toList());
        PreferenceContext.commit();
        epRegs.getScene().getWindow().hide();
    }

    @FXML
    protected void onCancelButtonClick() {
        epRegs.getScene().getWindow().hide();
    }

    @FXML
    protected void onResetButtonClick() {
        List<String> regs = Arrays.stream(DEFAULT_EP_PATTERNS).map(Pattern::pattern)
            .toList();
        epRegs.getItems().clear();
        epRegs.getItems().addAll(regs);
    }
}
