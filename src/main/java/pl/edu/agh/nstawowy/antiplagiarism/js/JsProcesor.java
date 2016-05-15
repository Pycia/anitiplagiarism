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
        File aFile = File.createTempFile("makota",".js");
        Files.write(source, aFile, Charset.defaultCharset());

        int commonLines = GitConnector.INSTANCE.compare(aFile, bFile).commonLines;

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
}