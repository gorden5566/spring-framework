package org.springframework.beans.factory.xml;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @author gorden5566
 * @date 2020/03/25
 */
public class DocumentBuilderTest {

	@Test
	public void testParseDocument() throws ParserConfigurationException, IOException, SAXException {
		// resource
		ClassPathResource resource = new ClassPathResource("XmlBeanFactoryTests-reftypes.xml", this.getClass());

		// DocumentBuilderFactory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");

		// DocumentBuilder
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		docBuilder.setErrorHandler(new SimpleErrorHandler());

		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		ResourceEntityResolver entityResolver = new ResourceEntityResolver(resolver);
		docBuilder.setEntityResolver(entityResolver);

		// parse
		Document document = docBuilder.parse(resource.getInputStream());

		// root
		Element root = document.getDocumentElement();

		// child
		NodeList childNodes = root.getChildNodes();

		// read nodes
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (node instanceof Element) {
				String id = ((Element) node).getAttribute("id");
				String name = ((Element) node).getAttribute("name");

				ParseResult result = new ParseResult();
				result.setId(id);
				result.setName(name);

				if (((Element) node).hasAttribute("class")) {
					String className = ((Element) node).getAttribute("class").trim();
					result.setClassName(className);
				}

				System.out.println(result.toString());
			}
		}
	}

	private static class ParseResult {
		private String id;
		private String name;
		private String className;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		@Override
		public String toString() {
			return "ParseResult{" +
					"id='" + id + '\'' +
					", name='" + name + '\'' +
					", className='" + className + '\'' +
					'}';
		}
	}

	private static class SimpleErrorHandler implements ErrorHandler {

		@Override
		public void warning(SAXParseException ex) throws SAXException {
			System.out.println("Ignored XML validation warning" + ex);
		}

		@Override
		public void error(SAXParseException ex) throws SAXException {
			throw ex;
		}

		@Override
		public void fatalError(SAXParseException ex) throws SAXException {
			throw ex;
		}
	}
}
