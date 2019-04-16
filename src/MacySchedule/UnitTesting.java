package MacySchedule;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * This class contains all the unit tests for the classes in this Macy Project.
 *
 * @author Lisa Chen
 * @since 26Nov18
 * @version 1
 */
public class UnitTesting
{
    public static void main(String[] args) throws FileNotFoundException
    {
//        timeUnitTest();
//        employeeUnitTest();
        readerUnitTest();
    }

    /** This is a unit test for the Time class. */
    private static void timeUnitTest()
    {
        Time test = new Time(1.5);
        System.out.println(test.getStrTime());
        test = new Time(0.75);
        System.out.println(test.getStrTime());
        test = new Time(12);
        System.out.println(test.getStrTime());
        test = new Time(23.25);
        System.out.println(test.getStrTime());
        test = new Time(14.12);
        System.out.println(test.getStrTime());
        test = new Time("10:33p");
        System.out.println(test.getStrTime());
        System.out.println(test.getDecimalTime());
        test = new Time("10:00a");
        System.out.println(test.getStrTime());
        System.out.println(test.getDecimalTime());
        test = new Time("12:15a");
        System.out.println(test.getStrTime());
        System.out.println(test.getDecimalTime());
        test = new Time("12:45p");
        System.out.println(test.getStrTime());
        System.out.println(test.getDecimalTime());
        test = new Time("5:45p");
        System.out.println(test.getStrTime());
        System.out.println(test.getDecimalTime());
        test = new Time("11:50p");
        System.out.println(test.getStrTime());
        System.out.println(test.getDecimalTime());
    }

    /** This is a unit test for the Employee class. */
    private static void employeeUnitTest()
    {
        Employee test = new Employee("Lisa", "Chen", "");
        test.addHoursAtDay(3, "TestDept", 10, "4:30p - 12:30a");
        test.addHoursAtDay(5, "TestDept", 10, "1:00p - 3:00p");
        System.out.println(test.getDayTotalHours(3));
        System.out.println(test.getDayTotalHours(5));
        System.out.println(test.getWeekTotalHours());
    }

    /**
     * This is a class that tests the SchedulePageReader class and also encompasses
     * testing WeekSchedule, Employee, and Time classes.
     * @throws FileNotFoundException For if the reade file or save file has issues
     */
    private static void readerUnitTest() throws FileNotFoundException
    {
        File read = new File("My Area View.html");
        SchedulePageReader test = new SchedulePageReader(read);
        WeekSchedule weekSchedule = test.getWeekSchedule();
        System.out.println(weekSchedule.getDeptNum() + " - " +
                weekSchedule.getDepartment());
        for (int value : weekSchedule.getStartWeek())
            System.out.print(value + " ");
        System.out.println();
        for (int value2 : weekSchedule.getEndWeek())
            System.out.print(value2 + " ");

        Employee[] employees = weekSchedule.getEmployeeList();
        for (Employee employee : employees)
        {
            if (employee != null)
            {
                System.out.println("\n\nFirst: " + employee.getFirstName());
                System.out.println("Middle: " + employee.getMiddleName());
                System.out.println("Last: " + employee.getLastName());
                for (int i = 0; i < 7; i++)
                {
                    String[] daySchedule = employee.getDaySchedule(i);
                    System.out.println("Dept: " + daySchedule[0]);
                    System.out.println("Hours: " + daySchedule[1]);
                    System.out.println("Day Qty Hours: " + employee.getDayTotalHours(i));
                }
                System.out.println("Dept Week Hours: " + employee.getWeekDeptHours(6601));
                System.out.println("Total Week Hours: " + employee.getWeekTotalHours() +
                        "\n");
            }
        }
        System.out.println(weekSchedule.getTotalDeptHours());
    }
}