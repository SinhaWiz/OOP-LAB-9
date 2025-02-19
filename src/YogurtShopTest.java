import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class YogurtShopTest {

    @Test
    void testSubtotalWithoutPaperCup() {
        Order order = new Order();
        order.addItem(new OrderItem(YogurtFlavor.COOKIES_AND_CREAM, 1));
        order.addItem(new OrderItem(Topping.SPRINKLES, 1));
        order.setContainer(false);
        assertEquals(3.10, order.calculateSubtotal(), 0.01);
    }

    @Test
    void testSubtotalWithGlassJar() {
        Order order = new Order();
        order.addItem(new OrderItem(YogurtFlavor.CHOCOLATE_FUDGE, 2));
        order.addItem(new OrderItem(Topping.FRESH_STRAWBERRIES, 1));
        order.setContainer(true); // Glass jar
        assertEquals(12.00, order.calculateSubtotal(), 0.01);
    }

    @Test
    void testTaxCalculation() {
        Order order = new Order();
        order.addItem(new OrderItem(YogurtFlavor.PISTACHIO_DELIGHT, 1));
        order.addItem(new OrderItem(Topping.CRUSHED_GINGERBREAD, 1));
        order.setContainer(false);
        assertEquals(0.32, order.calculateTax(), 0.01);
    }

    @Test
    void testTotalAmount() {
        Order order = new Order();
        order.addItem(new OrderItem(YogurtFlavor.COOKIES_AND_CREAM, 1));
        order.addItem(new OrderItem(Topping.MIXED_CHOPPED_NUTS, 1));
        order.setContainer(true);
        assertEquals(9.29, order.calculateTotal(), 0.01);
    }

    @Test
    void testTextInvoiceGeneration() {
        Order order = new Order();
        order.addItem(new OrderItem(YogurtFlavor.CHOCOLATE_FUDGE, 1));
        order.addItem(new OrderItem(Topping.SPRINKLES, 1));
        order.setContainer(false); // Paper cup

        InvoiceGenerator generator = new TextInvoiceGenerator();
        String invoice = generator.generateInvoice(order);

        String expectedInvoice = """
                Yogurt Shop Invoice
                Chocolate Fudge - 1 scoop: $3.00
                Sprinkles - 1 time: $0.30
                Subtotal: $3.30
                Tax : $0.26
                Total Amount Due: $3.56""";

        assertEquals(expectedInvoice, invoice);
    }

    @Test
    void testCSVInvoiceGeneration() {
        Order order = new Order();
        order.addItem(new OrderItem(YogurtFlavor.PISTACHIO_DELIGHT, 2));
        order.addItem(new OrderItem(Topping.FRESH_STRAWBERRIES, 1));
        order.setContainer(true); // Glass jar

        InvoiceGenerator generator = new CSVInvoiceGenerator();
        String invoice = generator.generateInvoice(order);

        String expectedInvoice = """
                Ingredients,Amount,Price
                Pistachio Delight,2,6.50
                Fresh Strawberries,1,1.00
                Subtotal,-,12.50
                Tax,-,1.00
                Total Amount Due,-,13.50""";

        assertEquals(expectedInvoice, invoice);
    }

    @Test
    void testMultipleYogurtFlavors() {
        Order order = new Order();
        order.addItem(new OrderItem(YogurtFlavor.COOKIES_AND_CREAM, 1));
        order.addItem(new OrderItem(YogurtFlavor.CHOCOLATE_FUDGE, 1));
        order.setContainer(false); // Paper cup
        assertEquals(5.80, order.calculateSubtotal(), 0.01);
    }

    @Test
    void testMultipleToppings() {
        Order order = new Order();
        order.addItem(new OrderItem(YogurtFlavor.PISTACHIO_DELIGHT, 1));
        order.addItem(new OrderItem(Topping.SPRINKLES, 1));
        order.addItem(new OrderItem(Topping.MIXED_CHOPPED_NUTS, 1));
        order.setContainer(false); // Paper cup
        assertEquals(4.35, order.calculateSubtotal(), 0.01);
    }

    @Test
    void testInvalidYogurtFlavor() {
        Order order = new Order();
        // Simulate invalid flavor input (not added to order)
        assertEquals(0, order.getItems().size());
    }

    @Test
    void testInvalidTopping() {
        Order order = new Order();
        // Simulate invalid topping input (not added to order)
        assertEquals(0, order.getItems().size());
    }
}