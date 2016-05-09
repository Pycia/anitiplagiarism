package pl.edu.agh.nstawowy.antiplagiarism;


import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

public class SimpleErrorReporter implements ErrorReporter {
    @Override
    public void warning(String s, String s1, int i, String s2, int i1) {
        System.out.println("WARN: s: " + s + ", s1: " + s1 + ", i: " + i + ", s2: " + s2 + ", i1: " + i1);
    }

    @Override
    public void error(String s, String s1, int i, String s2, int i1) {
        System.out.println("ERRR: s: " + s + ", s1: " + s1 + ", i: " + i + ", s2: " + s2 + ", i1: " + i1);
    }

    @Override
    public EvaluatorException runtimeError(String s, String s1, int i, String s2, int i1) {
        return new EvaluatorException(s, s1, i, s2, i1);
    }
}
