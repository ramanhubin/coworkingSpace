package com.CoworkingSpace;



import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    static DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    static Admin adminInterface = new Admin();
    static  Scanner scanner = new Scanner(System.in);
    static UsersBase usersBase = new UsersBase();
    public static void main(String[] args) throws IOException {


        initializeSampleData();
        TextDataLoader DataLoader = new TextDataLoader();
        adminInterface.setWorkspaces(TextDataLoader.loadOnStartup());

        if (adminInterface.getWorkspaces().isEmpty()) {
            initializeSampleData();
        } else {
            adminInterface.getWorkspaces().addAll(adminInterface.getWorkspaces());
        }


        try {
            ApplicationStateManager loadedState = ApplicationStateManager.loadData("save.dat");
            usersBase = loadedState.getUsersBase();
            adminInterface.setWorkspaces(loadedState.getWorkspaces());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка загрузки: " + e.getMessage());
        }
        System.out.println("=== Coworking Space Reservation System ===");

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Exit");
            System.out.print("Select option: ");

            int choiceEntrance = readIntInput();

            switch (choiceEntrance) {
                case 1 -> adminMenu();
                case 2 -> userMenu();
                case 3 -> {
                    System.out.println("Thank you for using our system. Goodbye!");
                    scanner.close();
                    ApplicationStateManager stateManager = new ApplicationStateManager(usersBase, adminInterface.getWorkspaces());
                    stateManager.saveData("save.dat");
                    System.exit(0);

                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void initializeSampleData() {    //Adding some data at start
        adminInterface.addWorkspace(new Workspace(1, "Closed Space", 15.0f));
        adminInterface.addWorkspace(new Workspace(2, "Private Office", 30.0f));
        adminInterface.addWorkspace(new Workspace(3, "Meeting Room", 25.0f));
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add new workspace");
            System.out.println("2. Remove workspace");
            System.out.println("3. View all reservations");
            System.out.println("4. View all workspaces");
            System.out.println("5. Back to main menu");
            System.out.print("Select option: ");

            int choice = readIntInput();

            switch (choice) {
                case 1 -> addWorkspace();
                case 2 -> removeWorkspace();
                case 3 -> adminInterface.viewAllReservations();
                case 4 -> adminInterface.viewAllWorkspaces();
                case 5 -> { return; }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addWorkspace() {
        System.out.print("Enter workspace ID: ");
        int id = readIntInput();

        System.out.print("Enter workspace type: ");
        String type = scanner.nextLine();

        System.out.print("Enter hourly price: ");
        float price = readFloatInput();

        adminInterface.addWorkspace(new Workspace(id, type, price));
        System.out.println("Workspace added successfully!");
    }

    private static void removeWorkspace() {
        System.out.print("Enter workspace ID to remove: ");
        int id = readIntInput();

        if (adminInterface.removeWorkspace(id)) {
            System.out.println("Workspace removed successfully!");
        } else {
            System.out.println("Workspace not found.");
        }
    }

    private static void userMenu() {
        System.out.print("\nEnter your name: ");
        String userName = scanner.nextLine();


        User user = usersBase.getOrCreateUser(userName);
        while (true) {
            System.out.println("\nUser Menu (" + userName + "):");
            System.out.println("1. Browse available spaces");
            System.out.println("2. Make a reservation");
            System.out.println("3. View my reservations");
            System.out.println("4. Cancel a reservation");
            System.out.println("5. Back to main menu");
            System.out.print("Select option: ");

            try {
                int choice = readIntInput();
                checkLogInSwitchInput(choice);
                switch (choice) {
                    case 1 -> adminInterface.viewAllWorkspaces();
                    case 2 -> makeReservation(user);
                    case 3 -> viewUserReservations(user);
                    case 4 -> cancelReservation(user);
                    case 5 -> { return; }

                }
            }
            catch(SwitchCaseInputException e) {
                System.out.println("Invalid data"+e.getMessage());
            }
        }
    }

    private static void makeReservation(User userInterface) {
        adminInterface.viewAllWorkspaces();

        System.out.print("Enter workspace ID: ");
        int workspaceId = readIntInput();

        System.out.print("Enter start time (yyyy-MM-dd HH:mm): ");
        LocalDateTime startTime = readDateTimeInput();

        System.out.print("Enter end time (yyyy-MM-dd HH:mm): ");
        LocalDateTime endTime = readDateTimeInput();

        if (userInterface.makeReservation(startTime, endTime, workspaceId, adminInterface.getWorkspaces())) {
            System.out.println("Reservation successful!");
        } else {
            System.out.println("Failed to make reservation. The workspace may be unavailable.");
        }
    }

    private static void viewUserReservations(User userInterface) {
        var reservations = userInterface.getUserReservations();

        if (reservations.isEmpty()) {
            System.out.println("You have no reservations.");
            return;
        }

        System.out.println("\nYour Reservations:");
        for (Reservation res : reservations) {
            System.out.printf("- Workspace ID: %d, From: %s, To: %s%n",
                    res.getWorkspaceId(),
                    res.getStartTime().format(TIME_FORMAT),
                    res.getEndTime().format(TIME_FORMAT));
        }
    }

    private static void cancelReservation(User userInterface) {
        viewUserReservations(userInterface);

        if (userInterface.getUserReservations().isEmpty()) {
            return;
        }

        System.out.print("Enter workspace ID to cancel: ");
        int workspaceId = readIntInput();

        if (userInterface.cancelReservation(workspaceId, adminInterface.getWorkspaces())) {
            System.out.println("Reservation canceled successfully!");
        } else {
            System.out.println("No reservation found for this workspace.");
        }
    }



    //Handling some exceptions that might occur
    private static int readIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: "+e.getMessage());
            }
        }
    }
    private static float readFloatInput() {
        while (true) {
            try {
                return Float.parseFloat(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: "+e.getMessage());
            }
        }
    }
    private static LocalDateTime readDateTimeInput() {
        while (true) {
            try {
                return LocalDateTime.parse(scanner.nextLine(), TIME_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.print("Invalid format. Please use yyyy-MM-dd HH:mm: "+e.getMessage());
            }
        }
    }
    public static void checkLogInSwitchInput(int input) throws SwitchCaseInputException{
        if(input<1||input>5){
            System.out.println("input must be not greater than 5");
        }
    }


}