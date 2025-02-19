import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Order order = new Order();

        System.out.println("Select yogurt flavors (name quantity), type 'done' to finish:");
        processItems(scanner, order, true);

        System.out.println("Select toppings (name quantity), type 'done' to finish:");
        processItems(scanner, order, false);

        System.out.println("Choose container (cup/jar):");
        String containerInput = scanner.nextLine().trim().toLowerCase();
        while (!containerInput.equals("cup") && !containerInput.equals("jar")) {
            System.out.println("Invalid container. Please choose 'cup' or 'jar':");
            containerInput = scanner.nextLine().trim().toLowerCase();
        }
        order.setContainer(containerInput.equals("jar"));

        System.out.println("Select invoice format (text/csv):");
        String formatInput = scanner.nextLine().trim().toLowerCase();
        while (!formatInput.equals("text") && !formatInput.equals("csv")) {
            System.out.println("Invalid format. Please choose 'text' or 'csv':");
            formatInput = scanner.nextLine().trim().toLowerCase();
        }
        InvoiceGenerator generator = getInvoiceGenerator(formatInput);

        String invoice = generator.generateInvoice(order);
        System.out.println(invoice);

        System.out.println("Enter file name:");
        String fileName = scanner.nextLine().trim();
        try {
            FileExporter.export(invoice, fileName + (formatInput.equals("text") ? ".txt" : ".csv"));
            System.out.println("Invoice saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving invoice: " + e.getMessage());
        }
    }
    private static void processItems(Scanner scanner, Order order, boolean isFlavor) {
        while (true) {
            System.out.print("> "); // Prompt for input
            String input = scanner.nextLine().trim();

            // Check if the user wants to finish
            if (input.equalsIgnoreCase("done")) {
                break;
            }

            // Split the input into name and quantity
            int lastSpaceIndex = input.lastIndexOf(' ');
            if (lastSpaceIndex == -1) {
                System.out.println("Invalid input. Please enter in the format '<name> <quantity>'.");
                continue;
            }

            String name = input.substring(0, lastSpaceIndex).trim();
            String quantityStr = input.substring(lastSpaceIndex + 1).trim();

            // Validate quantity
            int quantity;
            try {
                quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    System.out.println("Quantity must be a positive number.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity. Please enter a valid number.");
                continue;
            }

            // Find the product (flavor or topping)
            Product product = isFlavor ? findYogurtFlavor(name) : findTopping(name);
            if (product == null) {
                System.out.println("Invalid " + (isFlavor ? "flavor" : "topping") + ". Please try again.");
                continue;
            }

            // Add the item to the order
            order.addItem(new OrderItem(product, quantity));
        }
    }
    private static InvoiceGenerator getInvoiceGenerator(String format) {
        return format.equals("csv") ? new CSVInvoiceGenerator() : new TextInvoiceGenerator();
    }

    private static YogurtFlavor findYogurtFlavor(String name) {
        for (YogurtFlavor flavor : YogurtFlavor.values()) {
            if (flavor.getName().equalsIgnoreCase(name)) {
                return flavor;
            }
        }
        return null;
    }

    private static Topping findTopping(String name) {
        for (Topping topping : Topping.values()) {
            if (topping.getName().equalsIgnoreCase(name)) {
                return topping;
            }
        }
        return null;
    }
}