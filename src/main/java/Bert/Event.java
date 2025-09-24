package Bert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task{
    protected LocalDateTime startDateTime;
    protected LocalDateTime endDateTime;

    public Event(String description,LocalDateTime startDateTime,LocalDateTime endDateTime) {
        super(description);
        setStartDateTime(startDateTime);
        setEndDateTime(endDateTime);
    }
    public void setStartDateTime(LocalDateTime startDateTime)
    {
        this.startDateTime=startDateTime;
    }
    public void setEndDateTime(LocalDateTime endDateTime)
    {
        this.endDateTime=endDateTime;
    }

    @Override
    public String toString() {
        return "[E]" +super.toString() + " (From:" + startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                + " --> To:" + endDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ")";
    }
}
