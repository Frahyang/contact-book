import java.util.Scanner;
import java.util.LinkedList;
import java.util.HashMap;

public class ContactBook {
    public static void menu() { // Function for the switch case
        HashMap<String, LinkedList<String>> contactBook = new HashMap<>(); // Used for the contact book
        boolean programActive = true; // Checks program status
        Scanner userCommand = new Scanner(System.in);
        Scanner nameDisplay = new Scanner(System.in);
        Scanner contactDetails = new Scanner(System.in);
        Scanner removeContact = new Scanner(System.in);
        Scanner searchEmail = new Scanner(System.in);
        Scanner searchName = new Scanner(System.in); // Scanners to receive user input
        String selectCommand; // String to store user's command
        while (programActive == true) { // Keeps program running
            selectCommand = menuGUI(userCommand);
            switch (selectCommand) {
                case "A": // To add a new contact
                    adding(contactBook, nameDisplay, contactDetails);
                    break;
                case "D": // To delete an existing contact
                    deleting(contactBook, removeContact);
                    break;
                case "E": // To email search the contact book
                    emailSearch(contactBook, searchEmail);
                    break;
                case "P": // To show contact book
                    print(contactBook);
                    break;
                case "S": // To display name search the contact book
                    contactSearch(contactBook, searchName);
                    break;
                case "Q": // To close the program
                    System.out.println("Program Closed");
                    programActive = false;
                    break;
                default: // Error handling
                    System.out.println("Unkown entry");
                    break;
            }
        }
    }
    public static String menuGUI(Scanner userCommand) { // Starting menu
        System.out.println("***************\n(A)dd\n(D)elete\n(E)mail Search\n(P)rint List\n(S)earch\n(Q)uit\n***************");
        System.out.print("Please enter a command: ");
        return userCommand.nextLine().toUpperCase();
    }   
    public static void main(String[] args) { // Main function
        menu();
    }
    public static void adding(HashMap<String, LinkedList<String>> contactBook, Scanner nameDisplay, Scanner contactDetails) { // Adding function
        System.out.print("\nAdding a new contact detail\nEnter name display: ");
        String name = nameDisplay.nextLine();
        boolean validContactDetail = false; // Checks validity of user input's formatting
        while (validContactDetail == false) {
            System.out.println("Enter full name, email, and phone number separated by a comma: ");
            String details = contactDetails.nextLine();
            String[] detailsSplit = details.split(",", 3); //Splits user input with every comma
            LinkedList<String> userDetails = new LinkedList<>();
            if (detailsSplit.length == 3) { // Ensures that user input has exactly 3 strings
                for (String userDetail : detailsSplit) {
                    userDetails.add(userDetail);
                }
                String userEmail = userDetails.get(1);
                String userPhone = userDetails.get(2);
                if (!userEmail.contains("@")) { // Ensures a proper email input
                    userDetails.clear();
                    System.out.println("\n! Please input a valid email !\n");
                } else if (!userPhone.matches("\\d+")) { // Ensures a proper number input
                    userDetails.clear();
                    System.out.println("\n! Please input a valid phone number !\n");
                }
                if (!userDetails.isEmpty()) {// Checks that the userDetails was not removed after wrong formatting
                    contactBook.put(name, userDetails);
                    System.out.println("\n! Contact details of " + name + " successfully added to contact book !\n");
                    validContactDetail = true; // Loop stops
                }
            } else {
                System.out.println("! Invalid input !"); // Error handling
            }
        }
    }
    public static void deleting(HashMap<String, LinkedList<String>> contactBook, Scanner removeContact) { // Deleting function
        boolean displayNameValidity = true; // 
        while (displayNameValidity == true && !contactBook.isEmpty()) { // Checks if the name exists and the contact book isn't empty
            System.out.println("\nEnter a display name to remove the contact: ");
            String contactToBeRemoved = removeContact.nextLine();
            if (contactBook.containsKey(contactToBeRemoved)){ // This finds the exact name the user inputs and is case-sensitive
                contactBook.remove(contactToBeRemoved);
                displayNameValidity = false;
                System.out.println("\n! Contact details of " + contactToBeRemoved + " successfully removed from contact book !\n");
            } else {
                System.out.println("\n! Entry not found !\n");
            }
        }
        if (contactBook.isEmpty()) {
            System.out.println("\n! The contact book is empty !\n");
        }
    }
    public static void emailSearch(HashMap<String, LinkedList<String>> contactBook, Scanner searchEmail) { // Email search function
        System.out.print("\nSearch email: ");
        String emailInput = searchEmail.nextLine().toLowerCase();
        LinkedList<String> filteredNameListForEmail = new LinkedList<>();
        for (String currentName : contactBook.keySet()) { // Iterating every key from contactBook
            String currentEmail = contactBook.get(currentName).get(1); // To get each email
            if (currentEmail.toLowerCase().contains(emailInput)) { // Filters the emails with the user input
                filteredNameListForEmail.add(currentName);
            }
        }
        System.out.println("\nFiltered Contact Book:");
        System.out.println("================================================================================================");
        System.out.println("Display Name\t| Email");
        for (String filteredName : filteredNameListForEmail) { // Prints the filtered emails
            String filteredEmail = contactBook.get(filteredName).get(1);
            System.out.println("================================================================================================");
            System.out.println(filteredName + "\t| " + filteredEmail);
        }
        System.out.println("");
    }
    public static void print(HashMap<String, LinkedList<String>> contactBook) { // To show the contact book function
        System.out.println("\nContact Book:");
        System.out.println("================================================================================================");
        System.out.println("Display Name\t| Full name | Email | Phone Number");
        for (HashMap.Entry<String, LinkedList<String>> entry : contactBook.entrySet()) { // Entry set to return the contactBook set of keys and values and iterate
            String contactName = entry.getKey();
            LinkedList<String> contactBookDetails = entry.getValue();
            System.out.println("================================================================================================");
            System.out.println(contactName + "\t| " + contactBookDetails.get(0) + " | " + contactBookDetails.get(1) + " | " + contactBookDetails.get(2));
        }
        System.out.println("");
    }
    public static void contactSearch(HashMap<String, LinkedList<String>> contactBook, Scanner searchName) { // Displau name search function
        System.out.print("\nSearch display name: ");
        String nameInput = searchName.nextLine().toLowerCase();
        LinkedList<String> filteredNameList = new LinkedList<>();
        for (String currentName : contactBook.keySet()) {
            if (currentName.toLowerCase().contains(nameInput)) { // Filters the display names with the user input
                filteredNameList.add(currentName);
            }
        }
        System.out.println("\nFiltered Contact Book:");
        System.out.println("================================================================================================");
        System.out.println("Display Name\t| Full name | Email | Phone Number");
        for (String filteredName : filteredNameList) { // Prints the filtered names
            LinkedList<String> filteredDetails = contactBook.get(filteredName);
            System.out.println("================================================================================================");
            System.out.println(filteredName + "\t| " + filteredDetails.get(0) + " | " + filteredDetails.get(1) + " | " + filteredDetails.get(2));
        }
        System.out.println("");
    }
}