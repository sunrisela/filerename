package club.youtee.filerename;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/view/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 640);
        //scene.getStylesheets().addAll(BootstrapFX.bootstrapFXStylesheet());

        stage.setTitle("视频与字幕文件名批处理v1.0 Beta");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}