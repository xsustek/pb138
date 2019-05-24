package com.pb138.brno;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BrnoController {
    private CsvToXmlConverter csvToXmlConverter;
    private XmlParser xmlParser;

    /**
     * Controller init function

     * @param csvToXmlConverter
     * @param xmlParser
     */
    public BrnoController(CsvToXmlConverter csvToXmlConverter, XmlParser xmlParser) {
        this.xmlParser = xmlParser;

        /* Generating of XML file */
        this.csvToXmlConverter = csvToXmlConverter;
        try {
            this.csvToXmlConverter.getDocument("src/main/resources/brno_2016.csv");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @RequestMapping("/")
    public String index(HttpServletRequest request, Map<String, Object> model) {
        String region = request.getParameter("region");

        if (region == null) {
            region = "brno";
        }

        Integer regionInt = regionToInt(region);

        try {
            model.put("celkom", xmlParser.getCrimeCount(regionInt));
            model.put("skoda", xmlParser.sumOfDamageCaused(regionInt));
            model.put("ulica", xmlParser.getCrimesCommitedOnStreet(regionInt));
            model.put("zbran", xmlParser.getCrimesWithWeaponCount(regionInt));
            model.put("nazov", xmlParser.getRegionName(regionInt));
        } catch (Exception e) {
            System.out.println(e);
        }
        return "index";
    }

    @RequestMapping(value = "/api/crimes/{region}", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public BrnoCrimeStatistics api(@PathVariable String region) {
        if (region == null) {
            region = "brno";
        }

        int regionInt = regionToInt(region);
        BrnoCrimeStatistics statistics = new BrnoCrimeStatistics();
        try {
            statistics.setCount(xmlParser.getCrimeCount(regionInt));
            statistics.setDamage(xmlParser.sumOfDamageCaused(regionInt));
            statistics.setStreet(xmlParser.getCrimesCommitedOnStreet(regionInt));
            statistics.setWithWeaponCount(xmlParser.getCrimesWithWeaponCount(regionInt));
            statistics.setRegion(xmlParser.getRegionName(regionInt));
        } catch (Exception e) {
            System.out.println(e);
        }

        return statistics;
    }

    private int regionToInt(String region) {
        switch (region) {
            case "brno":
                return 10;
            case "str":
                return 1;
            case "zbv":
                return 2;
            case "svr":
                return 3;
            case "zid":
                return 4;
            case "krp":
                return 5;
            case "kom":
                return 6;
            case "vys":
                return 7;
            case "bys":
                return 8;
            case "dpv":
                return 9;
            default:
                throw new IllegalArgumentException();
        }
    }
}
