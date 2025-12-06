import java.util.Scanner;

public class GroceryShopping {
    private static int startIndex            = 0;
    private static int selectIndex           = 0;
    private static int[] countUnit           = new int[18];
    private static int[] selectedItems       = new int[5];
    private static float[] prices            = { 1.21f, 0.84f, 2.02f, 0.59f, 1.07f, 2.50f, 2.13f, 0.20f, 0.33f, 4.29f, 0.65f, 1.18f, 1.99f, 1.01f, 0.45f, 0.60f, 0.10f, 0.02f };
    private static String[] items            = { "Soap", "Toothpaste", "Milk", "Sugar", "Butter", "Chocolate", "Ice Cream", "Flour", "Tea", "Beef", "Coke", "Malt", "Body Cream", "Air Freshers", "Noodles", "Fanta", "Water", "Gum" };
    private static String currencySign       = "$";
    private static String moneyDecimalFormat = "%.2f";
    private static boolean inInfiniteLoop    = true;
    
    
    public static void main(String[] args) {
        Scanner scanner        = new Scanner(System.in);
        String WELCOME_DISPLAY = "Grocery Shopping Console";

        System.out.println(WELCOME_DISPLAY);
        startConsole(scanner);
    }

    private static void startConsole(Scanner scanner) {
        String COMMAND_OPTIONS    = "Enter 1 to add items\nEnter 2 to checkout\nEnter 3 to search for item\nEnter 'Exit' to end application";
        String EXIT_COMMAND       = "Exit";
        String ADD_ITEM_COMMAND   = "1";
        String CHECKOUT_COMMAND   = "2";
        String SEARCH_COMMAND     = "3";
        String EMPTY_CART_WARNING = "\nINFO: Please add items to cart\n";

        while (inInfiniteLoop) {
            System.out.println(COMMAND_OPTIONS);
            String commandChoice = scanner.nextLine();
            
            if (commandChoice.equalsIgnoreCase(EXIT_COMMAND))
                break;
            if (commandChoice.equals(ADD_ITEM_COMMAND))
                addItem(scanner);
            if (commandChoice.equals(CHECKOUT_COMMAND) && selectIndex != startIndex) {
                generateBill();
                break;
            }
            if (commandChoice.equals(CHECKOUT_COMMAND) && selectIndex == startIndex)
                System.out.println(EMPTY_CART_WARNING);
            if (commandChoice.equals(SEARCH_COMMAND))
                searchForItemByName(items, scanner);
        }
    }

    private static void addItem(Scanner scanner) {
        String ITEMS_LABEL      = "\nITEMS:";              
        String FINISH_COMMAND   = "Finish";
        String ADD_ITEMS_PROMPT = "\nENTER ITEM ID TO ADD\n- Select id options: [0 - " + (items.length - 1) + "]\n- If done, type and enter 'Finish'";

        System.out.println(ITEMS_LABEL);
        displayItems(items, prices);
        System.out.println(ADD_ITEMS_PROMPT);

        while (inInfiniteLoop) {
            String entryCommand = scanner.nextLine();

            if (entryCommand.equalsIgnoreCase(FINISH_COMMAND)) {
                System.out.println();
                break;
            }

            validateAddItemEntry(entryCommand);
        }
    }

    private static void displayItems(String[] items, float[] prices) {
        int itemsPerRow = 4;

        for (int i = startIndex; i < items.length; i++) {
            String ITEM_DETAILS = "(" + i + ") " + items[i] + " " + currencySign + String.format(moneyDecimalFormat, prices[i]) + "  ";
            System.out.print(ITEM_DETAILS);
            if (i % itemsPerRow == startIndex && i != startIndex)
                System.out.println();
        }

        System.out.println();
    }

    private static void validateAddItemEntry(String entryCommand) {
        String INVALID_INTEGER_WARNING = "\nERROR: Please enter a valid integer to add item,\nor if you're done adding, type and enter 'Finish'";

        try {
            validateItemId(entryCommand);
        } catch (NumberFormatException e) {
            System.out.println(INVALID_INTEGER_WARNING);
        }
    }

    private static void validateItemId(String entry) {
        int itemNum = Integer.parseInt(entry);
        String INVALID_ID_ERROR_MESSAGE     = "\nERROR: Please enter an ID number from 0 - " + (items.length - 1) + " to add item,\nor if you're done adding, type and enter 'Finish'";
        String CONTINUE_TO_ADD_ITEM_MESSAGE = "\nYou can add more items or enter 'Finish' if done";
        String MAXIMUM_CART_LIMIT_WARNING   = "\nINFO: Cart has reached it's maximum limit of 5 items, enter 'Finish' to checkout";

        if (itemNum >= startIndex && itemNum < items.length) {
            if (selectIndex != selectedItems.length) {
                selectedItems[selectIndex++] = itemNum;
                countUnit[itemNum]++;
                printSelectedItems();
                System.out.println(CONTINUE_TO_ADD_ITEM_MESSAGE);
                return;
            } 

            printSelectedItems();
            System.out.println(MAXIMUM_CART_LIMIT_WARNING);            
        } 
        
        System.out.println(INVALID_ID_ERROR_MESSAGE);
    }

    private static void printSelectedItems() {
        String SELECTED_ITEMS_LABEL = "Selected Items: ";

        for (int i = startIndex; i < selectIndex; i++) {
            String SELECTED_ITEM_NAME = items[selectedItems[i]] + "\t";

            if (i == startIndex)
                System.out.print(SELECTED_ITEMS_LABEL);
            System.out.print(SELECTED_ITEM_NAME);
        }
    }

    private static void generateBill() {
        String BILL_LABEL = "\nBILL:";

        System.out.println(BILL_LABEL);
        float totalCost = startIndex;

        for (int i = startIndex; i < countUnit.length; i++) {
            if (countUnit[i] == 0)
                continue;
            float quantityPrice = prices[i] * countUnit[i];
            totalCost += quantityPrice;
            String individualItemCostMessage = items[i] + " x" + countUnit[i] + ": " + currencySign + String.format(moneyDecimalFormat, quantityPrice);

            System.out.println(individualItemCostMessage);
        }

        String totalItemCostMessage = "Total Bill: " + currencySign + String.format(moneyDecimalFormat, totalCost) + "\n";
        System.out.println(totalItemCostMessage);
    }

    private static void searchForItemByName(String[] items, Scanner scanner) {
        String searchResponse     = "Item not found\n";
        String inputSearchMessage = "Enter item name";

        System.out.println(inputSearchMessage);
        String searchTerm = scanner.nextLine();

        for(int i = startIndex; i < items.length; i++) {
            boolean isSearchMatching = items[i].toLowerCase().matches(searchTerm.toLowerCase());
            if (isSearchMatching){
                searchResponse = "\nITEM FOUND: " + items[i] + " " + currencySign + String.format(moneyDecimalFormat, prices[i]) + "\n";
                break;
            }
        }

        System.out.println(searchResponse);
    }
}