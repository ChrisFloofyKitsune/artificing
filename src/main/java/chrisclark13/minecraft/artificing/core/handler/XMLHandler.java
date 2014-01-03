package chrisclark13.minecraft.artificing.core.handler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import chrisclark13.minecraft.artificing.client.gui.inventory.guidepages.GuiGuidePage;
import chrisclark13.minecraft.artificing.client.gui.inventory.guidepages.GuiGuideSection;
import chrisclark13.minecraft.artificing.client.gui.inventory.guidepages.GuideTextAlignment;
import chrisclark13.minecraft.artificing.core.helper.LogHelper;

public class XMLHandler {

	// private static final String JAXP_SCHEMA_LANGUAGE =
	// "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	// private static final String W3C_XML_SCHEMA =
	// "http://www.w3.org/2001/XMLSchema";
	// private static final String JAXP_SCHEMA_SOURCE =
	// "http://java.sun.com/xml/jaxp/properties/schemaSource";

	private XMLHandler() {
	}

	public static ArrayList<GuiGuideSection> parseGuideBookXML(String xmlPath) {
		ArrayList<GuiGuideSection> sections = new ArrayList<GuiGuideSection>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// factory.setNamespaceAware(true);
		// factory.setValidating(true);
		// factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
		// factory.setAttribute(JAXP_SCHEMA_SOURCE, new
		// File("/mods/Artificing/manuals/guideBookSchema.xsd"));

		try {
			InputStream stream = XMLHandler.class.getResourceAsStream(xmlPath);
			DocumentBuilder db = factory.newDocumentBuilder();
			db.setErrorHandler(new GuideBookErrorHandler());
			Document doc = db.parse(stream);

			Element bookElement = doc.getDocumentElement();
			NodeList sectionNodes = bookElement.getElementsByTagName("section");

			for (int i = 0; i < sectionNodes.getLength(); i++) {

				Element sectionElement = (Element) sectionNodes.item(i);
				String name;
				boolean hasBookmark = false;
				int bookmarkColor = 0xFFFFFF;

				name = sectionElement.getAttribute("name");
				if (sectionElement.hasAttribute("hasBookmark")) {
					hasBookmark = Boolean.parseBoolean(sectionElement
							.getAttribute("hasBookmark"));
					bookmarkColor = Integer.parseInt(
							sectionElement.getAttribute("bookmarkColor"), 16);
				}

				GuiGuideSection section = new GuiGuideSection(name,
						hasBookmark, bookmarkColor);
				NodeList pageNodes = sectionElement
						.getElementsByTagName("page");

				for (int j = 0; j < pageNodes.getLength(); j++) {
					section.addPage(parsePageElement((Element) pageNodes
							.item(j)));
				}

				sections.add(section);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sections;
	}

	private static GuiGuidePage parsePageElement(Element pageElement) {
		GuiGuidePage page = new GuiGuidePage();

		NodeList list = pageElement.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) node;
				if (e.getTagName().equals("text")) {
					int x = Integer.parseInt(e.getAttribute("x"));
					int y = Integer.parseInt(e.getAttribute("y"));

					int lineWidth = GuiGuidePage.PAGE_WIDTH;
					if (e.hasAttribute("lineWidth")) {
						lineWidth = Integer.parseInt(e
								.getAttribute("lineWidth"));
					}

					GuideTextAlignment alignment = GuideTextAlignment.LEFT;
					if (e.hasAttribute("alignment")) {
						String attr = e.getAttribute("alignment");
						if (attr.equals("center")) {
							alignment = GuideTextAlignment.CENTER;
						} else if (attr.equals("right")) {
							alignment = GuideTextAlignment.RIGHT;
						}
					}

					StringBuilder sb = new StringBuilder();
					NodeList textList = e.getChildNodes();

					for (int j = 0; j < textList.getLength(); j++) {
						Node textNode = textList.item(j);
						if (textNode.getNodeType() == Node.TEXT_NODE) {
							sb.append(((Text) textNode).getData());
						} else if (textNode.getNodeType() == Node.ELEMENT_NODE) {
							sb.append(getFormattingCodeFromElement((Element) textNode));
						}
					}

					page.addTextContent(x, y, lineWidth, alignment,
							sb.toString());

				} else if (e.getTagName().equals("image")) {
					int x = Integer.parseInt(e.getAttribute("x"));
					int y = Integer.parseInt(e.getAttribute("y"));
					int width = Integer.parseInt(e.getAttribute("width"));
					int height = Integer.parseInt(e.getAttribute("height"));
					String imagePath = e.getTextContent();

					page.addImageContent(x, y, width, height, imagePath);
				}
			}
		}

		return page;
	}

	private static String getFormattingCodeFromElement(Element e) {

		String tag = e.getTagName();
		
		if (tag.equals("black")) {
			return "§0";
		} else if (tag.equals("darkBlue")) {
			return "§1";
		} else if (tag.equals("darkGreen")) {
			return "§2";
		} else if (tag.equals("darkAqua")) {
			return "§3";
		} else if (tag.equals("darkRed")) {
			return "§4";
		} else if (tag.equals("purple")) {
			return "§5";
		} else if (tag.equals("gold")) {
			return "§6";
		} else if (tag.equals("gray")) {
			return "§7";
		} else if (tag.equals("darkGray")) {
			return "§8";
		} else if (tag.equals("blue")) {
			return "§9";
		} else if (tag.equals("green")) {
			return "§a";
		} else if (tag.equals("aqua")) {
			return "§b";
		} else if (tag.equals("red")) {
			return "§c";
		} else if (tag.equals("lightPurple")) {
			return "§d";
		} else if (tag.equals("yellow")) {
			return "§e";
		} else if (tag.equals("white")) {
			return "§f";
		} else if (tag.equals("random")) {
			return "§k";
		} else if (tag.equals("bold")) {
			return "§l";
		} else if (tag.equals("strikethrough")) {
			return "§m";
		} else if (tag.equals("underline")) {
			return "§n";
		} else if (tag.equals("italic")) {
			return "§o";
		} else if (tag.equals("reset")) {
			return "§r";
		} else {
			return "";
		}
	}

	private static class GuideBookErrorHandler implements ErrorHandler {

		@Override
		public void error(SAXParseException exception) throws SAXException {
			LogHelper.log(Level.WARNING,
					"[XML Validation Error] " + exception.getMessage());
		}

		@Override
		public void fatalError(SAXParseException exception) throws SAXException {
			LogHelper.log(Level.SEVERE, "[XML Validation Fatal Error] "
					+ exception.getMessage());

		}

		@Override
		public void warning(SAXParseException exception) throws SAXException {
			LogHelper.log(Level.WARNING, "[XML Validation Warning] "
					+ exception.getMessage());
		}

	}
}
