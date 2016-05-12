package pl.edu.agh.nstawowy.antiplagiarism;

import org.springframework.boot.SpringApplication;
import pl.edu.agh.nstawowy.antiplagiarism.web.SpringConfig;

import java.io.IOException;

public class Main {

    public static void main(String... args) throws IOException {
        try {
            SpringApplication.run(SpringConfig.class, args);
//            String fileName = "input/js/humanizeduration.js";
//            String fn2 ="input/js/humanizeduration2.js";
//
//            JSParser parser = new JSParser();
//            GitConnector gitConnector = new GitConnector();
//
//           DiffResult r = gitConnector.compare(Paths.get(fileName).toFile(), Paths.get(fn2).toFile());
//            System.out.println(r);
//            System.out.println(parser.parse(fileName));
//            System.out.println(parser.parse(fn2));
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
