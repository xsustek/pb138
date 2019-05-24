package com.pb138.brno;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class for parsing XML file
 */

@Service
public class XmlParser {	

    private Document document;
    private String numberToCityPart(int cityPart) {
        switch (cityPart) {
        case 1:
            return "OOP BRNO - STŘED";
        case 2:
            return "OOP BRNO - ŽABOVŘESKY";
        case 3:
            return "OOP BRNO - SEVER";
        case 4:
            return "OOP BRNO - ŽIDENICE";
        case 5:
            return "OOP BRNO - KRÁLOVO POLE";
        case 6:
            return "OOP BRNO - KOMÁROV";
        case 7:
            return "OOP BRNO - VÝSTAVIŠTĚ";
        case 8:
            return "OOP BRNO - BYSTRC";
        case 9:
            return "OŽP A DOPROVODU VLAKŮ";
        case 10:
        	return "Celé Brno";
        default: 
            throw new IllegalArgumentException();
        }
    }	
    
    private Document getDoc() throws ParserConfigurationException, IOException, SAXException {
        if(document != null) return document;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        document = db.parse("src/main/resources/output.xml");
        return document;
    }

    /**
     * Returns name of region
     *
     * @param n Int value of region
     * @return String name of region
     * @throws IllegalArgumentException
     */
    public String getRegionName(int n) throws IllegalArgumentException {
        return numberToCityPart(n);
    }

    /**
     * Returns total crime count in given region
     *
     * @param n Int value of region
     * @return Int count of crimes in region
     * @throws XPathExpressionException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public int getCrimeCount(int n) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath path = factory.newXPath();
        XPathExpression expression;

        if (n == 10) {
            expression = path.compile("/rok/mesto/trestneCiny/trestnyCin");
        } else {
            expression = path.compile("/rok/mesto/trestneCiny/trestnyCin[utvarPCR ='" + numberToCityPart(n) + "']");
        }
        NodeList nodes = (NodeList) expression.evaluate(getDoc(), javax.xml.xpath.XPathConstants.NODESET);
        return nodes.getLength();
    }

    /**
     * Returns numbers of crimes committed with weapon in given region
     *
     * @param n Int value of region
     * @return Int number of crimes
     * @throws XPathExpressionException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public int getCrimesWithWeaponCount(int n) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath path = factory.newXPath();
        XPathExpression expression;

        if (n == 10) {
            expression = path.compile("/rok/mesto/trestneCiny/trestnyCin/pouzitaZbran");
        } else {
            expression = path.compile("/rok/mesto/trestneCiny/trestnyCin[utvarPCR ='" + numberToCityPart(n) + "']/pouzitaZbran");
        }
        NodeList nodes = (NodeList) expression.evaluate(getDoc(), javax.xml.xpath.XPathConstants.NODESET);

        int counter = 0;
        for (int i = 0; i < nodes.getLength(); i++) {
            if (!nodes.item(i).getTextContent().equals("0")) {
                counter++;
            }
        }

        return counter;
    }

    /**
     * Returns number of crimes committed on street in given region
     *
     * @param n Int value of region
     * @return Int count of crimes
     * @throws XPathExpressionException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public int getCrimesCommitedOnStreet(int n) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath path = factory.newXPath();
        XPathExpression expression;

        if (n == 10) {
            expression = path.compile("/rok/mesto/trestneCiny/trestnyCin/spachanNaUlici");
        } else {
            expression = path.compile("/rok/mesto/trestneCiny/trestnyCin[utvarPCR ='" + numberToCityPart(n) + "']/spachanNaUlici");
        }

        NodeList nodes = (NodeList) expression.evaluate(getDoc(), javax.xml.xpath.XPathConstants.NODESET);

        int counter = 0;
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getTextContent().equals("1")) {
                counter++;
            }
        }

        return counter;
    }

    /**
     * Returns total damage caused by crime in given region
     *
     * @param n Int value of region
     * @return Int damage
     * @throws XPathExpressionException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public int sumOfDamageCaused(int n) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath path = factory.newXPath();
        XPathExpression expression;

        if (n == 10) {
            expression = path.compile("/rok/mesto/trestneCiny/trestnyCin/vzniknutaSkoda");
        } else {
            expression = path.compile("/rok/mesto/trestneCiny/trestnyCin[utvarPCR ='" + numberToCityPart(n) + "']/vzniknutaSkoda");
        }
        NodeList nodes = (NodeList) expression.evaluate(getDoc(), javax.xml.xpath.XPathConstants.NODESET);

        int damage = 0;
        for (int i = 0; i < nodes.getLength(); i++) {
            damage += Integer.parseInt(nodes.item(i).getTextContent());
        }

        return damage;
    }
    
    
    /**
     * This method calculates average time of crime resolution in given region
     * 
     * @param n number corresponding to region
     * @return average time 
     * @throws XPathException
     * @throws Exception
     * @throws IOException
     * @throws SAXException
     */
    public double averageTimeOfCrime(int n) throws XPathException, Exception, IOException, SAXException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath path = factory.newXPath();
        XPathExpression expressionStart;
        XPathExpression expressionEnd;

        if (n == 10) {
        	expressionStart = path.compile("/rok/mesto/trestneCiny/trestnyCin/datumVykonania");
        	expressionEnd = path.compile("/rok/mesto/trestneCiny/trestnyCin/datumUkoncenia");
        } else {
        	expressionStart = path.compile("/rok/mesto/trestneCiny/trestnyCin[utvarPCR ='" + numberToCityPart(n) + "']/datumVykonania");
        	expressionEnd = path.compile("/rok/mesto/trestneCiny/trestnyCin[utvarPCR ='" + numberToCityPart(n) + "']/datumUkoncenia");
        }
        NodeList nodesStart = (NodeList) expressionStart.evaluate(getDoc(), javax.xml.xpath.XPathConstants.NODESET);
        NodeList nodesEnd = (NodeList) expressionEnd.evaluate(getDoc(), javax.xml.xpath.XPathConstants.NODESET);

        double averageTime = 0;
        SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy");
        for (int i = 0; i < nodesStart.getLength(); i++) {
        	Date dateStart = myFormat.parse(nodesStart.item(i).getTextContent());
            Date dateEnd = myFormat.parse(nodesEnd.item(i).getTextContent());
            averageTime += (dateEnd.getTime() - dateStart.getTime());
        }

        return (averageTime / nodesStart.getLength());
    }
}
