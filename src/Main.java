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
        order.setContainer(scanner.nextLine().equalsIgnoreCase("jar"));

        System.out.println("Select invoice format (text/csv):");
        InvoiceGenerator generator = getInvoiceGenerator(scanner);
        String invoice = generator.generateInvoice(order);
        System.out.println(invoice);
        System.out.println("Enter file name:");
        try {
            FileExporter.export(invoice, scanner.nextLine());
            System.out.println("Invoice saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving invoice: " + e.getMessage());
        }
    }
    private static void processItems(Scanner scanner, Order order, boolean isFlavor) {
        String input;
        while (!(input = scanner.nextLine()).equalsIgnoreCase("done")) {
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println("Invalid input");
                continue;
            }
            Product product = isFlavor ? findYogurtFlavor(parts[0]) : findTopping(parts[0]);
            if (product == null) {
                System.out.println("Invalid " + (isFlavor ? "flavor" : "topping"));
                continue;
            }
            order.addItem(new OrderItem(product, Integer.parseInt(parts[1])));
        }
    }
    private static InvoiceGenerator getInvoiceGenerator(Scanner scanner) {
        String format = scanner.nextLine();
        return format.equalsIgnoreCase("csv") ? new CSVInvoiceGenerator() : new TextInvoiceGenerator();
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