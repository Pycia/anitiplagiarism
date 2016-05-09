package pl.edu.agh.nstawowy.antiplagiarism;


import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

/**
 * Created by mjjan_000 on 07/05/2016.
 */
public class SimpleErrorReporter implements ErrorReporter {
    @Override
    public void warning(String s, String s1, int i, String s2, int i1) {

    }

    @Override
    public void error(String s, String s1, int i, String s2, int i1) {

    }

    @Override
    public EvaluatorException runtimeError(String s, String s1, int i, String s2, int i1) {
        return null;
    }
}
