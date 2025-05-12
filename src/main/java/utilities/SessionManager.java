package utilities;

import models.Professors;
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

    private static Professors currentProfessor;

    public static void setCurrentProfessor(Professors professor) {
        currentProfessor = professor;
    }

    public static Professors getCurrentProfessor() {
        return currentProfessor;
    }


    public static void clearSession() {
        currentUser = null;
        currentStudent = null;
    }
}