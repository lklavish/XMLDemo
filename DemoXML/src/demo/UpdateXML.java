package demo;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UpdateXML {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileLocation = "/home/admin-pc/eclipse-workspace/AthenaXMLAgent/fileLog.xml";
		String xmlStatusCode;
		String xmlFileDate = null;
		String xmlFileShift = null;
		Date fileLogDate;
		int fileLogShift;
		Date newDate;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String query;
		int DBShift;
		Date DBDate;
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
//				System.out.println("\nNode Name :" + node.getNodeName());
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
//					System.out.println("Date : " + eElement.getElementsByTagName("date").item(0).getTextContent());
//					System.out.println("Shift : " + eElement.getElementsByTagName("shift").item(0).getTextContent());
//					System.out.println(
//							"XML File  Status: " + eElement.getElementsByTagName("xmlfile").item(0).getTextContent());
					xmlFileDate = eElement.getElementsByTagName("date").item(0).getTextContent();
					xmlFileShift = eElement.getElementsByTagName("shift").item(0).getTextContent();
					xmlStatusCode = eElement.getElementsByTagName("xmlfile").item(0).getTextContent();

//					System.out.println("XML File Date : " + xmlFileDate + ", XML File Shift : " + xmlFileShift
//							+ ", XML Status Code : " + xmlStatusCode);
//					Node n1 = eElement.getElementsByTagName("xmlfile").item(0);
//					if (xmlStatusCode.equals("0")) {
//						System.out.println("xml file is created : " + itr);
//						n1.setTextContent("1");
//
//					}

				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fileLocation));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Date : " + xmlFileDate);
		System.out.println("Shift : " + xmlFileShift);

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/athena", "root", "");
			query = "SELECT date, shift FROM float_issue ORDER BY date ASC;";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			fileLogDate = sdf.parse(xmlFileDate);
			fileLogShift = Integer.parseInt(xmlFileShift);
			System.out.println("File Log Date : " + fileLogDate);
			System.out.println("File Log Shift : " + fileLogShift);

			while (rs.next()) {
//			DBDate = rs.getString(1);
				DBDate = sdf.parse(rs.getString(1));
				DBShift = Integer.parseInt(rs.getString(2));

				System.out.println("DB Date : " + DBDate);
				System.out.println("DB Shift : " + DBShift);

				if (DBDate.equals(fileLogDate)) {
					if (DBShift > fileLogShift)
						System.out.println("DBDate is equal but DBShift is greater");
					// first it will generate the xml file for shift wise xml
					// then it will add date shift & status in fileLog file.

				}

				else if (DBDate.after(fileLogDate)) {
					System.out.println("DBDate is Greater");
				}

				else {
					System.out.println("No updated data found");
				}
			}
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (ParseException pe) {
			// TODO Auto-generated catch block
			pe.printStackTrace();
		}

	}

}
