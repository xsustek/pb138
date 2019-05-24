package com.pb138.brno;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlParser {	
	
	private static String numberToCityPart(int cityPart) {
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
		default: 
			throw new IllegalArgumentException();
		}
	}	
	
	private static Document getDoc() throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		return db.parse("src/main/resources/output.xml");
	}
	
	public static String getRegionName(int n) throws IllegalArgumentException {
		return numberToCityPart(n);
	}
	
	public static int getCrimeCount(int n) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		XPathFactory factory = XPathFactory.newInstance();
		XPath path = factory.newXPath();
		
		XPathExpression expression = path.compile("/rok/mesto/trestneCiny/trestnyCin[utvarPCR ='" + numberToCityPart(n) + "']");
		NodeList nodes = (NodeList) expression.evaluate(getDoc(), javax.xml.xpath.XPathConstants.NODESET);
		return nodes.getLength();
	}
	
	public static int getCrimesWithWeaponCount(int n) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		XPathFactory factory = XPathFactory.newInstance();
		XPath path = factory.newXPath();
		
		XPathExpression expression = path.compile("/rok/mesto/trestneCiny/trestnyCin[utvarPCR ='" + numberToCityPart(n) + "']/pouzitaZbran");
		NodeList nodes = (NodeList) expression.evaluate(getDoc(), javax.xml.xpath.XPathConstants.NODESET);
		
		int counter = 0;
		for (int i = 0; i < nodes.getLength(); i++) {
			if (!nodes.item(i).getTextContent().equals("0")) {
				counter++;
			}
		}
		
		return counter;
	}
	
	public static int getCrimesCommitedOnStreet(int n) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		XPathFactory factory = XPathFactory.newInstance();
		XPath path = factory.newXPath();
		
		XPathExpression expression = path.compile("/rok/mesto/trestneCiny/trestnyCin[utvarPCR ='" + numberToCityPart(n) + "']/spachanNaUlici");
		NodeList nodes = (NodeList) expression.evaluate(getDoc(), javax.xml.xpath.XPathConstants.NODESET);
		
		int counter = 0;
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getTextContent().equals("1")) {
				counter++;
			}
		}
		
		return counter;
	}
	
	public static int sumOfDamageCaused(int n) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		XPathFactory factory = XPathFactory.newInstance();
		XPath path = factory.newXPath();
		
		XPathExpression expression = path.compile("/rok/mesto/trestneCiny/trestnyCin[utvarPCR ='" + numberToCityPart(n) + "']/vzniknutaSkoda");
		NodeList nodes = (NodeList) expression.evaluate(getDoc(), javax.xml.xpath.XPathConstants.NODESET);
		
		int damage = 0;
		for (int i = 0; i < nodes.getLength(); i++) {
			damage += Integer.parseInt(nodes.item(i).getTextContent());
		}
		
		return damage;
	}
}
