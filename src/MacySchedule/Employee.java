package MacySchedule;

/**
 * This is a class for a Macy's employee. The employee will have a full name and a work
 * day schedule. If a name is not available, expected input for the string is a blank
 * string. This was designed to match Macy's current policies for work where it is not
 * possible for an employee to have two work schedules in a single day.
 *
 * Version 1.1: Fixes the calculation to remove meal hours from calculated total hours
 * in the inner class WorkDay since meal hours are unpaid. Based on Macy's California
 * policy, as checked on Nov 27, 2018, minimum 5 hours work requires a 30 min meal
 * period while minimum of 7.5 hours requires a 1 hour (in total) meal period.
 *
 * @author Lisa Chen
 * @since 27Nov18
 * @version 1.1
 */
public class Employee
{
    private String firstName;
    private String lastName;
    private String middleName;
    private WorkDay[] schedule;
    private int scheduled; //not used for manual scheduled work days
    private final int WEEK_LENGTH = 7;
    private final int INVALID_DEPT = -1;
    private final String HOUR_SEPARATOR = " - ";
    private final String NO_HOURS_SCHEDULED = "";

    /**
     * Constructs an employee with his/her name.
     * @param firstName Employee's first name
     * @param lastName Employee's last name
     * @param middleName Employee's middle name (blank string if N/A)
     */
    public Employee(String firstName, String lastName, String middleName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        schedule = new WorkDay[WEEK_LENGTH];
        scheduled = 0;
    }

    /**
     * Retrieves the employee's first name.
     * @return Employee's first name
     */
    public String getFirstName() { return firstName; }

    /**
     * Retrieves the employee's middle name.
     * @return Employee's middle name
     */
    public String getMiddleName() { return middleName; }

    /**
     * Retrieves the employee's last name.
     * @return Employee's first name
     */
    public String getLastName() { return lastName; }

    /**
     * Adds a day work schedule with a given department, department number, and range of
     * hours for the schedule to the employee's week schedule. The addition is at
     * the first index of the 7-day week. This is not used to manually place a schedule
     * in the middle of the week or to change an existing schedule.
     * @param department The department the work schedule is for
     * @param deptNum The department number representing the department
     * @param hours The range of hours for the schedule in format XX:XXx - XX:XXx
     */
    public void addSeqSchedule(String department, int deptNum, String hours)
    {
        if (scheduled <WEEK_LENGTH)
        {
            WorkDay workDay = new WorkDay(department, deptNum, hours);
            schedule[scheduled++] = workDay;
        }
        else
            throw new IndexOutOfBoundsException("Attempted to schedule more than " +
                    "7 days in a week");
    }

    /**
     * Adds a day work schedule with a given note about the day. The addition is at
     * the first index of the 7-day week. This is not used to manually place a schedule
     * in the middle of the week or to change an existing schedule. This is only
     * designed to be used when there is no department associated with the day.
     * @param note Note about the schedule for the particular day
     */
    public void addSeqSchedule(String note)
    {
        if (scheduled < WEEK_LENGTH)
        {
            WorkDay workDay = new WorkDay(note);
            schedule[scheduled++] = workDay;
        }
        else
            throw new IndexOutOfBoundsException("Attempted to schedule more than " +
                    "7 days in a week");
    }


    /**
     * Retrieves the work schedule for the given day. The schedule includes what
     * department it is for and the scheduled hours. The department information is given
     * with both the department number and the name of the department with a new line
     * separating the two information.
     * @param day The day to retrieve the work schedule for
     * @return The work schedule in the format {department information, hours scheduled}
     */
    public String[] getDaySchedule(int day)
    {
        String department = schedule[day].department;
        String hour = NO_HOURS_SCHEDULED;
        int deptNum = schedule[day].deptNum;
        //default is no schedule - change default if valid dept which means valid schedule
        if (deptNum > INVALID_DEPT)
        {
            //department information is connected together with a \n spacing
            department = deptNum + "\n" + department;
            hour = schedule[day].getStartTime() + HOUR_SEPARATOR +
                    schedule[day].getEndTime();
        }
        String[] result = {department, hour};
        return result;
    }

