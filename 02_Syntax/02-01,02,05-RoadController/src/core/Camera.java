package core;

public class Camera
{
    public static Car getNextCar()
    {
        // Доработка ДЗ-2: Переменная типа String - текст
        String randomNumber = Double.toString(Math.random()).substring(2, 5);
        // Переменная типа int - целое число
        int randomHeight = (int) (1000 + 3500. * Math.random());
        // Переменная типа double - число с дробной частью
        double randomWeight = 600 + 10000 * Math.random();

        Car car = new Car();
        car.number = randomNumber;
        car.height = randomHeight;
        car.weight = randomWeight;
        car.hasVehicle = Math.random() > 0.5;
        car.isSpecial = Math.random() < 0.15;

        return car;
    }
}