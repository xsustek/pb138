package com.pb138.brno;

import com.opencsv.CSVReader;
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
import java.util.HashMap;
import java.util.Map;

@Service
public class CsvToXmlConverter {
    private DocumentBuilder documentBuilder;
    private Document document;

    private static Map<Integer, String> headers = new HashMap<Integer, String>() {{
        put(4, "Klasifikace");
        put(2, "Stadium");
        put(5, "NaUlici");
        put(7, "zbran");
        put(9, "Spachani");
        put(17, "Skoda");
        put(26, "DatumUkonceni");
        put(27, "ZpusobUkonceni");
    }};

    public CsvToXmlConverter() throws ParserConfigurationException {
        this.documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    public Document getDocument(String csvPath) throws IOException, ParserConfigurationException, TransformerException {
        //if (document != null) return document;
        document = documentBuilder.newDocument();
        Element crimesElement = document.createElement("crimes");
        document.appendChild(crimesElement);
        try (FileReader reader = new FileReader(csvPath)) {
            try (CSVReader csvReader = new CSVReader(reader, ';', '"')) {
                for (String[] line : csvReader) {
                    Element crimeElement = document.createElement("crime");
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
        transformer.transform(input, output);
        return document;
    }
}
