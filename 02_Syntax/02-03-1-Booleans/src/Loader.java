public class Loader
{
    public static void main(String[] args)
    {
        // РЕЦЕПТЫ:
        // Omelette: milk - 300 ml, powder - 5 g, eggs - 5
        // ApplePie: milk - 100 ml, powder - 300 g, eggs - 4, apples - 3
        // Pancakes: milk - 1000 ml, powder - 400 g, sugar - 10 g, oil - 30 ml

        // ЗАПАСЫ:
        int milkAmount = 1400; // ml
        int powderAmount = 705; // g
        int eggsCount = 9; // items
        int appleCount = 3; // items
        int sugarAmount = 10; // g
        int oilAmount = 30; // ml

        if (milkAmount >= 300+100+1000 && powderAmount >= 5+300+400 && eggsCount >= 5+4 && appleCount >= 3 && sugarAmount >= 10 && oilAmount >= 30)
        {
            System.out.println("Из того, что есть, можно приготовить и омлет, и яблочный пирог, и кекс!");
            System.out.println("По отдельности, или все сразу!");
        }
        else
        {
            boolean canCookOmlAPie = (milkAmount >=  300+100 && powderAmount >=   5+300 && eggsCount >= 5+4 && appleCount  >=  3) ? true : false;
            boolean canCookOmlPnc  = (milkAmount >= 300+1000 && powderAmount >=   5+400 && eggsCount >=   5 && sugarAmount >= 10 && oilAmount   >= 30) ? true : false;
            boolean canCookAPiePnc = (milkAmount >= 100+1000 && powderAmount >= 300+400 && eggsCount >=   4 && appleCount  >=  3 && sugarAmount >= 10 && oilAmount >= 30) ? true : false;

            if (canCookOmlAPie == true || canCookOmlPnc == true || canCookAPiePnc == true)
            {
                System.out.println("Из того, что есть, можно приготовить:");
                if (canCookOmlAPie == true) {System.out.println("- Омлет и яблочный пирог");}
                if (canCookOmlPnc == true)  {System.out.println("- Омлет и кекс");}
                if (canCookAPiePnc == true) {System.out.println("- Яблочный пирог и кекс");}
                System.out.println("Два блюда вместе, или любое из них отдельно!");
            }
            else
            {
                boolean canCookOml  = (milkAmount >=  300 && powderAmount >=   5 && eggsCount   >=  5) ? true : false;
                boolean canCookAPie = (milkAmount >=  100 && powderAmount >= 300 && eggsCount   >=  4 && appleCount >= 3) ? true : false;
                boolean canCookPnc  = (milkAmount >= 1000 && powderAmount >= 400 && sugarAmount >= 10 && oilAmount >= 30) ? true : false;

                if (canCookOml == true || canCookAPie == true || canCookPnc == true)
                {
                    System.out.println("Из того, что осталось, можно приготовить на выбор:");
                    if (canCookOml == true)  {System.out.println("- омлет");}
                    if (canCookAPie == true) {System.out.println("- яблочный пирог ");}
                    if (canCookPnc == true)  {System.out.println("- кекс");}
                }
                else
                {
                    {System.out.println("Дома есть нечего, бегом в магазин )");}
                }
            }
        }
    }
}