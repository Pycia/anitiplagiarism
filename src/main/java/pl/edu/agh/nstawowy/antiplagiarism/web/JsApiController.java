package pl.edu.agh.nstawowy.antiplagiarism.web;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.nstawowy.antiplagiarism.js.JsProcesor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Controller
@RequestMapping("/api/js")
@EnableAutoConfiguration
public class JsApiController {
    private static final Path JS_PATH = Paths.get("input", "js");



    @RequestMapping("/library/list")
    @ResponseBody
    public String[] listJsLibrary() {
        return JS_PATH.toFile().list((dir, name) -> name.endsWith(".js"));
    }

    @RequestMapping(value = "/library/upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadJsFile(@RequestBody UploadedFile uploadedFile) throws IOException {
        File file = JS_PATH.resolve(Paths.get(uploadedFile.getFilename())).toFile();
        FileUtils.writeStringToFile(file, uploadedFile.getContent());
        return "{}";
    }

    @RequestMapping("/library/delete")
    @ResponseBody
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

    @RequestMapping("/pastedContent")
    @ResponseBody
    public String pastedCode() throws IOException {
        return "{\"code\":\"" +escapeJs(compareContent)+"\"}";
    }

    @RequestMapping("/listPlagiarisms")
    @ResponseBody
    public Plagiarism[] listPlagiarisms() throws IOException, InterruptedException {
        return JsProcesor.INSTANCE.findPlagiarisms(compareContent);
    }

    @RequestMapping("/compare")
    @ResponseBody
    public CompareResult compare(@RequestParam(name = "filename") String filename) throws IOException, InterruptedException {
        File file = JS_PATH.resolve(filename).toFile();
        String source = Files.toString(file, Charset.defaultCharset());

        return JsProcesor.INSTANCE.annotate(file, compareContent);
    }

    private String escapeJs(String code) {
        return StringEscapeUtils.escapeEcmaScript(code)
                .replaceAll("\\n","\\n")
                .replaceAll("\\\\'", "\\\\u0027");
    }




    private String compareContent = "var express = require('express')\n" +
            "var app = express()\n" +
            "\n" +
            "app.set('port', (process.env.PORT || 4000))\n" +
            "app.use(express.static(__dirname + '/public'))\n" +
            "app.get('/', function(request, response) {\n" +
            "  response.send('Hello World!')\n" +
            "})\n" +
            "\n" +
            "app.listen(app.get('port'), function() {\n" +
            "//alama kota\n" +
            "  console.log(\"Node app is running at localhost:\" + app.get('port'))\n" +
            "})\n" +
            "        ";

}