    /**
     * Changes the work schedule for a specified day as long as the day already has
     * an existing schedule with a valid department. This keeps the current department
     * for that existing work schedule when changing the schedule.
     * @param day The given day to change the schedule
     * @param hours The new hours for the schedule in format XX:XXx - XX:XXx
     */
    public void changeDayScheduledHours(int day, String hours)
    {
        if (schedule[day].deptNum != INVALID_DEPT)
            schedule[day].setHours(hours);
        else
            throw new UnsupportedOperationException("The day requested does not have " +
                    "an existing work schedule with a valid department.");
    }

    /**
     * Changes the work schedule for a specific day with a given department, its
     * reference number, and the range of hours for the schedule.
     * @param day The specified day to change the work schedule
     * @param dept The description of the department for the schedule
     * @param deptNum The reference number for the department
     * @param hours The new hours for the schedule in format XX:XXx - XX:XXx
     */
    public void addHoursAtDay(int day, String dept, int deptNum, String hours)
    {
        if (schedule[day] == null || schedule[day].deptNum == INVALID_DEPT)
            scheduled++;
        schedule[day] = new WorkDay(dept, deptNum, hours);
    }

    /**
     * Retrieves the total work hours for a given day.
     * @param day The day to calculate the total work hours
     * @return The total work hours
     */
    public double getDayTotalHours (int day)
    {
        return schedule[day].getTotalHours();
    }

    /**
     * Retrieves the total work hours for the week, regardless of the departments
     * scheduled for the employee.
     * @return The total work hours
     */
    public double getWeekTotalHours()
    {
        double totalHours = 0;
        for (int i = 0; i < WEEK_LENGTH; i++)
        {
            if (schedule[i] != null)
                totalHours += getDayTotalHours(i);
        }
        return totalHours;
    }

    /**
     * Retrieves the total work hours for the week for a specified department.
     * @param deptNum The reference number for the department
     * @return The total work hours for a specified department
     */
    public double getWeekDeptHours(int deptNum)
    {
        double deptHours = 0;
        for (int i = 0; i < WEEK_LENGTH; i++)
        {
            WorkDay day = schedule[i];
            if (day != null && deptNum == day.deptNum)
                deptHours += getDayTotalHours(i);
        }
        return deptHours;
    }

    /**
     * This is a class for a work day of an employee. The work day has a department (or
     * a note/blank if there is no department), a department number used as a reference
     * to the department, and a range of work hours for the day.
     */
    class WorkDay
    {
        private String department;
        private int deptNum;
        Time startTime;
        Time endTime;
        double totalHours;
        private final double DAY_HOURS = 24;
        private final double FULL_MEAL_TIME = 1; //1 hour meal
        private final double MIN_WORKED_FOR_FULL_MEAL = 7.5; //in hours
        private final double MIN_WORKED_FOR_HALF_MEAL = 5; //in hours

        /**
         * Constructs a work day schedule with an associated department, the number used
         * as a reference to the department, and the range of work hours.
         * @param department The description of the department for the schedule
         * @param deptNum The reference number for the department
         * @param hours The range of hours for the schedule in format XX:XXx - XX:XXx
         */
        public WorkDay(String department, int deptNum, String hours)
        {
            this.department = department;
            this.deptNum = deptNum;
            setHours(hours);
        }

        /**
         * Constructs a work day schedule with an associated department, the number used
         * as a reference to the department, the work hours start time, and the work
         * hours end time.
         * @param department The description of the department for the schedule
         * @param deptNum The reference number for the department
         * @param startTime The time the work day starts
         * @param endTime The time the work day ends
         */
        public WorkDay(String department, int deptNum, Time startTime, Time endTime)
        {
            this.department = department;
            this.deptNum = deptNum;
            setHours(startTime, endTime);
        }

