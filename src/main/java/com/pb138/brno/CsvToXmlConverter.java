package com.pb138.brno;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for converting csv file to xml
 */
@Service
public class CsvToXmlConverter {
    private final DocumentBuilder documentBuilder;
    private final Document document;
    private final Map<Integer, String> headers = new HashMap<>();

    
    /*
     * Constructor of class
     */
    public CsvToXmlConverter() throws ParserConfigurationException {
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        document = documentBuilder.newDocument();
        initializeMap();
    }

    
    /**
     * Initializes map for parsing csv file
     */
    private void initializeMap() {
    	headers.put(1, "UtvarPCR");
        headers.put(2, "StadiumCinu");
        headers.put(3, "DruhCinu");
        headers.put(4, "KlasifikaceCinu");
        headers.put(5, "SpachanNaUlici");
        headers.put(7, "PouzitaZbran");
        headers.put(17, "VzniknutaSkoda");
        headers.put(9, "DatumVykonania");
        headers.put(26, "DatumUkoncenia");
        headers.put(27, "TypUkoncenia");
    }

    
    /**
     * This method creates new xml document and builds it using the given csv file
     * 
     * @param csvPath path to csv file
     * @return Converted xml document
     * @throws IOException
     * @throws TransformerException
     */
    public Document getDocument(String csvPath) throws IOException, TransformerException {
    	
        Element yearElement = document.createElement("Rok");
        yearElement.setAttribute("rok", "2016");
        document.appendChild(yearElement);
        Element cityElement = document.createElement("Mesto");
        cityElement.setAttribute("nazev", "Brno");
        cityElement.setAttribute("obyvatele", "377549");
        yearElement.appendChild(cityElement);
        Element crimesElement = document.createElement("TrestneCiny");
        cityElement.appendChild(crimesElement);
        
        // CSVReader(reader, ';', '"')  
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
        try (FileReader reader = new FileReader(csvPath)) {
            try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvPath)).withCSVParser(csvParser).build()) {
            	boolean isFirst = true;
                for (String[] line : csvReader) {
                	if (isFirst) {
                		isFirst = false;
                		continue;
                	}
                    Element crimeElement = document.createElement("TrestnyCin");
                    headers.forEach((index, header) -> {
                        Element el = document.createElement(header);
                        el.setTextContent(line[index]);
                        crimeElement.appendChild(el);
                    });
                    crimesElement.appendChild(crimeElement);
                }

            }

        }
        
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(new File("src/main/resources/output.xml"));
        Source input = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.transform(input, output);
        
        return document;
    }
}
