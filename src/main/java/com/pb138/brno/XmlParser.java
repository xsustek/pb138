package com.pb138.brno;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * Class for parsing XML file
 */
@Service
public class XmlParser {
    private XPathFactory factory = XPathFactory.newInstance();
    private XPath path = factory.newXPath();
    private XPathExpression expression1;
    private XPathExpression expression2;
    private Map<Integer, Document> documents = new HashMap<>();
    private CityPartConverter cityPartConverter;

    public XmlParser(CityPartConverter cityPartConverter) {
        this.cityPartConverter = cityPartConverter;
    }
    
    
    /**
     * Gets population in corresponding city part
     * 
     * @param cityPart
     * @return population number
     */
    public int getCityPartPopulation(int cityPart) {
        switch (cityPart) {
        case 1:
            return 82874;
        case 2:
            return 26772;
        case 3:
            return 48161;
        case 4:
            return 65780;
        case 5:
            return 47215;
        case 6:
            return 22965;
        case 7:
            return 50207;
        case 8:
            return 32903;
        case 9:
            return 377549;
        case 10:
            return 377549;
        default: 
            throw new IllegalArgumentException();
        }
    }	
    
    
    /**
     * Get the document to pull data from
     * 
     * @return document
     * @throws Exception
     */
    private Document getDoc(int n) throws Exception {
        if (documents.containsKey(n)) return documents.get(n);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse("src/main/resources/xmls/" + n + ".xml");
        documents.put(n, doc);
        return doc;
    }

    /**
     * Returns name of region
     *
     * @param n Int value of region
     * @return String name of region
     * @throws IllegalArgumentException
     */
    public String getRegionName(int n) throws IllegalArgumentException {
        return cityPartConverter.numberToCityPart(n);
    }

    /**
     * Returns total crime count in given region
     *
     * @param n Int value of region
     * @return Int count of crimes in region
     * @throws Exception
     */
    public int getCrimeCount(int n) throws Exception {
        if (n == 10) {
            expression1 = path.compile("/rok/mesto/trestneCiny/trestnyCin");
        } else {
            expression1 = path.compile("/rok/mesto/trestneCiny/trestnyCin[utvarPCR ='" + cityPartConverter.numberToCityPart(n) + "']");
        }
        NodeList nodes = (NodeList) expression1.evaluate(getDoc(n), javax.xml.xpath.XPathConstants.NODESET);
        return nodes.getLength();
    }

    /**
     * Returns numbers of crimes committed with weapon in given region
     *
     * @param n Int value of region
     * @return Int number of crimes
     * @throws Exception
     */
    public int getCrimesWithWeaponCount(int n) throws Exception {
        if (n == 10) {
            expression1 = path.compile("/rok/mesto/trestneCiny/trestnyCin/pouzitaZbran");
        } else {
            expression1 = path.compile("/rok/mesto/trestneCiny/trestnyCin[utvarPCR ='" + cityPartConverter.numberToCityPart(n) + "']/pouzitaZbran");
        }
        NodeList nodes = (NodeList) expression1.evaluate(getDoc(n), javax.xml.xpath.XPathConstants.NODESET);

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
     * @throws Exception
     */
    public int getCrimesCommitedOnStreet(int n) throws Exception {
        if (n == 10) {
            expression1 = path.compile("/rok/mesto/trestneCiny/trestnyCin/spachanNaUlici");
        } else {
            expression1 = path.compile("/rok/mesto/trestneCiny/trestnyCin[utvarPCR ='" + cityPartConverter.numberToCityPart(n) + "']/spachanNaUlici");
        }

        NodeList nodes = (NodeList) expression1.evaluate(getDoc(n), javax.xml.xpath.XPathConstants.NODESET);

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
     * @throws Exception
     */
    public int sumOfDamageCaused(int n) throws Exception {
        if (n == 10) {
            expression1 = path.compile("/rok/mesto/trestneCiny/trestnyCin/vzniknutaSkoda");
        } else {
            expression1 = path.compile("/rok/mesto/trestneCiny/trestnyCin[utvarPCR ='" + cityPartConverter.numberToCityPart(n) + "']/vzniknutaSkoda");
        }
        NodeList nodes = (NodeList) expression1.evaluate(getDoc(n), javax.xml.xpath.XPathConstants.NODESET);

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
     * @throws Exception
     */
    public int averageTimeOfCrime(int n) throws Exception {
        if (n == 10) {
            expression1 = path.compile("/rok/mesto/trestneCiny/trestnyCin/datumVykonania");
            expression2 = path.compile("/rok/mesto/trestneCiny/trestnyCin/datumUkoncenia");
        } else {
            expression1 = path.compile("/rok/mesto/trestneCiny/trestnyCin[utvarPCR ='" + cityPartConverter.numberToCityPart(n) + "']/datumVykonania");
            expression2 = path.compile("/rok/mesto/trestneCiny/trestnyCin[utvarPCR ='" + cityPartConverter.numberToCityPart(n) + "']/datumUkoncenia");
        }
        NodeList nodesStart = (NodeList) expression1.evaluate(getDoc(n), javax.xml.xpath.XPathConstants.NODESET);
        NodeList nodesEnd = (NodeList) expression2.evaluate(getDoc(n), javax.xml.xpath.XPathConstants.NODESET);

        int averageTime = 0;
        SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy");
        for (int i = 0; i < nodesStart.getLength(); i++) {
            Date dateStart = myFormat.parse(nodesStart.item(i).getTextContent());
            Date dateEnd = myFormat.parse(nodesEnd.item(i).getTextContent());
            long diff = dateEnd.getTime() - dateStart.getTime();
            if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) > 2000 || TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) < 0) {
            	continue;
            }
            averageTime += TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        }

        return (averageTime / nodesStart.getLength());
    }


