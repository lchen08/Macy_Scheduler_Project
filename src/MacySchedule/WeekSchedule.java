package MacySchedule;
/**
 * This is a class for Macy's cumultative work schedule for a given week. The schedule
 * includes Employees with each having their own schedule associated with them.
 *
 * @author Lisa Chen
 * @since 26Nov18
 * @version 1
 */
public class WeekSchedule
{
    private String weekRange;
    private int[] startWeek;
    private int[] endWeek;
    private Employee[] employeeList;
    private int numEmployees;
    private String department;
    private int deptNum;
    private final int WEEK_PARAMETERS = 3;
    private final int MAX_EMPLOYEES = 70; //change if needed

    /**
     * Constructs a week schedule with a given week range, the focus department, and
     * the reference number for the department.
     * @param weekRange The week of given format: XX/XX/XXXX - XX/XX/XXXX
     * @param department The department of focus for the week schedule
     * @param deptNum The reference number for the department
     */
    public WeekSchedule(String weekRange, String department, int deptNum)
    {
        startWeek = new int[WEEK_PARAMETERS];
        endWeek = new int[WEEK_PARAMETERS];
        employeeList = new Employee[MAX_EMPLOYEES];
        numEmployees = 0;
        this.weekRange = weekRange;
        this.department = department;
        this.deptNum = deptNum;
        parseWeekRange();
    }

    /**
     * Initializes the schedule with a given week range, , the focus department,
     * the reference number for the department, employee list, and number of
     * employees (does not include empty indices).
     * @param weekRange The week of given format: XX/XX/XXXX - XX/XX/XXXX
     * @param department The department of focus for the week schedule
     * @param deptNum The reference number for the department
     * @param employeeList The list of employees
     * @param numEmployees The number of employees already in the list
     */
    public WeekSchedule (String weekRange, String department, int deptNum,
                         Employee[] employeeList, int numEmployees)
    {
        this(weekRange, department, deptNum);
        int listLength = employeeList.length;
        if (listLength <= MAX_EMPLOYEES)
        {
            this.numEmployees = numEmployees;

            //if same length, do not alter input employee list
            if (listLength == MAX_EMPLOYEES)
                this.employeeList = employeeList;

            //copy over the employees if input list is smaller than MAX_EMPLOYEES
            else
            {
                for (int i = 0; i < numEmployees; i++)
                    this.employeeList[i] = employeeList[i];
            }
        }
        //larger employee size than MAX_EMPLOYEES not allowed
        else
            throw new IndexOutOfBoundsException("Program is not designed to schedule " +
                    "for more than " + MAX_EMPLOYEES + " employees.");
    }

    /**
     * Retrieves the date of the start of the week.
     * @return The date of the start of the week
     */
    public int[] getStartWeek() { return startWeek; }

    /**
     * Retrieves the date of the end of the week.
     * @return The date of the end of the week
     */
    public int[] getEndWeek() { return endWeek; }

    /**
     * Retrieves the department of focus for the week schedule.
     * @return The department for the schedule
     */
    public String getDepartment() { return department; }

    /**
     * Retrieves the reference number for the department of focus for the schedule.
     * @return The reference number for the department
     */
    public int getDeptNum() { return deptNum; }

    /**
     * Breaks down the given week range of format XX/XX/XXXX - XX/XX/XXXX to parts: the
     * dates for start of the week and the end of the week.
     */
    private void parseWeekRange()
    {
        //these are the indices for start/end of month, day, and year
        int[] dateIndices = {0, 2, 3, 5, 6, 10};
        int[][] weeks = {startWeek, endWeek};
        int endWeekIndex = 13;
        int weekPointer = 0;

        //loops to populate the dates for the start week and end week
        for (int week = 0; week < weeks.length; week++)
        {
            int startIndex = 0;
            for (int i = 0; i < weeks[week].length; i++)
            {
                weeks[week][i] = Integer.parseInt(weekRange.substring(
                        dateIndices[startIndex] + weekPointer,
                        dateIndices[startIndex + 1] + weekPointer));
                startIndex += 2;
            }
            weekPointer += endWeekIndex;
        }
    }

    /**
     * Adds an employee to the employee list, if the employee list is not full.
     * @param employee An employee object with a schedule
     * @return True if an employee was added; false otherwise
     */
    public boolean addEmployee(Employee employee)
    {
        if (employeeList.length <= MAX_EMPLOYEES)
        {
            employeeList[numEmployees++] = employee;
            return true;
        }
        return false;
    }

    /**
     * Retrieves the list of employees in the week schedule.
     * @return The list of employees
     */
    public Employee[] getEmployeeList() {return employeeList; }

    /**
     * Retrieves the total number of work hours scheduled for a the schedule, which is
     * specific to a department of focus.
     * @return The total number of department hours
     */
    public double getTotalDeptHours()
    {
        double totalHours = 0;
        for (int employee = 0; employee < numEmployees; employee++)
        {
            totalHours+= employeeList[employee].getWeekDeptHours(deptNum);
        }
        return totalHours;
    }
}