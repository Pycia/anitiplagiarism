package pl.edu.agh.nstawowy.antiplagiarism.web;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


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
}