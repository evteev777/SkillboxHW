class Loading {

    private int truckCapacity = 12; // объем грузовика 12 контейнеров
    private int containerCapacity = 27; // объем контейнера 27 ящиков

    private int truckNumber = 1;
    private int containerNumber = 1;
    private int boxNumber = 1;

    Loading (int boxCount) {

        while (boxNumber <= boxCount) {
            System.out.print((boxNumber % (truckCapacity * containerCapacity) == 1) ? ("Грузовик " + truckNumber++ + "\n") : "");
            System.out.print((boxNumber % containerCapacity == 1) ? ("\tКонтейнер " + containerNumber++ + "\n") : "");
            System.out.println("\t\t\tЯщик " + boxNumber++);
        }

        System.out.println("Итого:\nГрузовиков: " + (truckNumber - 1));
        System.out.println("Контейнеров: " + (containerNumber - 1));
        System.out.println("Ящиков: " + (boxNumber - 1));
    }
}