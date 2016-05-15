package pl.edu.agh.nstawowy.antiplagiarism.git;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class GitConnector {
    public static final GitConnector INSTANCE = new GitConnector();

    public DiffResult compare(File a, File b) throws IOException, InterruptedException {
        Runtime r = Runtime.getRuntime();
        Process p = r.exec("git diff " + a.getAbsolutePath() + " " + b.getAbsolutePath());
        p.waitFor();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line = "";

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        return new DiffResult(0, 0, 0, 0);
    }

    public int commonLines(File a, File b) throws IOException, InterruptedException {
        Runtime r = Runtime.getRuntime();
        Process p = r.exec("git diff " + a.getAbsolutePath() + " " + b.getAbsolutePath());
        p.waitFor();

        int result = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line = "";

            while ((line = reader.readLine()) != null) {
                if (line.startsWith(" ")) {
                    result += 1;
                }
            }
        }
        return result;
    }


    private GitConnector() {

    }
}
