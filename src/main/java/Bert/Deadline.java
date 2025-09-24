package Bert;

import java.time.LocalDate;

public class Deadline extends Task {
    protected LocalDate dueByDate;

    public Deadline(String description, LocalDate dueByDate) {
        super(description);
        setByDate(dueByDate);
    }

    public void setByDate(LocalDate dueByDate)
    {
        this.dueByDate=dueByDate;
    }

    @Override
    public String toString() {
        return "[D]" +super.toString() + " (by: " + dueByDate+ ")";
    }
}