package com.pb138.brno;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /**
    @RequestMapping(value = "/xml/{region}", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public BrnoDataEntities xml(@PathVariable String region) {
        return new BrnoDataEntities(new BrnoDataEntity[]{
                new BrnoDataEntity("", "", region, "", "", "", "", "", "", "", ""),
                new BrnoDataEntity("", "", region, "", "", "", "", "", "", "", ""),
                new BrnoDataEntity("", "", region, "", "", "", "", "", "", "", "")
        });
    }
    */
}
