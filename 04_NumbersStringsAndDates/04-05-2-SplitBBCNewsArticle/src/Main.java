public class Main {

    public static void main(String[] args) {

        String article = "US singer Jennifer Lopez has revived her iconic green Versace dress that led to the invention of Google Images.\n" +
                "Lopez, 50, first wore the gown to the Grammy Awards in February 2000.\n" +
                "Former Google CEO Eric Schmidt later revealed there were so many searches for photos of the dress afterwards, it inspired them to create Google Images.\n" +
                "Footage of Lopez modelling the modern version of the dress at Versace's S/S 2020 show has been viewed more than two million times on social media.";

        String[] text = article.replaceAll("\n"," ").
                replaceAll("\\."," ").
                replaceAll(","," ").
                replaceAll("\\s+"," ").
                split(" ");

        for (String s : text) {
            System.out.println(s);
        }
    }
}
