public class CSVInvoiceGenerator implements InvoiceGenerator {
    @Override
    public String generateInvoice(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("Ingredients,Amount,Price\n");
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            sb.append(String.format("%s,%d,%.2f%n",
                    product.getName(),
                    item.getQuantity(),
                    item.getTotalPrice()));
        }
        sb.append(String.format("Subtotal,-,%.2f%n", order.calculateSubtotal()));
        sb.append(String.format("Tax,-,%.2f%n", order.calculateTax()));
        sb.append(String.format("Total Amount Due,-,%.2f", order.calculateTotal()));
        return sb.toString();
    }
}
