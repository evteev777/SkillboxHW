package Staff;

public interface Employee {

  int getId();
  String getFullName();
  String getCategory();
  double getExperience();
  double getSalary();
  void setSalary(double salary);
}