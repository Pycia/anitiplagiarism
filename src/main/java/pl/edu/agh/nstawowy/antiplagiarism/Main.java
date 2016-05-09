package pl.edu.agh.nstawowy.antiplagiarism;

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import jdk.nashorn.internal.ir.*;
import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.options.Options;
import sun.misc.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static Map<CodePlacement, FunctionBody> functions = new HashMap<>();

    private static <T> List<T> list(T t) {
        return Collections.singletonList(t);
    }

    public static void findFunctions(List<? extends Node> nodes, String code) throws IOException {
        for (Node node : nodes) {
            if (node instanceof FunctionNode) {
                addFunction(node, code);
                findFunctions(((FunctionNode) node).getBody().getStatements(), code);
            }

            if (node instanceof ExpressionStatement) {
                findFunctions(list(((ExpressionStatement) node).getExpression()), code);
            }
            if (node instanceof VarNode) {
                findFunctions(list(((VarNode) node).getInit()), code);
            }
            if (node instanceof CallNode) {
                findFunctions(list(((CallNode) node).getFunction()), code);
            }
            if (node instanceof ObjectNode) {
                findFunctions(((ObjectNode) node).getElements(), code);
            }
            if (node instanceof PropertyNode) {
                findFunctions(list(((PropertyNode) node).getValue()), code);
            }
        }
    }

    private static void addFunction(Node statement, String code) throws IOException {
        CodePlacement placement = new CodePlacement(statement.getStart(), statement.getFinish());
        String functionCode = code.substring(placement.startChar, placement.endChar);
        String functionLiteral = "function x()";
        JavaScriptCompressor compressor = new JavaScriptCompressor(new StringReader(functionLiteral + functionCode), new SimpleErrorReporter());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        compressor.compress(new OutputStreamWriter(out), 200, false, false, false, false);
        out.close();
        String minimified = new String(out.toByteArray());

        functions.put(placement, new FunctionBody(functionCode, minimified.substring(functionLiteral.length())));
    }

    public static void main(String... args) throws IOException {
        try {
            String fileName = "input/js/humanizeduration.js";
            String codeText = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);

            Options options = new Options("nashorn");
            options.set("anon.functions", true);
            options.set("parse.only", true);
            options.set("scripting", true);

            ErrorManager errors = new ErrorManager();
            Context context = new Context(options, errors, Thread.currentThread().getContextClassLoader());
            Source source = Source.sourceFor(fileName, codeText, false);
            Parser parser = new Parser(context.getEnv(), source, errors);
            FunctionNode functionNode = parser.parse();
            Block block = functionNode.getBody();
            List<Statement> statements = block.getStatements();
            findFunctions(statements, codeText);
            System.out.println(statements);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
