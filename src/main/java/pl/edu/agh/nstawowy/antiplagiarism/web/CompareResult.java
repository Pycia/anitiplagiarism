package pl.edu.agh.nstawowy.antiplagiarism.web;


public class CompareResult {
    private String code;

    public CompareResult(String code) {
        this.code = code;
    }

    public CompareResult() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
