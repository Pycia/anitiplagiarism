package pl.edu.agh.nstawowy.antiplagiarism.js;

public class FunctionBody {
    public final String body, minifiedBody;

    public FunctionBody(String body, String minifiedBody) {
        this.body = body;
        this.minifiedBody = minifiedBody;
    }

    @Override
    public String toString() {
        return "FunctionBody{" +
                "body='" + body + '\'' +
                ", minifiedBody='" + minifiedBody + '\'' +
                '}';
    }
}
