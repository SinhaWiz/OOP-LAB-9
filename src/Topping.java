public enum Topping implements Product {
    SPRINKLES("Sprinkles", 0.30),
    MIXED_CHOPPED_NUTS("Mixed chopped nuts", 0.80),
    CRUSHED_GINGERBREAD("Crushed Gingerbread", 0.75),
    FRESH_STRAWBERRIES("Fresh Strawberries", 1.00);

    private final String name;
    private final double price;

    Topping(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getUnit() {
        return "time";
    }
}