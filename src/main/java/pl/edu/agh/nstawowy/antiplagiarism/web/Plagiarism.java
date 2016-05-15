package pl.edu.agh.nstawowy.antiplagiarism.web;


public class Plagiarism {
    private String name;
    private int commonLines;
    private int copiedFunctions;

    public Plagiarism() {
    }

    public Plagiarism(String name, int commonLines, int copiedFunctions) {
        this.name = name;
        this.commonLines = commonLines;
        this.copiedFunctions = copiedFunctions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCommonLines() {
        return commonLines;
    }

    public void setCommonLines(int commonLines) {
        this.commonLines = commonLines;
    }

    public int getCopiedFunctions() {
        return copiedFunctions;
    }

    public void setCopiedFunctions(int copiedFunctions) {
        this.copiedFunctions = copiedFunctions;
    }
}
