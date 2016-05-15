package pl.edu.agh.nstawowy.antiplagiarism.js;

import com.google.common.io.Files;
import pl.edu.agh.nstawowy.antiplagiarism.git.GitConnector;
import pl.edu.agh.nstawowy.antiplagiarism.web.Plagiarism;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class JsProcesor {
    private static final Path JS_PATH = Paths.get("input", "js");
    public static JsProcesor INSTANCE = new JsProcesor();

    public Plagiarism[] findPlagiarisms(String source) throws IOException, InterruptedException {
        File[] files = JS_PATH.toFile().listFiles((dir, name) -> name.endsWith(".js"));
        Plagiarism[] res = new Plagiarism[files.length];

        for (int i = 0; i < files.length; ++i){
            res[i] = compareRoughly(source, files[i]);
        }
        return res;
    }

    private Plagiarism compareRoughly(String source, File bFile) throws IOException, InterruptedException {
        File aFile = File.createTempFile("antiplagiarism",".js");
        Files.write(source, aFile, Charset.defaultCharset());

        int commonLines = GitConnector.INSTANCE.commonLines(aFile, bFile);

        return new Plagiarism(bFile.getName(), commonLines, findCopies(aFile, bFile).size());
    }

    private List<Copy> findCopies(File aFile, File bFile) throws IOException {

        JSParser parser = new JSParser();
        Map<CodePlacement, FunctionBody> aMap = parser.parse(aFile.getAbsolutePath());
        Map<CodePlacement, FunctionBody> bMap = parser.parse(bFile.getAbsolutePath());

        List<Copy> result = new ArrayList<>();

        aMap.forEach((aPlacement, aBody) -> {
            bMap.forEach((bPlacement, bBody) -> {
                if(Objects.equals(aBody.minifiedBody, bBody.minifiedBody) &&
                        !Objects.equals(aBody.body, bBody.body)) {
                    result.add(new Copy(aPlacement, bPlacement, aBody.body, bBody.body));
                }
            });
        });

        return result;
    }

    private JsProcesor() {

    }

    public String annotate(File bfile, String aContent) throws IOException, InterruptedException {
        File aFile = File.createTempFile("antiplagiarism",".js");
        Files.write(aContent, aFile, Charset.defaultCharset());

        String bContent = Files.toString(bfile, Charset.defaultCharset());

        List<LineState> states = GitConnector.INSTANCE.annotate(aFile, bfile);
        List<Copy> copies = findCopies(aFile, bfile);
        for (Copy copy: copies) {
            int startLine = countLines(bContent.substring(0, copy.bPlacement.startChar));
            int endLine = countLines(bContent.substring(0, copy.bPlacement.endChar));
            for (int i = startLine; i < endLine && i < states.size(); ++i) {
                states.get(i).setPlagiate(copy.aBody.replaceAll("\\n", "&#10;"));
            }
        }

        StringBuilder sb = new StringBuilder();


        for (LineState state : states) {
            if ("old".equals(state.getState())) {
                sb.append(state.getContent());
            } else if ("new".equals(state.getState())) {
                sb.append("<span style=\"background:#ADFF2F\">").append(state.getContent()).append("</span>");
            } else if ("mod".equals(state.getState())) {
                sb.append("<span style=\"background:#00BFFF\">").append(state.getContent()).append("</span>");
            }
            sb.append("\n");

        }

        return sb.toString();
    }

    public static int countLines(String str) {
        if(str == null || str.isEmpty())
        {
            return 0;
        }
        int lines = 1;
        int pos = 0;
        while ((pos = str.indexOf("\n", pos) + 1) != 0) {
            lines++;
        }
        return lines;
    }
}
