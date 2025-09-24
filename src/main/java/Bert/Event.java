package Bert;

import java.time.LocalDate;

public class Event extends Task{
    protected LocalDate startTime;
    protected LocalDate endTime;

    public Event(String description,LocalDate startTime,LocalDate endTime) {
        super(description);
        setStartTime(startTime);
        setEndTime(endTime);
    }
    public void setStartTime(LocalDate startTime)
    {
        this.startTime=startTime;
    }
    public void setEndTime(LocalDate endTime)
    {
        this.endTime=endTime;
    }

    @Override
    public String toString() {
        return "[E]" +super.toString() + " (From:" + startTime + " --> To:"+endTime + ")";
    }
}
