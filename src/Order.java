import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Order {
    private final List<OrderItem> items = new ArrayList<>();
    private boolean isGlassJar;
    private static final double TAX_RATE = 0.08;

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public void setContainer(boolean isGlassJar) {
        this.isGlassJar = isGlassJar;
    }

    public double calculateSubtotal() {
        double itemsTotal = items.stream().mapToDouble(OrderItem::getTotalPrice).sum();
        return itemsTotal + (isGlassJar ? 5.0 : 0.0);
    }

    public double calculateTax() {
        return calculateSubtotal() * TAX_RATE;
    }

    public double calculateTotal() {
        return calculateSubtotal() + calculateTax();
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean isGlassJar() {
        return isGlassJar;
    }
}