package club.youtee.filerename;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class MainApplication extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        List<String> rawParams = getParameters().getRaw() ;
        String[] args = rawParams.toArray(new String[0]) ;
        context = SpringApplication.run(getClass(), args);
        // further configuration on context as needed
    }

    @Override
    public void stop() {
        if (context != null) {
            context.close();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/view/main-view.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);
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