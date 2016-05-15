package pl.edu.agh.nstawowy.antiplagiarism;

import org.springframework.boot.SpringApplication;
import pl.edu.agh.nstawowy.antiplagiarism.web.SpringConfig;

import java.io.IOException;

public class Main {

    public static void main(String... args) throws IOException {
        try {
            SpringApplication.run(SpringConfig.class, args);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
