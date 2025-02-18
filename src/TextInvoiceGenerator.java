public class TextInvoiceGenerator implements InvoiceGenerator {
    @Override
    public String generateInvoice(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("Yogurt Shop Invoice\n");
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            sb.append(String.format("%s - %d %s: $%.2f%n",
                    product.getName(),
                    item.getQuantity(),
                    product.getUnit(),
                    item.getTotalPrice()));
        }
        sb.append(String.format("Subtotal: $%.2f%n", order.calculateSubtotal()));
        sb.append(String.format("Tax : $%.2f%n", order.calculateTax()));
        sb.append(String.format("Total Amount Due: $%.2f", order.calculateTotal()));
        return sb.toString();
    }
}