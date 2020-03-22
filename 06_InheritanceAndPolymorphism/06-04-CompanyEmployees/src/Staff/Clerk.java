package Staff;

public class Clerk extends Staff {

    public Clerk(int id, String category) {
        super(id, category);
    }

    @Override
    public double salary() {
        this.fixSalary = 35000.0;
        return Math.round(fixSalary);
    }
}