        /**
         * Constructs a work day schedule with a given note about the day. Intended to
         * only be used when there is no department associated with the work day object.
         * @param note Note about the schedule for the particular day
         */
        public WorkDay(String note)
        {
            this(note, INVALID_DEPT, NO_HOURS_SCHEDULED );
        }

        /**
         * Sets the range of work hours for the work day. Expected inputs are either
         * hours or matches NO_HOURS_SCHEDULED if there is no hours assigned.
         * @param hours The range of hours for the schedule in format XX:XXx - XX:XXx
         */
        public void setHours(String hours)
        {
            if (hours.equals(NO_HOURS_SCHEDULED))
            {
                totalHours = 0;
                startTime = null;
                endTime = null;
            }
            else
            {
                parseHours(hours);
                calcTotalHours();
            }
        }

        /**
         * Sets the range of work hours for the work day with a given start and end
         * time.
         * @param startTime The start hour for the work day
         * @param endTime The end hour for the work day
         */
        public void setHours(double startTime, double endTime)
        {
            //invalid time given to denote no work hours
            if (startTime < 0) //if startTime is negative, endTime should also be
            {
                totalHours = 0;
                this.startTime = null;
                this.endTime = null;
            }
            else
            {
                this.startTime = new Time(startTime);
                this.endTime = new Time(endTime);
                calcTotalHours();
            }
        }

        /**
         * Sets the range of work hours for the work day with a given start and end
         * time.
         * @param startTime The start hour for the work day
         * @param endTime The end hour for the work day
         */
        public void setHours(Time startTime, Time endTime)
        {
            if (startTime == null) //if one is null, both should be null
            {
                totalHours = 0;
                this.startTime = null;
                this.endTime = null;
            }
            else
            {
                this.startTime = startTime;
                this.endTime = endTime;
                calcTotalHours();
            }
        }

        /**
         * Converts a range of hours in String format to the appropriate object format.
         * @param hours The range of hours in String format
         */
        private void parseHours(String hours)
        {
            int separatorIndex = hours.indexOf(HOUR_SEPARATOR);
            String firstHour = hours.substring(0, separatorIndex);
            String lastHour = hours.substring(separatorIndex + HOUR_SEPARATOR.length());
            startTime = new Time(firstHour);
            endTime = new Time(lastHour);
        }

        /**
         * Calculates the total number of hours scheduled for the work day. Subtracts
         * the meal breaks as defined by company policies as they are unpaid.
         */
        private void calcTotalHours()
        {
            double startTime = this.startTime.getDecimalTime();
            double endTime = this.endTime.getDecimalTime();
            if (endTime <= startTime)
                endTime += DAY_HOURS;
            totalHours = endTime - startTime;
            //accounts for Macy's policy for meals by subtracting unpaid meal hours
            if (totalHours >= MIN_WORKED_FOR_FULL_MEAL)
                totalHours -= FULL_MEAL_TIME;
            else if (totalHours >= MIN_WORKED_FOR_HALF_MEAL)
                totalHours -= FULL_MEAL_TIME / 2.0;
        }

        /**
         * Retrieves the start time for the work day.
         * @return The start time in String format
         */
        public String getStartTime()
        {
            if (startTime == null)
                return NO_HOURS_SCHEDULED;
            return startTime.getStrTime();
        }

        /**
         * Retrieves the end time for the work day.
         * @return The end time in String format
         */
        public String getEndTime()
        {
            if (endTime == null)
                return NO_HOURS_SCHEDULED;
            return endTime.getStrTime();
        }

        /**
         * Retrieves the total number of work hours scheduled for the work day.
         * @return Total number of work hours scheduled
         */
        public double getTotalHours() { return totalHours; }
    }
}