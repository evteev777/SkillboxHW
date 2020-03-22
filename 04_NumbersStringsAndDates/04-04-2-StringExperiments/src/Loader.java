
public class Loader
{
    public static void main(String[] args)
    {
        String text = "Вася заработал 5000 рублей, Петя - 7563 рубля, а Маша - 30000 рублей";
        System.out.println(text);

        String text1 = text.substring((text.indexOf(' ') + 1), (text.indexOf("руб")));
//        System.out.println("* промежуточный вариант: " + text1);
        int vasyaSalary = Integer.parseInt(text1.substring(text1.indexOf(' ') + 1).trim());

        String text2 = text.substring((text.indexOf('-') + 2), (text.lastIndexOf("Маша")));
//        System.out.println("* промежуточный вариант: " + text2);
        int petyaSalary = Integer.parseInt(text2.substring(0, text2.lastIndexOf("руб")).trim());

        int mashaSalary = Integer.parseInt(text.substring((text.lastIndexOf('-') + 2), (text.lastIndexOf("руб")) - 1));

        System.out.println("Вместе они заработали " + (vasyaSalary + petyaSalary + mashaSalary) + " руб.");
    }
}