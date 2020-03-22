public class Main
{
    public static void main(String[] args) {

        int vasyaAge  = 20;
        int grishaAge = 21;
        int mishaAge  = 22;

        int min;
        int mid;
        int max;

        if (vasyaAge > grishaAge && vasyaAge > mishaAge) {
            max = vasyaAge;
        }
        else max = grishaAge > mishaAge ? grishaAge : mishaAge;

        if (vasyaAge < grishaAge && vasyaAge < mishaAge) {
            min = vasyaAge;
        }
        else min = grishaAge < mishaAge ? grishaAge : mishaAge;

        mid = vasyaAge + grishaAge + mishaAge - max - min;

        System.out.println("Minimum age is " + min + " years old");
        System.out.println("Middle age is "  + mid + " years old");
        System.out.println("Maximum age is " + max + " years old");
    }
}
