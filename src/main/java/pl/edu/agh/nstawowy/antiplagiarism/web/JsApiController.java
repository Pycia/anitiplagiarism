package pl.edu.agh.nstawowy.antiplagiarism.web;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Controller
@RequestMapping("/api/js")
@EnableAutoConfiguration
public class JsApiController {
    private static final Path JS_PATH = Paths.get("input", "js");

    private String compareContent;


    @RequestMapping("/library/list")
    @ResponseBody
    public String[] listJsLibrary() {
        return JS_PATH.toFile().list((dir, name) -> name.endsWith(".js"));
    }

    @RequestMapping(value = "/library/upload", method = RequestMethod.POST)
    public String uploadJsFile(@RequestBody UploadedFile uploadedFile) throws IOException {
        File file = JS_PATH.resolve(Paths.get(uploadedFile.getFilename())).toFile();
        FileUtils.writeStringToFile(file, uploadedFile.getContent());
        return "{}";
    }

    @RequestMapping("/library/delete")
    public String deleteJsFile(@RequestParam(name = "filename") String filename) throws IOException {
        File file = JS_PATH.resolve(filename).toFile();
        file.delete();
        return "{}";
    }

    @RequestMapping(value = "/findPlagiarism", method = RequestMethod.POST)
    @ResponseBody
    public String findPlagiarism(@RequestBody StringHolder content) {
        compareContent = content.getValue();
        return "{}";
    }
}