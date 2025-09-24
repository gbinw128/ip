package Bert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime dueByDateTime;

    public Deadline(String description, LocalDateTime dueByDateTime) {
        super(description);
        setByDate(dueByDateTime);
    }

    public void setByDate(LocalDateTime dueByDateTime)
    {
        this.dueByDateTime=dueByDateTime;
    }

    @Override
    public String toString() {
        return "[D]" +super.toString() + " (by: " + dueByDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))+ ")";
    }
}