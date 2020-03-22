package core;

public class Car
{
    // Переменная reason типа String - текст
    public String number;
    // Переменная типа int - целое число
    public int height;
    // Переменная типа double - число с дробной частью
    public double weight;
    // Переменная типа boolean - true/false
    public boolean hasVehicle;
    // Добавил переменную типа Strind для вывода надписи "с прицепом"
    public String hasVehicleMsg;
    // Переменная типа boolean - true/false
    public boolean isSpecial;

    public String toString()
    {
        // Если автомобиль с прицепом -
        // записываем в переменную hasVehicleMsg типа String сообщение об этом
        hasVehicleMsg = hasVehicle == true ? "с прицепом " : "";

        // Переменная типа String - текст
        String special = isSpecial ? "СПЕЦТРАНСПОРТ " : "";
        return "\n====================================================\n" +
            special + "Автомобиль " + hasVehicleMsg + "гос.номер " + number +
            ":\n\tВысота: " + height + " мм\n\tМасса: " + weight + " кг";
    }

    // Сеттеры, геттеры
    public  void setNumber(String number) { this.number = number; }
    public String getNumber() { return number; }

    public void setHeight(int height) { this.height = height; }
    public int getHeight() { return height; }

    public void setWeight (double weight) { this.weight = weight;}
    public double getWeight () { return weight; }

    public void setHasVehicle (boolean hasVehicle) { this.hasVehicle = hasVehicle;}
    public boolean getHasVehicle () { return hasVehicle; }

    public  void setHasVehicleMsg(String hasVehicleMsg) { this.hasVehicleMsg = hasVehicleMsg; }
    public String getHasVehicleMsg() { return hasVehicleMsg; }

    public void setIsSpecial (boolean isSpecial) { this.isSpecial = isSpecial;}
    public boolean getIsSpecial () { return isSpecial; }

}