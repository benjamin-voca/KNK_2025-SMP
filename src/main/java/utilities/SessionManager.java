package utilities;

import models.Student;
import models.User;

public class SessionManager {
    private static User currentUser;
    private static Student currentStudent;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentStudent(Student student) {
        currentStudent = student;
    }

    public static Student getCurrentStudent() {
        return currentStudent;
    }

    public static void clearSession() {
        currentUser = null;
        currentStudent = null;
    }
}