    /**
     * Get get cases / population
     *
     * @param n city part
     * @return number
     * @throws Exception
     */
    public double averageOfCases(int n) throws Exception {
        double pop = getCityPartPopulation(n);
        double cases = getCrimeCount(n);
        
        double result = cases / pop;
        
        return Double.parseDouble(Double.toString(result).substring(0, 6));
    }

    /**
     * Gets list of crime type nodes
     * 
     * @param n city part
     * @return list of nodes
     * @throws Exception
     */
    private NodeList getCrimeTypes(int n) throws Exception {
        if (n == 10) {
            expression1 = path.compile("/rok/mesto/trestneCiny/trestnyCin/druhCinu");
        } else {
            expression1 = path.compile("/rok/mesto/trestneCiny/trestnyCin[utvarPCR ='" + cityPartConverter.numberToCityPart(n) + "']/druhCinu");
        }
        NodeList nodes = (NodeList) expression1.evaluate(getDoc(n), javax.xml.xpath.XPathConstants.NODESET);
        return nodes;
    }
    
    
    /**
     * Gets number of precin from xml file
     * 
     * @param n city part
     * @return number of precin
     * @throws Exception
     */
    public int getNumberOfPrecin(int n) throws Exception {
        NodeList nodes = getCrimeTypes(n);
        int counter = 0;
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getTextContent().equals("18")) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Gets number of zlocin from xml file
     *
     * @param n city part
     * @return number of zlocin
     * @throws Exception
     */
    public int getNumberOfZlocin(int n) throws Exception {
        NodeList nodes = getCrimeTypes(n);
        int counter = 0;
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getTextContent().equals("11")) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Get list of stage nodes
     *  
     * @param n city part
     * @return list of stage nodes
     * @throws Exception
     */
    private NodeList getStageList(int n) throws Exception {
        if (n == 10) {
            expression1 = path.compile("/rok/mesto/trestneCiny/trestnyCin/stadiumCinu");
        } else {
            expression1 = path.compile("/rok/mesto/trestneCiny/trestnyCin[utvarPCR ='" + cityPartConverter.numberToCityPart(n) + "']/stadiumCinu");
        }
        NodeList nodes = (NodeList) expression1.evaluate(getDoc(n), javax.xml.xpath.XPathConstants.NODESET);
        return nodes;
    }

    /**
     * Gets number of executed crimes
     * 
     * @param n city part
     * @return number of executed crimes
     * @throws Exception
     */
    public int getNumberOfExecutedCrimes(int n) throws Exception {
        NodeList nodes = getStageList(n);

        int counter = 0;
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getTextContent().equals("3")) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Gets number of prepared crimes
     * 
     * @param n city part
     * @return number of prepared crimes
     * @throws Exception
     */
    public int getNumberOfPreparedCrimes(int n) throws Exception {
        NodeList nodes = getStageList(n);

        int counter = 0;
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getTextContent().equals("2")) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Gets number of planned crimes
     * 
     * @param n city part
     * @return number of planned crimes
     * @throws Exception
     */
    public int getNumberOfPlannedCrimes(int n) throws Exception {
        NodeList nodes = getStageList(n);

        int counter = 0;
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getTextContent().equals("1")) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Get list of resolution type nodes
     *  
     * @param n city part
     * @return list of resolution nodes
     * @throws Exception
     */
    private NodeList getResolutionTypeList(int n) throws Exception {
        if (n == 10) {
            expression1 = path.compile("/rok/mesto/trestneCiny/trestnyCin/typUkoncenia");
        } else {
            expression1 = path.compile("/rok/mesto/trestneCiny/trestnyCin[utvarPCR ='" + cityPartConverter.numberToCityPart(n) + "']/typUkoncenia");
        }
        NodeList nodes = (NodeList) expression1.evaluate(getDoc(n), javax.xml.xpath.XPathConstants.NODESET);
        return nodes;
    }

    /**
     * Gets number of planned crimes
     * 
     * @param n city part
     * @return number of planned crimes
     * @throws Exception
     */
    public int getNumberOfPerpetratorsCaught(int n) throws Exception {
        NodeList nodes = getResolutionTypeList(n);
        int counter = 0;
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getTextContent().equals("1")) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Gets number of cold cases
     * 
     * @param n city part
     * @return number of cold cases
     * @throws Exception
     */
    public int getNumberOfColdCases(int n) throws Exception {
        NodeList nodes = getResolutionTypeList(n);
        int counter = 0;
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getTextContent().equals("5")) {
                counter++;
            }
        }
        return counter;
    }
}
