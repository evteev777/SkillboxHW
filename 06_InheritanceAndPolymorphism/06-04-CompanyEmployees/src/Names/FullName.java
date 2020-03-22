package Names;

public class FullName {

    public static String create() {

        String fullName;
        if (Math.random() > 0.5) {
            fullName = Surname.getSurname() + " " + MaleName.getMaleName() + " " +
                    MaleMiddleName.getMaleMiddleName();
        } else {
            fullName = Surname.getSurname() + "а " + FemaleName.getFemaleName() + " "
                    + FemaleMiddleName.getFemaleMiddleName();
        }
        return fullName;
    }
}