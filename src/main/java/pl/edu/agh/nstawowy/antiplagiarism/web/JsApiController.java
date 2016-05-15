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
    public CompareResult compare(@RequestParam(name = "filename") String filename) throws IOException {
        File file = JS_PATH.resolve(filename).toFile();
        String source = Files.toString(file, Charset.defaultCharset());
        return new CompareResult(source);
    }

    private String escapeJs(String code) {
        return StringEscapeUtils.escapeEcmaScript(code)
                .replaceAll("\\n","\\n")
                .replaceAll("\\\\'", "\\\\u0027");
    }




    private String compareContent = "// HumanizeDuration.js - http://git.io/j0HgmQ\n" +
            "\n" +
            "(function() {\n" +
            "\n" +
            "  var UNITS = {\n" +
            "    y: 31557600000,\n" +
            "    mo: 2629800000,\n" +
            "    w: 604800000,\n" +
            "    d: 86400000,\n" +
            "    h: 3600000,\n" +
            "    m: 60000,\n" +
            "    s: 1000,\n" +
            "    ms: 1\n" +
            "  };\n" +
            "\n" +
            "  var languages = {\n" +
            "    ar: {\n" +
            "      y: function(c) { return ((c === 1) ? \"سنة\" : \"سنوات\"); },\n" +
            "      mo: function(c) { return ((c === 1) ? \"شهر\" : \"أشهر\"); },\n" +
            "      w: function(c) { return ((c === 1) ? \"أسبوع\" : \"أسابيع\"); },\n" +
            "      d: function(c) { return ((c === 1) ? \"يوم\" : \"أيام\"); },\n" +
            "      h: function(c) { return ((c === 1) ? \"ساعة\" : \"ساعات\"); },\n" +
            "      m: function(c) { return ((c === 1) ? \"دقيقة\" : \"دقائق\"); },\n" +
            "      s: function(c) { return ((c === 1) ? \"ثانية\" : \"ثواني\"); },\n" +
            "      ms: function(c) { return ((c === 1) ? \"جزء من الثانية\" : \"أجزاء من الثانية\"); }\n" +
            "    },\n" +
            "    ca: {\n" +
            "      y: function(c) { return \"any\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      mo: function(c) { return \"mes\" + ((c !== 1) ? \"os\" : \"\"); },\n" +
            "      w: function(c) { return \"setman\" + ((c !== 1) ? \"es\" : \"a\"); },\n" +
            "      d: function(c) { return \"di\" + ((c !== 1) ? \"es\" : \"a\"); },\n" +
            "      h: function(c) { return \"hor\" + ((c !== 1) ? \"es\" : \"a\"); },\n" +
            "      m: function(c) { return \"minut\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      s: function(c) { return \"segon\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      ms: function(c) { return \"milisegon\" + ((c !== 1) ? \"s\" : \"\" ); }\n" +
            "    },\n" +
            "    da: {\n" +
            "      y: \"år\",\n" +
            "      mo: function(c) { return \"måned\" + ((c !== 1) ? \"er\" : \"\"); },\n" +
            "      w: function(c) { return \"uge\" + ((c !== 1) ? \"r\" : \"\"); },\n" +
            "      d: function(c) { return \"dag\" + ((c !== 1) ? \"e\" : \"\"); },\n" +
            "      h: function(c) { return \"time\" + ((c !== 1) ? \"r\" : \"\"); },\n" +
            "      m: function(c) { return \"minut\" + ((c !== 1) ? \"ter\" : \"\"); },\n" +
            "      s: function(c) { return \"sekund\" + ((c !== 1) ? \"er\" : \"\"); },\n" +
            "      ms: function(c) { return \"millisekund\" + ((c !== 1) ? \"er\" : \"\"); }\n" +
            "    },\n" +
            "    de: {\n" +
            "      y: function(c) { return \"Jahr\" + ((c !== 1) ? \"e\" : \"\"); },\n" +
            "      mo: function(c) { return \"Monat\" + ((c !== 1) ? \"e\" : \"\"); },\n" +
            "      w: function(c) { return \"Woche\" + ((c !== 1) ? \"n\" : \"\"); },\n" +
            "      d: function(c) { return \"Tag\" + ((c !== 1) ? \"e\" : \"\"); },\n" +
            "      h: function(c) { return \"Stunde\" + ((c !== 1) ? \"n\" : \"\"); },\n" +
            "      m: function(c) { return \"Minute\" + ((c !== 1) ? \"n\" : \"\"); },\n" +
            "      s: function(c) { return \"Sekunde\" + ((c !== 1) ? \"n\" : \"\"); },\n" +
            "      ms: function(c) { return \"Millisekunde\" + ((c !== 1) ? \"n\" : \"\"); }\n" +
            "    },\n" +
            "    en: {\n" +
            "      y: function(c) { return \"year\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      mo: function(c) { return \"month\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      w: function(c) { return \"week\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      d: function(c) { return \"day\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      h: function(c) { return \"hour\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      m: function(c) { return \"minute\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      s: function(c) { return \"second\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      ms: function(c) { return \"millisecond\" + ((c !== 1) ? \"s\" : \"\"); }\n" +
            "    },\n" +
            "    es: {\n" +
            "      y: function(c) { return \"año\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      mo: function(c) { return \"mes\" + ((c !== 1) ? \"es\" : \"\"); },\n" +
            "      w: function(c) { return \"semana\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      d: function(c) { return \"día\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      h: function(c) { return \"hora\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      m: function(c) { return \"minuto\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      s: function(c) { return \"segundo\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      ms: function(c) { return \"milisegundo\" + ((c !== 1) ? \"s\" : \"\" ); }\n" +
            "    },\n" +
            "    fr: {\n" +
            "      y: function(c) { return \"an\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      mo: \"mois\",\n" +
            "      w: function(c) { return \"semaine\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      d: function(c) { return \"jour\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      h: function(c) { return \"heure\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      m: function(c) { return \"minute\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      s: function(c) { return \"seconde\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      ms: function(c) { return \"milliseconde\" + ((c !== 1) ? \"s\" : \"\"); }\n" +
            "    },\n" +
            "    hu: {\n" +
            "      y: \"év\",\n" +
            "      mo: \"hónap\",\n" +
            "      w: \"hét\",\n" +
            "      d: \"nap\",\n" +
            "      h: \"óra\",\n" +
            "      m: \"perc\",\n" +
            "      s: \"másodperc\",\n" +
            "      ms: \"ezredmásodperc\"\n" +
            "    },\n" +
            "    it: {\n" +
            "      y: function(c) { return \"ann\" + ((c !== 1) ? \"i\" : \"o\"); },\n" +
            "      mo: function(c) { return \"mes\" + ((c !== 1) ? \"i\" : \"e\"); },\n" +
            "      w: function(c) { return \"settiman\" + ((c !== 1) ? \"e\" : \"a\"); },\n" +
            "      d: function(c) { return \"giorn\" + ((c !== 1) ? \"i\" : \"o\"); },\n" +
            "      h: function(c) { return \"or\" + ((c !== 1) ? \"e\" : \"a\"); },\n" +
            "      m: function(c) { return \"minut\" + ((c !== 1) ? \"i\" : \"o\"); },\n" +
            "      s: function(c) { return \"second\" + ((c !== 1) ? \"i\" : \"o\"); },\n" +
            "      ms: function(c) { return \"millisecond\" + ((c !== 1) ? \"i\" : \"o\" ); }\n" +
            "    },\n" +
            "    ja: {\n" +
            "      y: \"年\",\n" +
            "      mo: \"月\",\n" +
            "      w: \"週\",\n" +
            "      d: \"日\",\n" +
            "      h: \"時間\",\n" +
            "      m: \"分\",\n" +
            "      s: \"秒\",\n" +
            "      ms: \"ミリ秒\"\n" +
            "    },\n" +
            "    ko: {\n" +
            "      y: \"년\",\n" +
            "      mo: \"개월\",\n" +
            "      w: \"주일\",\n" +
            "      d: \"일\",\n" +
            "      h: \"시간\",\n" +
            "      m: \"분\",\n" +
            "      s: \"초\",\n" +
            "      ms: \"밀리 초\"\n" +
            "    },\n" +
            "    nl: {\n" +
            "      y: \"jaar\",\n" +
            "      mo: function(c) { return (c === 1) ? \"maand\" : \"maanden\"; },\n" +
            "      w: function(c) { return (c === 1) ? \"week\" : \"weken\"; },\n" +
            "      d: function(c) { return (c === 1) ? \"dag\" : \"dagen\"; },\n" +
            "      h: \"uur\",\n" +
            "      m: function(c) { return (c === 1) ? \"minuut\" : \"minuten\"; },\n" +
            "      s: function(c) { return (c === 1) ? \"seconde\" : \"seconden\"; },\n" +
            "      ms: function(c) { return (c === 1) ? \"milliseconde\" : \"milliseconden\"; }\n" +
            "    },\n" +
            "    no: {\n" +
            "      y: \"år\",\n" +
            "      mo: function(c) { return \"måned\" + ((c !== 1) ? \"er\" : \"\"); },\n" +
            "      w: function(c) { return \"uke\" + ((c !== 1) ? \"r\" : \"\"); },\n" +
            "      d: function(c) { return \"dag\" + ((c !== 1) ? \"er\" : \"\"); },\n" +
            "      h: function(c) { return \"time\" + ((c !== 1) ? \"r\" : \"\"); },\n" +
            "      m: function(c) { return \"minutt\" + ((c !== 1) ? \"er\" : \"\"); },\n" +
            "      s: function(c) { return \"sekund\" + ((c !== 1) ? \"er\" : \"\"); },\n" +
            "      ms: function(c) { return \"millisekund\" + ((c !== 1) ? \"er\" : \"\"); }\n" +
            "    },\n" +
            "    pl: {\n" +
            "      y: function(c) { return [\"rok\", \"roku\", \"lata\", \"lat\"][getPolishForm(c)]; },\n" +
            "      mo: function(c) { return [\"miesiąc\", \"miesiąca\", \"miesiące\", \"miesięcy\"][getPolishForm(c)]; },\n" +
            "      w: function(c) { return [\"tydzień\", \"tygodnia\", \"tygodnie\", \"tygodni\"][getPolishForm(c)]; },\n" +
            "      d: function(c) { return [\"dzień\", \"dnia\", \"dni\", \"dni\"][getPolishForm(c)]; },\n" +
            "      h: function(c) { return [\"godzina\", \"godziny\", \"godziny\", \"godzin\"][getPolishForm(c)]; },\n" +
            "      m: function(c) { return [\"minuta\", \"minuty\", \"minuty\", \"minut\"][getPolishForm(c)]; },\n" +
            "      s: function(c) { return [\"sekunda\", \"sekundy\", \"sekundy\", \"sekund\"][getPolishForm(c)]; },\n" +
            "      ms: function(c) { return [\"milisekunda\", \"milisekundy\", \"milisekundy\", \"milisekund\"][getPolishForm(c)]; }\n" +
            "    },\n" +
            "    pt: {\n" +
            "      y: function(c) { return \"ano\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      mo: function(c) { return (c !== 1) ? \"meses\" : \"mês\"; },\n" +
            "      w: function(c) { return \"semana\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      d: function(c) { return \"dia\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      h: function(c) { return \"hora\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      m: function(c) { return \"minuto\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      s: function(c) { return \"segundo\" + ((c !== 1) ? \"s\" : \"\"); },\n" +
            "      ms: function(c) { return \"milissegundo\" + ((c !== 1) ? \"s\" : \"\"); }\n" +
            "    },\n" +
            "    ru: {\n" +
            "      y: function(c) { return [\"лет\", \"год\", \"года\"][getRussianForm(c)]; },\n" +
            "      mo: function(c) { return [\"месяцев\", \"месяц\", \"месяца\"][getRussianForm(c)]; },\n" +
            "      w: function(c) { return [\"недель\", \"неделя\", \"недели\"][getRussianForm(c)]; },\n" +
            "      d: function(c) { return [\"дней\", \"день\", \"дня\"][getRussianForm(c)]; },\n" +
            "      h: function(c) { return [\"часов\", \"час\", \"часа\"][getRussianForm(c)]; },\n" +
            "      m: function(c) { return [\"минут\", \"минута\", \"минуты\"][getRussianForm(c)]; },\n" +
            "      s: function(c) { return [\"секунд\", \"секунда\", \"секунды\"][getRussianForm(c)]; },\n" +
            "      ms: function(c) { return [\"миллисекунд\", \"миллисекунда\", \"миллисекунды\"][getRussianForm(c)]; }\n" +
            "    },\n" +
            "    sv: {\n" +
            "      y: \"år\",\n" +
            "      mo: function(c) { return \"månad\" + ((c !== 1) ? \"er\" : \"\"); },\n" +
            "      w: function(c) { return \"veck\" + ((c !== 1) ? \"or\" : \"a\"); },\n" +
            "      d: function(c) { return \"dag\" + ((c !== 1) ? \"ar\" : \"\"); },\n" +
            "      h: function(c) { return \"timm\" + ((c !== 1) ? \"ar\" : \"e\"); },\n" +
            "      m: function(c) { return \"minut\" + ((c !== 1) ? \"er\" : \"\"); },\n" +
            "      s: function(c) { return \"sekund\" + ((c !== 1) ? \"er\" : \"\"); },\n" +
            "      ms: function(c) { return \"millisekund\" + ((c !== 1) ? \"er\" : \"\"); }\n" +
            "    },\n" +
            "    tr: {\n" +
            "      y: \"yıl\",\n" +
            "      mo: \"ay\",\n" +
            "      w: \"hafta\",\n" +
            "      d: \"gün\",\n" +
            "      h: \"saat\",\n" +
            "      m: \"dakika\",\n" +
            "      s: \"saniye\",\n" +
            "      ms: \"milisaniye\"\n" +
            "    },\n" +
            "    zh_CN: {\n" +
            "      y: \"年\",\n" +
            "      mo: \"个月\",\n" +
            "      w: \"周\",\n" +
            "      d: \"天\",\n" +
            "      h: \"小时\",\n" +
            "      m: \"分钟\",\n" +
            "      s: \"秒\",\n" +
            "      ms: \"毫秒\"\n" +
            "    },\n" +
            "    zh_TW: {\n" +
            "      y: \"年\",\n" +
            "      mo: \"個月\",\n" +
            "      w: \"周\",\n" +
            "      d: \"天\",\n" +
            "      h: \"小時\",\n" +
            "      m: \"分鐘\",\n" +
            "      s: \"秒\",\n" +
            "      ms: \"毫秒\"\n" +
            "    }\n" +
            "  };\n" +
            "\n" +
            "  // You can create a humanizer, which returns a function with defaults\n" +
            "  // parameters.\n" +
            "  function humanizer(passedOptions) {\n" +
            "\n" +
            "    var result = function humanizer(ms, humanizerOptions) {\n" +
            "      var options = extend({}, result, humanizerOptions || {});\n" +
            "      return doHumanization(ms, options);\n" +
            "    };\n" +
            "\n" +
            "    return extend(result, {\n" +
            "      language: \"en\",\n" +
            "      delimiter: \", \",\n" +
            "      spacer: \" \",\n" +
            "      units: [\"y\", \"mo\", \"w\", \"d\", \"h\", \"m\", \"s\"],\n" +
            "      languages: {},\n" +
            "      round: false\n" +
            "    }, passedOptions);\n" +
            "\n" +
            "  }\n" +
            "\n" +
            "  // The main function is just a wrapper around a default humanizer.\n" +
            "  var defaultHumanizer = humanizer({});\n" +
            "  function humanizeDuration() {\n" +
            "    return defaultHumanizer.apply(defaultHumanizer, arguments);\n" +
            "  }\n" +
            "\n" +
            "  // doHumanization does the bulk of the work.\n" +
            "  function doHumanization(ms, options) {\n" +
            "\n" +
            "    // Make sure we have a positive number.\n" +
            "    // Has the nice sideffect of turning Number objects into primitives.\n" +
            "    ms = Math.abs(ms);\n" +
            "\n" +
            "    var dictionary = options.languages[options.language] || languages[options.language];\n" +
            "    if (!dictionary) {\n" +
            "      throw new Error(\"No language \" + dictionary + \".\");\n" +
            "    }\n" +
            "\n" +
            "    if (ms === 0) {\n" +
            "      return render(0, options.units[options.units.length - 1], dictionary, options.spacer);\n" +
            "    }\n" +
            "\n" +
            "    var result = [];\n" +
            "\n" +
            "    // Start at the top and keep removing units, bit by bit.\n" +
            "    var unitName, unitMS, unitCount;\n" +
            "    for (var i = 0, len = options.units.length; i < len; i++) {\n" +
            "\n" +
            "      unitName = options.units[i];\n" +
            "      unitMS = UNITS[unitName];\n" +
            "\n" +
            "      // What's the number of full units we can fit?\n" +
            "      if ((i + 1) === len) {\n" +
            "        unitCount = ms / unitMS;\n" +
            "        if (options.round) {\n" +
            "          unitCount = Math.round(unitCount);\n" +
            "        }\n" +
            "      } else {\n" +
            "        unitCount = Math.floor(ms / unitMS);\n" +
            "      }\n" +
            "\n" +
            "      // Add the string.\n" +
            "      if (unitCount) {\n" +
            "        result.push(render(unitCount, unitName, dictionary, options.spacer));\n" +
            "      }\n" +
            "\n" +
            "      // Remove what we just figured out.\n" +
            "      ms -= unitCount * unitMS;\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "    return result.join(options.delimiter);\n" +
            "\n" +
            "  }\n" +
            "\n" +
            "  function render(count, type, dictionary, spacer) {\n" +
            "    var dictionaryValue = dictionary[type];\n" +
            "    var word;\n" +
            "    if (typeof dictionaryValue === \"function\") {\n" +
            "      word = dictionaryValue(count);\n" +
            "    } else {\n" +
            "      word = dictionaryValue;\n" +
            "    }\n" +
            "    return count + spacer + word;\n" +
            "  }\n" +
            "\n" +
            "  function extend(destination) {\n" +
            "    var source;\n" +
            "    for (var i = 1; i < arguments.length; i++) {\n" +
            "      source = arguments[i];\n" +
            "      for (var prop in source) {\n" +
            "        if (source.hasOwnProperty(prop)) {\n" +
            "          destination[prop] = source[prop];\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "    return destination;\n" +
            "  }\n" +
            "\n" +
            "  // Internal helper function for Polish language.\n" +
            "  function getPolishForm(c) {\n" +
            "    if (c === 1) {\n" +
            "      return 0;\n" +
            "    } else if (Math.floor(c) !== c) {\n" +
            "      return 1;\n" +
            "    } else if (c % 10 >= 2 && c % 10 <= 4 && !(c % 100 > 10 && c % 100 < 20)) {\n" +
            "      return 2;\n" +
            "    } else {\n" +
            "      return 3;\n" +
            "    }\n" +
            "  }\n" +
            "\n" +
            "  // Internal helper function for Russian language.\n" +
            "  function getRussianForm(c) {\n" +
            "    if (Math.floor(c) !== c) {\n" +
            "      return 2;\n" +
            "    } else if (c === 0 || (c >= 5 && c <= 20) || (c % 10 >= 5 && c % 10 <= 9) || (c % 10 === 0)) {\n" +
            "      return 0;\n" +
            "    } else if (c === 1 || c % 10 === 1) {\n" +
            "      return 1;\n" +
            "    } else if (c > 1) {\n" +
            "      return 2;\n" +
            "    } else {\n" +
            "      return 0;\n" +
            "    }\n" +
            "  }\n" +
            "\n" +
            "  function getSupportedLanguages() {\n" +
            "    var result = [];\n" +
            "    for (var language in languages) {\n" +
            "      if (languages.hasOwnProperty(language)) {\n" +
            "        result.push(language);\n" +
            "      }\n" +
            "    }\n" +
            "    return result;\n" +
            "  }\n" +
            "\n" +
            "  humanizeDuration.humanizer = humanizer;\n" +
            "  humanizeDuration.getSupportedLanguages = getSupportedLanguages;\n" +
            "\n" +
            "  if (typeof define === \"function\" && define.amd) {\n" +
            "    define(function() {\n" +
            "      return humanizeDuration;\n" +
            "    });\n" +
            "  } else if (typeof module !== \"undefined\" && module.exports) {\n" +
            "    module.exports = humanizeDuration;\n" +
            "  } else {\n" +
            "    this.humanizeDuration = humanizeDuration;\n" +
            "  }\n" +
            "\n" +
            "})();";

}