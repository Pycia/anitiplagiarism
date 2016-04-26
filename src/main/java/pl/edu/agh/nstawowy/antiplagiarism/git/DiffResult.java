package pl.edu.agh.nstawowy.antiplagiarism.git;

/**
 * Created by natalia on 26.04.16.
 */
public class DiffResult {
    public final int commonCharacters;
    public final int commonLines;
    public final int differentCharacters;
    public final int differentLines;

    public DiffResult(int commonCharacters, int commonLines, int differentCharacters, int differentLines) {
        this.commonCharacters = commonCharacters;
        this.commonLines = commonLines;
        this.differentCharacters = differentCharacters;
        this.differentLines = differentLines;
    }
}
