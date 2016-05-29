package pl.edu.agh.nstawowy.antiplagiarism.web;


public class CompareResult {
    private String code;
    private String original;

    public CompareResult(String code, String original) {
        this.code = code;
        this.original= original;
    }

    public CompareResult() {
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
