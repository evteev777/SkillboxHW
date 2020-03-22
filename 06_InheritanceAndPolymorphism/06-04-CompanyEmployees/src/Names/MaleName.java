package Names;

public enum MaleName {
    Артём,
    Артемий,
    Александр,
    Максим,
    Даниил,
    Данил,
    Данила,
    Дмитрий,
    Димитрий,
    Иван,
    Кирилл,
    Никита,
    Михаил,
    Егор,
    Егорий,
    Матвей,
    Андрей,
    Илья,
    Алексей,
    Роман,
    Сергей,
    Владислав,
    Ярослав,
    Тимофей,
    Арсений,
    Арсентий,
    Денис,
    Владимир,
    Павел,
    Глеб,
    Константин,
    Богдан,
    Евгений,
    Николай,
    Степан,
    Захар,
    Тимур,
    Марк,
    Семён,
    Фёдор,
    Георгий,
    Лев,
    Антон,
    Антоний,
    Вадим,
    Игорь,
    Руслан,
    Вячеслав,
    Григорий,
    Макар,
    Артур,
    Виктор,
    Станислав,
    Савелий,
    Олег,
    Давид,
    Давыд,
    Леонид,
    Пётр,
    Юрий,
    Виталий,
    Мирон,
    Василий,
    Всеволод,
    Елисей,
    Назар,
    Родион,
    Марат,
    Платон,
    Герман,
    Игнат,
    Игнатий,
    Святослав,
    Анатолий,
    Тихон,
    Валерий,
    Мирослав,
    Ростислав,
    Борис,
    Филипп,
    Демьян,
    Клим,
    Климент,
    Гордей,
    Валентин,
    Геннадий,
    Демид,
    Диомид,
    Прохор,
    Серафим,
    Савва,
    Яромир,
    Аркадий,
    Архип,
    Тарас,
    Трофим;

    public static MaleName getMaleName() {
        return values()[(int) (Math.random() * values().length)];
    }
}
