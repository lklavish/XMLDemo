package demo;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class ReadXML {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileLocation = "/home/admin-pc/eclipse-workspace/AthenaXMLAgent/fileLog.xml";
		String xmlStatusCode;
		try {
			// creating a constructor of file class and parsing an XML file
			File file = new File(fileLocation);
			// an instance of factory that gives a document builder
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			// an instance of builder to parse the specified xml file
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
			NodeList floatIssue = doc.getElementsByTagName("float_issue");
			// floatIssue is not iterable, so we are using for loop
			for (int itr = 0; itr < floatIssue.getLength(); itr++) {
				Node node = floatIssue.item(itr);
				System.out.println("\nNode Name :" + node.getNodeName());
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
//					System.out.println("Date : " + eElement.getElementsByTagName("date").item(0).getTextContent());
//					System.out.println("Shift : " + eElement.getElementsByTagName("shift").item(0).getTextContent());
//					System.out.println(
//							"XML File  Status: " + eElement.getElementsByTagName("xmlfile").item(0).getTextContent());
					xmlStatusCode = eElement.getElementsByTagName("xmlfile").item(0).getTextContent();

					if (xmlStatusCode.equals("1")) {
						System.out.println("xml file is created : " + itr);

					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
