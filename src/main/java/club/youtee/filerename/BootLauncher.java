package club.youtee.filerename;

/**
 * Main app extends Application and has a main method. The sun.launcher.LauncherHelper which is in the java.base module
 * will check for the javafx.graphics module to be present as a named module. If that module is not present, the launch
 * is aborted.
 *
 * @author Xinglong.Li
 * @date 2024-10-19
 */
public class BootLauncher {

    public static void main(String[] args) {
        MainApplication.main(args);
    }

}
