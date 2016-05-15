package pl.edu.agh.nstawowy.antiplagiarism.js;


public class LineState {
    private String state;
    private String content;
    private String plagiate;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
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

    public LineState(String state, String content, String plagiate) {
        this.state = state;
        this.content = content;
        this.plagiate = plagiate;
    }
}


