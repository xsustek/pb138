package com.pb138.brno;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.commons.collections4.map.MultiValueMap;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for converting csv file to xml
 */
@Service
public class CsvToXmlConverter {
    private final DocumentBuilder documentBuilder;
    private final Map<Integer, String> headers = new HashMap<>();
    private CityPartConverter cityPartConverter;

    /*
     * Constructor of class
     */
    public CsvToXmlConverter(CityPartConverter cityPartConverter) throws ParserConfigurationException {
        this.cityPartConverter = cityPartConverter;

        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        initializeMap();
    }

    /**
     * Initializes map for parsing csv file
     */
    private void initializeMap() {
        headers.put(1, "utvarPCR");
        headers.put(2, "stadiumCinu");
        headers.put(3, "druhCinu");
        headers.put(4, "klasifikaceCinu");
        headers.put(5, "spachanNaUlici");
        headers.put(7, "pouzitaZbran");
        headers.put(17, "vzniknutaSkoda");
        headers.put(9, "datumVykonania");
        headers.put(26, "datumUkoncenia");
        headers.put(27, "typUkoncenia");
    }

    /**
     * This method creates new xml documents and builds it using the given csv file
     *
     * @param csvPath path to csv file
     * @throws IOException
     * @throws TransformerException
     */
    public void convert(String csvPath) throws IOException, TransformerException {
        Map<Integer, Document> docs = groupDocumentByCityPart(csvPath);
        for (Map.Entry<Integer, Document> entry : docs.entrySet()) {
            Document doc = entry.getValue();
            int index = entry.getKey();

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            File dir = new File("src/main/resources/xmls");
            dir.mkdirs();
            Result output = new StreamResult(new File("src/main/resources/xmls/" + index + ".xml"));
            Source input = new DOMSource(doc);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(input, output);
        }
    }

    private Map<Integer, Document> groupDocumentByCityPart(String csvPath) throws IOException {
        Map<Integer, Document> result = new HashMap<>();

        MultiValueMap<Integer, Map<String, String>> cityPathMap = getMap(csvPath);
        for (int i : cityPathMap.keySet()) {
            Collection<Map<String, String>> elements = cityPathMap.getCollection(i);

            Document document = getDocument(elements);
            result.put(i, document);
        }
        Collection<Map<String, String>> allElements = new ArrayList<>();
        for (int i : cityPathMap.keySet()) {
            Collection<Map<String, String>> elements = cityPathMap.getCollection(i);

            allElements.addAll(elements);
        }
        Document document = getDocument(allElements);
        result.put(10, document);


        return result;
    }

    private Document getDocument(Collection<Map<String, String>> elements) {
        Document document = documentBuilder.newDocument();
        Element yearElement = document.createElement("rok");
        yearElement.setAttribute("rok", "2016");
        document.appendChild(yearElement);
        Element cityElement = document.createElement("mesto");
        cityElement.setAttribute("nazev", "Brno");
        cityElement.setAttribute("obyvatele", "377549");
        yearElement.appendChild(cityElement);
        Element crimesElement = document.createElement("trestneCiny");
        cityElement.appendChild(crimesElement);
        elements.forEach(element -> {
            Element crimeElement = document.createElement("trestnyCin");
            element.forEach((key, value) -> {
                Element el = document.createElement(key);
                el.setTextContent(value);
                crimeElement.appendChild(el);
            });
            crimesElement.appendChild(crimeElement);
        });
        return document;
    }

    private MultiValueMap<Integer, Map<String, String>> getMap(String csvPath) throws IOException {
        MultiValueMap<Integer, Map<String, String>> result = new MultiValueMap<>();
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvPath)).withCSVParser(csvParser).build()) {
            boolean isFirst = true;
            for (String[] line : csvReader) {
                if (isFirst) {
                    isFirst = false;
                    continue;
                }
                Map<String, String> crimes = new HashMap<>();
                headers.forEach((index, header) -> crimes.put(header, line[index]));
                result.put(cityPartConverter.cityPartToNumber(line[1]), crimes);
            }
        }

        return result;
    }
}
