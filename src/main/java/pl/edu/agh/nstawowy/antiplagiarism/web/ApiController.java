package pl.edu.agh.nstawowy.antiplagiarism.web;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
@RequestMapping("/api")
@EnableAutoConfiguration
public class ApiController {
    private static final Path JS_PATH = Paths.get("input", "js");

    @RequestMapping("/test")
    @ResponseBody
    String test() {
        return "works!";
    }

    @RequestMapping("/js/library/list")
    @ResponseBody
    String[] listJsLibrary() {
        return JS_PATH.toFile().list((dir, name) -> name.endsWith(".js"));
    }

    @RequestMapping(value = "/js/library/upload", method = RequestMethod.POST)
    @ResponseBody
    String uploadJsFile(@RequestBody UploadedFile uploadedFile) throws IOException {
        File file = JS_PATH.resolve(Paths.get(uploadedFile.getFilename())).toFile();
        FileUtils.writeStringToFile(file, uploadedFile.getContent());
        return "{}";
    }
}