package pl.edu.agh.nstawowy.antiplagiarism.js;


public class LineState {
    public enum LineCategory {
        OLD, NEW, MOD, DEL
    }
    private LineCategory state;
    private String content;
    private String plagiate;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LineCategory getCategory() {
        return state;
    }

    public void setState(LineCategory state) {
        this.state = state;
    }

    public String getPlagiate() {
        return plagiate;
    }

    public void setPlagiate(String plagiate) {
        this.plagiate = plagiate;
    }

    public LineState() {
    }

    public LineState(LineCategory state, String content, String plagiate) {
        this.state = state;
        this.content = content;
        this.plagiate = plagiate;
    }
}


