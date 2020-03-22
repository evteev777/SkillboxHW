public class Main
{
    public static void main(String[] args)
    {
        Container container = new Container();
        container.count += 7843;

        Integer number = 14253647;
        int total = sumDigits(number);
        System.out.println("Total: " + total);
    }

    private static Integer sumDigits(Integer number)
    {
        //@TODO: write code here
        String textString = Integer.toString(number);
        int currentSum = 0;
        for (int i = 0; i < textString.length(); i++) {
//            System.out.print(currentSum);
            currentSum = currentSum + Integer.parseInt(String.valueOf(textString.charAt(i)));
//            System.out.println(" + " + textString.charAt(i) + " = " + currentSum);
        }
        return currentSum;
    }
}
