public class Deadline extends Task {
    protected String dueBy;
    public Deadline(String description,String dueByDate) {
        super(description);
        setBy(dueByDate);
    }
    public void setBy(String dueBy)
    {
        this.dueBy=dueBy;
    }
    public String toString() {
        return "[D]" +super.toString() + " (by: " +  dueBy + ")";
    }
}