package pl.edu.agh.nstawowy.antiplagiarism;

public class CodePlacement {
    public final int startChar, endChar;

    public CodePlacement(int startChar, int endChar) {
        this.startChar = startChar;
        this.endChar = endChar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodePlacement placement = (CodePlacement) o;

        return startChar == placement.startChar && endChar == placement.endChar;

    }

    @Override
    public int hashCode() {
        int result = startChar;
        result = 31 * result + endChar;
        return result;
    }
}
