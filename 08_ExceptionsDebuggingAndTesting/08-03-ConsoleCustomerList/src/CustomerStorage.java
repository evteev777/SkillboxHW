import java.util.HashMap;

public class CustomerStorage
{
    private HashMap<String, Customer> storage;

    String nameFormat = "Имя: только рус/eng буквы (без пробелов и других символов)";
    final String nameRegEx = "^[а-яА-яa-zA-Z]*$";

    String surNameFormat = "Фамилия: только рус/eng буквы, возможно - через дефис (без пробелов и других символов))";
    final String surNameRegEx = "^[а-яА-яa-zA-Z\\-]*$";

    String phoneNumberFormat = "Телефон: в формате +79991234567 (10-13 цифр)";
    final String phoneRegEx = "^\\+[\\d]{11,14}$";

    String emailFormat = "E-mail: в формате name@domain.com (только eng буквы, дефисы, подчеркивания, точки, и один символ @)";
    final String emailRegEx = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";

    public CustomerStorage()
    {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) throws IllegalArgumentException
    {
        // Если пробелов нет вообще - введенная строка не разобьется на компоненты
        if (!data.contains(" ")) {
            throw new IllegalArgumentException(
                    "Ошибка! Введен один компонент из четырех, или компоненты не разделены пробелами");
        }

        String[] components = data.split("\\s+");

        if (components.length > 4) {
            throw new IllegalArgumentException("Ошибка! Введено более четырех компонентов");
        }
        if (components.length < 4) {
            throw new IllegalArgumentException("Ошибка! Введено менее четырех компонентов");
        }

        if (!components[0].matches(nameRegEx)) {
            throw new IllegalArgumentException("Ошибка! " + nameFormat);
        }
        if (!components[1].matches(surNameRegEx)) {
            throw new IllegalArgumentException("Ошибка! " + surNameFormat);
        }
        if (!components[2].matches(emailRegEx)) {
            throw new IllegalArgumentException("Ошибка! " + emailFormat);
        }
        if (!components[3].matches(phoneRegEx)) {
            throw new IllegalArgumentException("Ошибка! " + phoneNumberFormat);
        }

        String name = components[0] + " " + components[1];

        if (storage.containsKey(name)) {
            throw new IllegalArgumentException("Ошибка! В базе уже есть такая запись");
        }

        storage.put(name, new Customer(name, components[3], components[2]));
        System.out.println("added");
    }

    public void listCustomers()
    {
        // Если storage пустой - просто выводим сообщение об этом, без эесепшена
        if (storage.size() != 0) {
            storage.values().forEach(System.out::println);
        }
        else {
            System.out.println("There are 0 customers");
        }

    }

    public void removeCustomer(String name)
    {
        // Если пробелов нет вообще - введенная строка не разобьется на компоненты

        if (!name.contains(" ")) {
            throw new IllegalArgumentException(
                    "Ошибка! Введено только имя, или только фамилия, или они не разделены пробелом");
        }
        if (!storage.containsKey(name)) {
            throw new IllegalArgumentException("Ошибка! В базе нет такой записи для удаления");
        }
        storage.remove(name);
        System.out.println(name + " removed ");
    }

    public int getCount()
    {
        return storage.size();
    }
}