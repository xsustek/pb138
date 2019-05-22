package com.pb138.brno;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BrnoController {
    private CsvToXmlConverter csvToXmlConverter;

    public BrnoController(CsvToXmlConverter csvToXmlConverter) {
        
    	/* Generating of XML file */
    	this.csvToXmlConverter = csvToXmlConverter;
        try {
        	this.csvToXmlConverter.getDocument("src/main/resources/brno_2016.csv");
        } catch (Exception e) {
        	System.out.println(e);
        }
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
