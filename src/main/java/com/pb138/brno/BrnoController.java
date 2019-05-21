package com.pb138.brno;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

@Controller
public class BrnoController {
    private CsvToXmlConverter csvToXmlConverter;

    public BrnoController(CsvToXmlConverter csvToXmlConverter) {
        this.csvToXmlConverter = csvToXmlConverter;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
