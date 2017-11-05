import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        WeeklyCalendar weeklyCalendar = new WeeklyCalendar();
        weeklyCalendar.addEntry(new WeeklyEntry("friday", "Sport"));
        weeklyCalendar.addEntry(new WeeklyEntry("monday", "Chill"));
        weeklyCalendar.addEntry(new WeeklyEntry("sunday", "PC"));
        weeklyCalendar.addEntry(new WeeklyEntry("tuesday", "Work"));

        weeklyCalendar.getWeeklySchedule().forEach(x -> System.out.println(x.toString()));

	/*
	Monday - Chill
	Tuesday - Work
	Friday - Sport
	Sunday - PC
	*/
    }
}

enum Weekday{  // Main enum, containing the days of the week
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    @Override
    public String toString() {  // Basic toString() method
        return this.name().substring(0, 1) + this.name().substring(1, this.name().length()).toLowerCase();
    }

}

class WeeklyEntry{
    public static Comparator<WeeklyEntry> BY_WEEKDAY = getComparatorByWeekday();  // The Collections.sort() method requires a comparator, so it can sort the days
    private Weekday weekday;
    private String notes;

    public WeeklyEntry(String weekday, String notes) {  // Basic constructor
        this.weekday = Weekday.valueOf(Weekday.class, weekday.toUpperCase());
        this.notes = notes;
    }

    @Override
    public String toString() {  // Normal toString() method
        return String.format("%s - %s", this.weekday, this.notes);
    }

    private static Comparator<WeeklyEntry> getComparatorByWeekday() {  // Here we return the comparator (Collections.sort() requires it)
        return (e1, e2) -> Integer.compare(e1.weekday.ordinal(), e2.weekday.ordinal());
    }
}

class WeeklyCalendar{
    private List<WeeklyEntry> weekdayList;

    public WeeklyCalendar() {
        this.weekdayList = new ArrayList<>();
    }

    public void addEntry(WeeklyEntry weeklyEntry){
        this.weekdayList.add(weeklyEntry);
    }

    public Iterable<WeeklyEntry> getWeeklySchedule(){
        Collections.sort(this.weekdayList, WeeklyEntry.BY_WEEKDAY);  // We sort it using the custom comparator...
        return Collections.unmodifiableList(this.weekdayList);  // and then we return it, so the user can iterat trought it
    }
}
