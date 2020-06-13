package demo;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WriteXMLFile {
	public static void main(String[] args) {
		String filename = "fileLog.xml";
		String date = "2018-06-16";
		String shift = "2";
		String xmlStatus = "1";
		int i;
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document xml = docBuilder.newDocument();
			Element athena = xml.createElement("athena");
			xml.appendChild(athena);

			for (i = 1; i <= 10; i++) {
				Element floatIssue = xml.createElement("floatIssue");
				athena.appendChild(floatIssue);

				Element xmlDate = xml.createElement("date");
				xmlDate.appendChild(xml.createTextNode(date));
				floatIssue.appendChild(xmlDate);

				Element xmlShift = xml.createElement("shift");
				xmlShift.appendChild(xml.createTextNode(shift));
				floatIssue.appendChild(xmlShift);

				Element xmlFileStatus = xml.createElement("xmlstatus");
				xmlFileStatus.appendChild(xml.createTextNode(xmlStatus));
				floatIssue.appendChild(xmlFileStatus);

			}
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(xml);
			StreamResult result = new StreamResult(new File(filename));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

}
