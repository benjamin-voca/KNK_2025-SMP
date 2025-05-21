package utilities;

import javafx.stage.Stage;

public class SceneLocator {
    public static final String ASSESSOR_LOGIN = "/views/assessor_login.fxml";
    public static final String ASSESSOR_PAGE = "/views/assessorpage.fxml";
    public static final String CLASSES = "/views/classes.fxml";
    public static final String HOMEPAGE = "/views/homepage.fxml";
    public static final String INDEX = "/views/index.fxml";
    public static final String LOGIN = "/views/login.fxml";
    public static final String LOGIN_PROFESSOR = "/views/loginprofessor.fxml";
    public static final String LOGOUT = "/views/logout.fxml";
    public static final String MODIFY = "/views/modify.fxml";
    public static final String NOTIFICATIONS = "/views/notification_item.fxml";
    public static final String OTHER = "/views/othersProfessor.fxml";
    public static final String PROFESSOR_HOMEPAGE = "/views/professorhomepage.fxml";
    public static final String PROFESSOR_PROFILE = "/views/professorprofile.fxml";
    public static final String PROFILE = "/views/profile.fxml";
    public static final String REGISTER_PAGE = "/views/registerpage.fxml";
    public static final String STUDENT_COURSES = "/views/studentcourses.fxml";
    public static final String TRANSFER = "/views/transfer.fxml";
    public static final String UPLOAD_SUBMISSION = "/views/upload_submission.fxml";

    private static volatile SceneLocator instance; // Volatile for thread-safety
    private Stage primaryStage;

    private SceneLocator() {

    }

    public static SceneLocator getInstance() {
        if (instance == null) {
            synchronized (SceneLocator.class) {
                if (instance == null) {
                    instance = new SceneLocator();
                }
            }
        }
        return instance;
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}