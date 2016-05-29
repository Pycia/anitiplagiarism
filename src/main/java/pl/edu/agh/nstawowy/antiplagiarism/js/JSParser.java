package pl.edu.agh.nstawowy.antiplagiarism.js;


import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import jdk.nashorn.internal.ir.*;
import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.options.Options;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSParser {

    public Map<CodePlacement, FunctionBody> parse(String fileName) throws IOException {
        Map<CodePlacement, FunctionBody> functions = new HashMap<>();
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
        findFunctions(block.getStatements(), codeText, functions);
        return functions;
    }


    private void findFunctions(List<? extends Node> nodes, String code, Map<CodePlacement, FunctionBody> result) throws IOException {
        for (Node node : nodes) {
            if (node instanceof FunctionNode) {
                addFunction((FunctionNode)node, code, result);
                findFunctions(((FunctionNode) node).getBody().getStatements(), code, result);
            }

            if (node instanceof ExpressionStatement) {
                findFunctions(list(((ExpressionStatement) node).getExpression()), code,result);
            }
            if (node instanceof VarNode) {
                findFunctions(list(((VarNode) node).getInit()), code, result);
            }
            if (node instanceof CallNode) {
                findFunctions(list(((CallNode) node).getFunction()), code, result);
                findFunctions(((CallNode) node).getArgs(), code, result);
            }
            if (node instanceof ObjectNode) {
                findFunctions(((ObjectNode) node).getElements(), code, result);
            }
            if (node instanceof PropertyNode) {
                findFunctions(list(((PropertyNode) node).getValue()), code, result);
            }
        }
    }

    private void addFunction(FunctionNode statement, String code, Map<CodePlacement, FunctionBody> result) throws IOException {
        CodePlacement placement = new CodePlacement(statement.getStart(), statement.getFinish());

        String functionCode = code.substring(placement.startChar, placement.endChar);
        String functionHead = "function (" + Joiner.on(',').join(Lists.transform(statement.getParameters(), IdentNode::getName)) + ')';
        String theFunction = functionHead + functionCode;
        JavaScriptCompressor compressor = new JavaScriptCompressor(new StringReader(theFunction), new SimpleErrorReporter());
        Writer writer = new StringWriter();
        compressor.compress(writer, 200, true, false, false, false);
        String minimified = writer.toString();

        result.put(placement, new FunctionBody(functionHead + ' ' + functionCode, minimified));
    }

    private <T> List<T> list(T t) {
        return Collections.singletonList(t);
    }

}
