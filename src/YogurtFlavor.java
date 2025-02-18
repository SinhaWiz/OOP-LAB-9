public enum YogurtFlavor implements Product {
    COOKIES_AND_CREAM("Cookies and Cream", 2.80),
    CHOCOLATE_FUDGE("Chocolate Fudge", 3.00),
    PISTACHIO_DELIGHT("Pistachio Delight", 3.25);

    private final String name;
    private final double price;

    YogurtFlavor(String name, double price) {
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
        return "scoop";
    }
}