package pl.edu.agh.nstawowy.antiplagiarism.git;


import pl.edu.agh.nstawowy.antiplagiarism.js.LineState;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GitConnector {
    public static final GitConnector INSTANCE = new GitConnector();


    public int commonLines(File a, File b) throws IOException, InterruptedException {
        Runtime r = Runtime.getRuntime();
        Process p = r.exec("git diff --no-prefix -U10000 " + a.getAbsolutePath() + " " + b.getAbsolutePath());
        p.waitFor();

        int result = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line = "";

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.startsWith(" ")) {
                    result += 1;
                }
            }
        }
        return result;
    }

    public List<LineState> annotate(File a, File b) throws IOException, InterruptedException {
        Runtime r = Runtime.getRuntime();
        Process p = r.exec("git diff --no-prefix -U10000 " + a.getAbsolutePath() + " " + b.getAbsolutePath());
        p.waitFor();


        List<LineState> list = new ArrayList<>();
        int minuses = 0;


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line = "";

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("---") || line.startsWith("+++") || line.startsWith("@@")) {
                    continue;
                }
                if (line.startsWith("-")) {
                    minuses += 1;
                } else {
                    if (line.startsWith(" ")) {
                        list.add(new LineState("old", line.substring(1), null));
                        minuses = 0;
                    }
                    if (line.startsWith("+")) {
                        if (minuses > 0) {
                            minuses -= 1;
                            list.add(new LineState("mod", line.substring(1), null));
                        } else {
                            list.add(new LineState("new", line.substring(1), null));
                        }
                    }
                }
            }
        }

        return list;
    }


    private GitConnector() {

    }
}
