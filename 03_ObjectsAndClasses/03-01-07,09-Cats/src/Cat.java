
public class Cat
{
    private String name;
    private CatColor catColor;

    private double originWeight;
    private double weight;

    private static final double minWeight = 1000.0;
    private static final double maxWeight = 9000.0;

    private double feedAmount;
    private double drinkAmount;

    public static final int EYES_COUNT = 2;

    boolean isAlive = true;

    private static int count;

    Cat() {
        this("Cat", CatColor.WHITE, 1500.0 + 3000.0 * Math.random());
    }

    Cat(String name, CatColor catColor, double weight) {
        this.name = name;
        this.catColor = catColor;
        this.weight = weight;
        originWeight = weight;
        isBorn();
    }

    Cat(Cat originName) {
        setName(originName.getName());
        setCatColor(originName.getCatColor());
        setWeight(originName.getWeight());
        setOriginWeight(originName.getOriginWeight());
        isBorn();
    }

    private void isBorn() {
        if (weight >= minWeight && weight <= maxWeight) {
            count++;
            System.out.println("-Hi, " + name + "!");
        }
        else {
            isAlive = false;
            System.out.print(" " + name + " was born dead:");
        }
    }

    private void isDead() {
        if (weight < minWeight || weight > maxWeight) {
            isAlive = false;
            count--;
        }
    }

    void meow(int meowCount) {
            for (int i = 0; i < meowCount  && isAlive; i++) {
                weight = weight - 1;
                isDead();
            }
    }

    void feed(int feedCount, double amount) {
            for (int i = 0; i < feedCount && isAlive; i++) {
                weight += amount;
                feedAmount += amount;
                isDead();
            }
    }

    void drink(int drinkCount, double amount) {
            for (int i = 0; i < drinkCount && isAlive; i++) {
                weight += amount;
                drinkAmount += amount;
                isDead();
            }
    }

    double getFeedAndDrink() {
        return feedAmount + drinkAmount;
    }

    void toilet(int toiletCount, double amount) {
            for (int i = 0; i < toiletCount && isAlive; i++) {
            weight -= amount;
            isDead();
            }
    }

    String getStatus() {
        if(weight < minWeight) {
            return "died of exhaustion";
        }
        else if(weight > maxWeight) {
            return "exploded!!!";
        }
        else if(weight > originWeight) {
            return "sleeping";
        }
        else {
            return "playing";
        }
    }

    static int getCount() { return count; }

    private void setCatColor(CatColor catColor) {this.catColor = catColor;}
    CatColor getCatColor() { return catColor; }

    private void setName(String name) {this.name = name;}
    String getName() { return name; }

    private void setWeight(double weight) {this.weight = weight;}
    double getWeight() { return weight; }

    private void setOriginWeight(double originWeight) {this.originWeight = originWeight;}
    double getOriginWeight() { return originWeight; }
}