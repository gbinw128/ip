package Bert;

public class Event extends Task{
    protected String startTime;
    protected String endTime;

    public Event(String description,String startTime,String endTime) {
        super(description);
        setStartTime(startTime);
        setEndTime(endTime);
    }
    public void setStartTime(String startTime)
    {
        this.startTime=startTime;
    }
    public void setEndTime(String endTime)
    {
        this.endTime=endTime;
    }

    @Override
    public String toString() {
        return "[E]" +super.toString() + " (From:" + startTime + " --> To:"+endTime + ")";
    }
}
