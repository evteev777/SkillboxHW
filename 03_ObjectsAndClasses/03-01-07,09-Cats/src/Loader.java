
public class Loader
{
    public static void main(String[] args)
    {
        Cat murka = new Cat();
        murka.feed(2,5.0);
        murka.drink(3,1.0);
        murka.meow(50);
        System.out.println(" Murka is " + murka.getStatus() + "\n");

        Cat vaska = new Cat();
        while(vaska.isAlive){
            vaska.feed(10, 100.0);
            vaska.drink(10, 50.0);
        }
        System.out.println(" Vaska is " + vaska.getStatus() + "\n");

        Cat barsik = new Cat();
        while(barsik.isAlive){
            barsik.meow(500);
        }
        System.out.println(" Barsik is " + barsik.getStatus() + "\n");

        Cat murzik = new Cat();
        murzik.feed(2,50.0);
        murzik.toilet(5,20.0);
        murzik.drink(1,50.0);
        System.out.println(" Murzik is " + murzik.getStatus());
        System.out.println(" Today Murzik ate and drank " + murzik.getFeedAndDrink() + " g in total\n");

        Cat kuzya = new Cat("Kyzya", CatColor.BLACK, 3000.0);
        System.out.println("-My name is " + kuzya.getName() +
            ",\n my color is " + kuzya.getCatColor() +
            ", my weight is " + kuzya.getWeight() + " g, origin weight " + kuzya.getOriginWeight() + "g");
        System.out.println(" Kuzya is " + kuzya.getStatus() + "\n");

        Cat deepcopy = new Cat(kuzya);
        System.out.println("-You are right, my name is not Deep Copy, my name is " + deepcopy.getName() +
            ",\n my color is " + deepcopy.getCatColor() +
            ", my weight is " + deepcopy.getWeight() + " g, origin weight " + deepcopy.getOriginWeight() + " g");
        System.out.println(" Deep Copy is " + deepcopy.getStatus() + "\n");

        System.out.println(" Now we have " + Cat.getCount() + " cats");
    }
}