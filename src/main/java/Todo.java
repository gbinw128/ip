public class Todo extends Task{
    private Boolean isDone=false;
    public Todo(String description,boolean isDone) {
        super(description);
        this.isDone = isDone;
    }
    public Todo(String description) {
        super(description);
    }
    public boolean isDone() {
        return isDone;
    }
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }
    public String toString() {
        return "[T]" +super.toString();
    }
}