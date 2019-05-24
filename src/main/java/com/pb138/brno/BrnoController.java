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
    public String index(HttpServletRequest request, Map<String, Object> model) {
    	String region = request.getParameter("region");
    	
    	if (region == null) {
    		region = "str";
    	}
    	
    	Integer regionInt = regionToInt(region);
    	
    	try {
    		model.put("celkom", XmlParser.getCrimeCount(regionInt));
    		model.put("skoda", XmlParser.sumOfDamageCaused(regionInt));
    		model.put("ulica", XmlParser.getCrimesCommitedOnStreet(regionInt));
    		model.put("zbran", XmlParser.getCrimesWithWeaponCount(regionInt));
    		model.put("nazov", XmlParser.getRegionName(regionInt));
    	} catch (Exception e) {
    		System.out.println(e);
    	}
        return "index";
    }

    @RequestMapping(value = "/xml/{region}", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public BrnoDataEntities xml(@PathVariable String region) {
        return new BrnoDataEntities(new BrnoDataEntity[]{
                new BrnoDataEntity("", "", region, "", "", "", "", "", "", "", ""),
                new BrnoDataEntity("", "", region, "", "", "", "", "", "", "", ""),
                new BrnoDataEntity("", "", region, "", "", "", "", "", "", "", "")
        });
    }
    
    private int regionToInt(String region) {
    	switch (region) {
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
