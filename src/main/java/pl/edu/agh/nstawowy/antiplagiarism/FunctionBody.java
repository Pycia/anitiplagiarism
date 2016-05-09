package pl.edu.agh.nstawowy.antiplagiarism;

public class FunctionBody {
    public final String body, minifiedBody;

    public FunctionBody(String body, String minifiedBody) {
        this.body = body;
        this.minifiedBody = minifiedBody;
    }
}
