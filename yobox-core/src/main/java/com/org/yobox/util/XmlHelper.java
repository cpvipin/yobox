package com.org.yobox.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author Vipin C P
 */
public class XmlHelper {

	public static Document parse(File aFile) throws Exception {

		Document doc = null;
		return doc;
	}

	public static Document parse(String aFileName) throws Exception {
		return XmlHelper.parse(new File(aFileName));
	}

	public static Document parseString(String aXmlData) throws Exception {

		Document doc = null;
		try {
			SAXReader xmlReader = new SAXReader();
			doc = xmlReader.read(new StringReader(aXmlData));
		} catch (DocumentException ex) {
			throw new Exception(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return doc;
	}

	/**
	 * Gets a collection of child nodes given an Xpath location and a document.
	 * 
	 */
	public static Collection getNodes(Document aDoc, String anXPathString) {

		if (aDoc == null) {
			return Collections.EMPTY_LIST;
		}
		XPath xpathSelector = DocumentHelper.createXPath(anXPathString);
		if (xpathSelector == null) {
			return Collections.EMPTY_LIST;
		}
		List nodes = xpathSelector.selectNodes(aDoc);
		return nodes;
	}

	/**
	 * Gets a collection of child nodes given an Xpath location and a document.
	 * 
	 */
	public static Iterator getNodeIterator(Document aDoc, String anXPathString) {

		if (aDoc == null) {
			return Collections.EMPTY_LIST.iterator();
		}
		XPath xpathSelector = DocumentHelper.createXPath(anXPathString);
		if (xpathSelector == null) {
			return Collections.EMPTY_LIST.iterator();
		}
		List nodes = xpathSelector.selectNodes(aDoc);
		if (nodes == null) {
			return Collections.EMPTY_LIST.iterator();
		}
		return nodes.iterator();
	}

	/**
	 * Gets a collection of child nodes given an Xpath location and an element.
	 * 
	 */
	public static Iterator getNodeIterator(Element anElement,
			String anXPathString) {

		if (anElement == null) {
			return Collections.EMPTY_LIST.iterator();
		}
		Iterator nodes = anElement.elementIterator(anXPathString);
		return nodes;
	}

	/**
	 * returns the single element given the document and the xpath string
	 */
	public static Element getSinlgeElement(Document aDoc, String anXPathString) {
		if (aDoc == null) {
			return null;
		}

		XPath xpathSelector = DocumentHelper.createXPath(anXPathString);
		if (xpathSelector == null) {
			return null;
		}
		return (Element) xpathSelector.selectSingleNode(aDoc);
	}

	/**
	 * returns the single node given the document and the xpath string
	 */
	public static Node getSinlgeNode(Document aDoc, String anXPathString) {
		if (aDoc == null) {
			return null;
		}

		XPath xpathSelector = DocumentHelper.createXPath(anXPathString);
		if (xpathSelector == null) {
			return null;
		}
		return xpathSelector.selectSingleNode(aDoc);
	}

	/**
	 * returns the single node given the document and the xpath string
	 */
	public static Node getSinlgeNode(Element anElement, String anXPathString) {
		if (anElement == null) {
			return null;
		}

		XPath xpathSelector = DocumentHelper.createXPath(anXPathString);
		if (xpathSelector == null) {
			return null;
		}
		return xpathSelector.selectSingleNode(anElement);
	}

	/**
	 * returns the value of a child element given the document and the name of
	 * the child element
	 */
	public static String getSinlgeNodeValue(Document aDoc, String anXPathString) {

		if (aDoc == null) {
			return "";
		}

		XPath xpathSelector = DocumentHelper.createXPath(anXPathString);
		if (xpathSelector == null) {
			return "";
		}
		Node node = xpathSelector.selectSingleNode(aDoc);
		String value = node.getStringValue();
		return value;
	}

	/**
	 * returns the value of a child element given the element and the name of
	 * the child element
	 */
	public static String getSinlgeNodeValue(Element anElement,
			String anXPathString) {
		if (anElement == null) {
			return "";
		}
		return anElement.elementTextTrim(anXPathString);
	}

	/**
	 * returns the collection of attributes in an element
	 */
	public static Collection getAttributes(Element anElement) {
		if (anElement == null) {
			return Collections.EMPTY_LIST;
		}
		return anElement.attributes();
	}

	/**
	 * returns the attribute value given the element and the attribute name
	 */
	public static String getAttributeValue(Element anElement, String attrName) {
		return anElement.attributeValue(attrName);
	}

	/**
	 * Write a document to an xml File.
	 * 
	 * @param document Document to write.
	 * @param fileName Name of the output file.
	 * @throws IOException
	 * @return void
	 *
	 */
	public static void write(Document document,String fileName) throws Exception {

        // lets write to a file
        XMLWriter writer = new XMLWriter(
            new FileWriter( fileName )
        );
        writer.write( document );
        writer.close();

    }
	
	/**
	 * returns the value of a child element given the document and the name of
	 * the child element
	 */
	public static String getSingleNodeValue(Document aDoc, String anXPathString) {

		if (aDoc == null) {
			return "";
		}

		XPath xpathSelector = DocumentHelper.createXPath(anXPathString);
		if (xpathSelector == null) {
			return "";
		}
		Node node = xpathSelector.selectSingleNode(aDoc);
		String value = node.getStringValue();
		return value;
	}

	/**
	 * returns the value of a child element given the element and the name of
	 * the child element
	 */
	public static String getSingleNodeValue(Element anElement,
			String anXPathString) {
		if (anElement == null) {
			return "";
		}
		return anElement.elementTextTrim(anXPathString);
	}

}
