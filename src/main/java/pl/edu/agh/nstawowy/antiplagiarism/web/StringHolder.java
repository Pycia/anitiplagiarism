package pl.edu.agh.nstawowy.antiplagiarism.web;


public class StringHolder {
    private String value;

    public StringHolder(String value) {
        this.value = value;
    }

    public StringHolder() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
