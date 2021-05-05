package Classes;

public class Object {

    public Object(String name, double startPrice, String description) {
        this.name = name;
        this.startPrice = startPrice;
        this.description = description;
    }

    public Object() {

    }

    public Object(String name){
        name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String name;
    private double startPrice;
    private String description;
}
