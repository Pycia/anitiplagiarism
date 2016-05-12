package pl.edu.agh.nstawowy.antiplagiarism.web;


public class ProbablePlagiarism {
    private String name;
    private int commonLines;
    private int totalLines;
    private int copiedFunctions;

    public ProbablePlagiarism() {
    }

    public ProbablePlagiarism(String name, int commonLines, int totalLines, int copiedFunctions) {
        this.name = name;
        this.commonLines = commonLines;
        this.totalLines = totalLines;
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

    public int getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }

    public int getCopiedFunctions() {
        return copiedFunctions;
    }

    public void setCopiedFunctions(int copiedFunctions) {
        this.copiedFunctions = copiedFunctions;
    }
}
