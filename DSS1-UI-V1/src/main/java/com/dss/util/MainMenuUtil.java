package com.dss.util;

import com.dss.entities.User;
import com.dss.exception.UserNotFoundException;
import org.springframework.util.ObjectUtils;

import java.util.Optional;
import java.util.Scanner;

public final class MainMenuUtil {

    private MainMenuUtil() {

    }

    public static String WELCOME_MESSAGE = "Welcome to Digital Streaming System! \uD83D\uDCFA";
    private final static String MAIN_MENU = "1. Add Movie \n" +
            "2. View all movie \n" +
            "3. Update movie details \n" +
            "4. Delete movie \n" +
            "5. Delete a booking \n" +
            "6. Search movie" +
            "7. Add movie review" +
            "8. Add actor" +
            "9. Get all actors" +
            "10. Update actor's details" +
            "11. Delete actor" +
            "12. Add new administrator" +
            "13. Logout";
    private final static String ENTER_LOGIN_DETAILS_MSG = "Please enter login details: ";
    private static String ENTER_SELECTED_PROCESS_MSG = "Enter selected process: ";
    private static String ENTER_SELECTED_STORAGE_TYPE_MSG = "Enter selected storage type: ";


    public static int displayMainMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ENTER_LOGIN_DETAILS_MSG);
        System.out.print(ENTER_SELECTED_PROCESS_MSG);
        int selectedChoice = Integer.parseInt(scanner.next());
        return selectedChoice;
    }

    public static User doLogin() {
        Scanner scanner = new Scanner(System.in);
        User user = new User();
        System.out.println(ENTER_LOGIN_DETAILS_MSG);
        System.out.print("User Id: ");
        user.setUserId(scanner.next());
        System.out.print("Password: ");
        user.setPassword(scanner.next());
        return user;
    }
}
