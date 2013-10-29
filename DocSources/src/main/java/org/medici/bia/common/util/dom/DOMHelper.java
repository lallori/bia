/*
 * DOMHelper.java
 *
 * Developed by The Medici Archive Project Inc. (2010-2012)
 * 
 * This file is part of DocSources.
 * 
 * DocSources is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * DocSources is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * As a special exception, if you link this library with other files to
 * produce an executable, this library does not by itself cause the
 * resulting executable to be covered by the GNU General Public License.
 * This exception does not however invalidate any other reasons why the
 * executable file might be covered by the GNU General Public License.
 */
package org.medici.bia.common.util.dom;

import java.io.ByteArrayInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.medici.bia.exception.DOMHelperException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Utility class for DOM transformations of a provided xml document.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 * 
 */
public class DOMHelper {

	public static final String ENTITIES = "<!ENTITY nbsp \"&#160;\">\n"
			+ "<!ENTITY iexcl \"&#161;\">\n" + "<!ENTITY cent \"&#162;\">\n"
			+ "<!ENTITY pound \"&#163;\">\n" + "<!ENTITY curren \"&#164;\">\n"
			+ "<!ENTITY yen \"&#165;\">\n" + "<!ENTITY brvbar \"&#166;\">\n"
			+ "<!ENTITY sect \"&#167;\">\n" + "<!ENTITY uml \"&#168;\">\n"
			+ "<!ENTITY copy \"&#169;\">\n" + "<!ENTITY ordf \"&#170;\">\n"
			+ "<!ENTITY laquo \"&#171;\">\n" + "<!ENTITY not \"&#172;\">\n"
			+ "<!ENTITY shy \"&#173;\">\n" + "<!ENTITY reg \"&#174;\">\n"
			+ "<!ENTITY macr \"&#175;\">\n" + "<!ENTITY deg \"&#176;\">\n"
			+ "<!ENTITY plusmn \"&#177;\">\n" + "<!ENTITY sup2 \"&#178;\">\n"
			+ "<!ENTITY sup3 \"&#179;\">\n" + "<!ENTITY acute \"&#180;\">\n"
			+ "<!ENTITY micro \"&#181;\">\n" + "<!ENTITY para \"&#182;\">\n"
			+ "<!ENTITY middot \"&#183;\">\n" + "<!ENTITY cedil \"&#184;\">\n"
			+ "<!ENTITY sup1 \"&#185;\">\n" + "<!ENTITY ordm \"&#186;\">\n"
			+ "<!ENTITY raquo \"&#187;\">\n" + "<!ENTITY frac14 \"&#188;\">\n"
			+ "<!ENTITY frac12 \"&#189;\">\n" + "<!ENTITY frac34 \"&#190;\">\n"
			+ "<!ENTITY iquest \"&#191;\">\n" + "<!ENTITY Agrave \"&#192;\">\n"
			+ "<!ENTITY Aacute \"&#193;\">\n" + "<!ENTITY Acirc \"&#194;\">\n"
			+ "<!ENTITY Atilde \"&#195;\">\n" + "<!ENTITY Auml \"&#196;\">\n"
			+ "<!ENTITY Aring \"&#197;\">\n" + "<!ENTITY AElig \"&#198;\">\n"
			+ "<!ENTITY Ccedil \"&#199;\">\n" + "<!ENTITY Egrave \"&#200;\">\n"
			+ "<!ENTITY Eacute \"&#201;\">\n" + "<!ENTITY Ecirc \"&#202;\">\n"
			+ "<!ENTITY Euml \"&#203;\">\n" + "<!ENTITY Igrave \"&#204;\">\n"
			+ "<!ENTITY Iacute \"&#205;\">\n" + "<!ENTITY Icirc \"&#206;\">\n"
			+ "<!ENTITY Iuml \"&#207;\">\n" + "<!ENTITY ETH \"&#208;\">\n"
			+ "<!ENTITY Ntilde \"&#209;\">\n" + "<!ENTITY Ograve \"&#210;\">\n"
			+ "<!ENTITY Oacute \"&#211;\">\n" + "<!ENTITY Ocirc \"&#212;\">\n"
			+ "<!ENTITY Otilde \"&#213;\">\n" + "<!ENTITY Ouml \"&#214;\">\n"
			+ "<!ENTITY times \"&#215;\">\n" + "<!ENTITY Oslash \"&#216;\">\n"
			+ "<!ENTITY Ugrave \"&#217;\">\n" + "<!ENTITY Uacute \"&#218;\">\n"
			+ "<!ENTITY Ucirc \"&#219;\">\n" + "<!ENTITY Uuml \"&#220;\">\n"
			+ "<!ENTITY Yacute \"&#221;\">\n" + "<!ENTITY THORN \"&#222;\">\n"
			+ "<!ENTITY szlig \"&#223;\">\n" + "<!ENTITY agrave \"&#224;\">\n"
			+ "<!ENTITY aacute \"&#225;\">\n" + "<!ENTITY acirc \"&#226;\">\n"
			+ "<!ENTITY atilde \"&#227;\">\n" + "<!ENTITY auml \"&#228;\">\n"
			+ "<!ENTITY aring \"&#229;\">\n" + "<!ENTITY aelig \"&#230;\">\n"
			+ "<!ENTITY ccedil \"&#231;\">\n" + "<!ENTITY egrave \"&#232;\">\n"
			+ "<!ENTITY eacute \"&#233;\">\n" + "<!ENTITY ecirc \"&#234;\">\n"
			+ "<!ENTITY euml \"&#235;\">\n" + "<!ENTITY igrave \"&#236;\">\n"
			+ "<!ENTITY iacute \"&#237;\">\n" + "<!ENTITY icirc \"&#238;\">\n"
			+ "<!ENTITY iuml \"&#239;\">\n" + "<!ENTITY eth \"&#240;\">\n"
			+ "<!ENTITY ntilde \"&#241;\">\n" + "<!ENTITY ograve \"&#242;\">\n"
			+ "<!ENTITY oacute \"&#243;\">\n" + "<!ENTITY ocirc \"&#244;\">\n"
			+ "<!ENTITY otilde \"&#245;\">\n" + "<!ENTITY ouml \"&#246;\">\n"
			+ "<!ENTITY divide \"&#247;\">\n" + "<!ENTITY oslash \"&#248;\">\n"
			+ "<!ENTITY ugrave \"&#249;\">\n" + "<!ENTITY uacute \"&#250;\">\n"
			+ "<!ENTITY ucirc \"&#251;\">\n" + "<!ENTITY uuml \"&#252;\">\n"
			+ "<!ENTITY yacute \"&#253;\">\n" + "<!ENTITY thorn \"&#254;\">\n"
			+ "<!ENTITY yuml \"&#255;\">\n" + "<!ENTITY quot \"&#34;\">\n"
			+ "<!ENTITY amp \"&#38;\">\n" + "<!ENTITY lt \"&#60;\">\n"
			+ "<!ENTITY gt \"&#62;\">\n" + "<!ENTITY apos \"&#39;\">\n"
			+ "<!ENTITY OElig \"&#338;\">\n" + "<!ENTITY oelig \"&#339;\">\n"
			+ "<!ENTITY Scaron \"&#352;\">\n" + "<!ENTITY scaron \"&#353;\">\n"
			+ "<!ENTITY Yuml \"&#376;\">\n" + "<!ENTITY circ \"&#710;\">\n"
			+ "<!ENTITY tilde \"&#732;\">\n" + "<!ENTITY ensp \"&#8194;\">\n"
			+ "<!ENTITY emsp \"&#8195;\">\n" + "<!ENTITY thinsp \"&#8201;\">\n"
			+ "<!ENTITY zwnj \"&#8204;\">\n" + "<!ENTITY zwj \"&#8205;\">\n"
			+ "<!ENTITY lrm \"&#8206;\">\n" + "<!ENTITY rlm \"&#8207;\">\n"
			+ "<!ENTITY ndash \"&#8211;\">\n" + "<!ENTITY mdash \"&#8212;\">\n"
			+ "<!ENTITY lsquo \"&#8216;\">\n" + "<!ENTITY rsquo \"&#8217;\">\n"
			+ "<!ENTITY sbquo \"&#8218;\">\n" + "<!ENTITY ldquo \"&#8220;\">\n"
			+ "<!ENTITY rdquo \"&#8221;\">\n" + "<!ENTITY bdquo \"&#8222;\">\n"
			+ "<!ENTITY dagger \"&#8224;\">\n"
			+ "<!ENTITY Dagger \"&#8225;\">\n"
			+ "<!ENTITY permil \"&#8240;\">\n"
			+ "<!ENTITY lsaquo \"&#8249;\">\n"
			+ "<!ENTITY rsaquo \"&#8250;\">\n" + "<!ENTITY euro \"&#8364;\">\n"
			+ "<!ENTITY fnof \"&#402;\">\n" + "<!ENTITY Alpha \"&#913;\">\n"
			+ "<!ENTITY Beta \"&#914;\">\n" + "<!ENTITY Gamma \"&#915;\">\n"
			+ "<!ENTITY Delta \"&#916;\">\n" + "<!ENTITY Epsilon \"&#917;\">\n"
			+ "<!ENTITY Zeta \"&#918;\">\n" + "<!ENTITY Eta \"&#919;\">\n"
			+ "<!ENTITY Theta \"&#920;\">\n" + "<!ENTITY Iota \"&#921;\">\n"
			+ "<!ENTITY Kappa \"&#922;\">\n" + "<!ENTITY Lambda \"&#923;\">\n"
			+ "<!ENTITY Mu \"&#924;\">\n" + "<!ENTITY Nu \"&#925;\">\n"
			+ "<!ENTITY Xi \"&#926;\">\n" + "<!ENTITY Omicron \"&#927;\">\n"
			+ "<!ENTITY Pi \"&#928;\">\n" + "<!ENTITY Rho \"&#929;\">\n"
			+ "<!ENTITY Sigma \"&#931;\">\n" + "<!ENTITY Tau \"&#932;\">\n"
			+ "<!ENTITY Upsilon \"&#933;\">\n" + "<!ENTITY Phi \"&#934;\">\n"
			+ "<!ENTITY Chi \"&#935;\">\n" + "<!ENTITY Psi \"&#936;\">\n"
			+ "<!ENTITY Omega \"&#937;\">\n" + "<!ENTITY alpha \"&#945;\">\n"
			+ "<!ENTITY beta \"&#946;\">\n" + "<!ENTITY gamma \"&#947;\">\n"
			+ "<!ENTITY delta \"&#948;\">\n" + "<!ENTITY epsilon \"&#949;\">\n"
			+ "<!ENTITY zeta \"&#950;\">\n" + "<!ENTITY eta \"&#951;\">\n"
			+ "<!ENTITY theta \"&#952;\">\n" + "<!ENTITY iota \"&#953;\">\n"
			+ "<!ENTITY kappa \"&#954;\">\n" + "<!ENTITY lambda \"&#955;\">\n"
			+ "<!ENTITY mu \"&#956;\">\n" + "<!ENTITY nu \"&#957;\">\n"
			+ "<!ENTITY xi \"&#958;\">\n" + "<!ENTITY omicron \"&#959;\">\n"
			+ "<!ENTITY pi \"&#960;\">\n" + "<!ENTITY rho \"&#961;\">\n"
			+ "<!ENTITY sigmaf \"&#962;\">\n" + "<!ENTITY sigma \"&#963;\">\n"
			+ "<!ENTITY tau \"&#964;\">\n" + "<!ENTITY upsilon \"&#965;\">\n"
			+ "<!ENTITY phi \"&#966;\">\n" + "<!ENTITY chi \"&#967;\">\n"
			+ "<!ENTITY psi \"&#968;\">\n" + "<!ENTITY omega \"&#969;\">\n"
			+ "<!ENTITY thetasym \"&#977;\">\n"
			+ "<!ENTITY upsih \"&#978;\">\n" + "<!ENTITY piv \"&#982;\">\n"
			+ "<!ENTITY bull \"&#8226;\">\n" + "<!ENTITY hellip \"&#8230;\">\n"
			+ "<!ENTITY prime \"&#8242;\">\n" + "<!ENTITY Prime \"&#8243;\">\n"
			+ "<!ENTITY oline \"&#8254;\">\n" + "<!ENTITY frasl \"&#8260;\">\n"
			+ "<!ENTITY weierp \"&#8472;\">\n"
			+ "<!ENTITY image \"&#8465;\">\n" + "<!ENTITY real \"&#8476;\">\n"
			+ "<!ENTITY trade \"&#8482;\">\n"
			+ "<!ENTITY alefsym \"&#8501;\">\n"
			+ "<!ENTITY larr \"&#8592;\">\n" + "<!ENTITY uarr \"&#8593;\">\n"
			+ "<!ENTITY rarr \"&#8594;\">\n" + "<!ENTITY darr \"&#8595;\">\n"
			+ "<!ENTITY harr \"&#8596;\">\n" + "<!ENTITY crarr \"&#8629;\">\n"
			+ "<!ENTITY lArr \"&#8656;\">\n" + "<!ENTITY uArr \"&#8657;\">\n"
			+ "<!ENTITY rArr \"&#8658;\">\n" + "<!ENTITY dArr \"&#8659;\">\n"
			+ "<!ENTITY hArr \"&#8660;\">\n" + "<!ENTITY forall \"&#8704;\">\n"
			+ "<!ENTITY part \"&#8706;\">\n" + "<!ENTITY exist \"&#8707;\">\n"
			+ "<!ENTITY empty \"&#8709;\">\n" + "<!ENTITY nabla \"&#8711;\">\n"
			+ "<!ENTITY isin \"&#8712;\">\n" + "<!ENTITY notin \"&#8713;\">\n"
			+ "<!ENTITY ni \"&#8715;\">\n" + "<!ENTITY prod \"&#8719;\">\n"
			+ "<!ENTITY sum \"&#8721;\">\n" + "<!ENTITY minus \"&#8722;\">\n"
			+ "<!ENTITY lowast \"&#8727;\">\n"
			+ "<!ENTITY radic \"&#8730;\">\n" + "<!ENTITY prop \"&#8733;\">\n"
			+ "<!ENTITY infin \"&#8734;\">\n" + "<!ENTITY ang \"&#8736;\">\n"
			+ "<!ENTITY and \"&#8743;\">\n" + "<!ENTITY or \"&#8744;\">\n"
			+ "<!ENTITY cap \"&#8745;\">\n" + "<!ENTITY cup \"&#8746;\">\n"
			+ "<!ENTITY int \"&#8747;\">\n" + "<!ENTITY there4 \"&#8756;\">\n"
			+ "<!ENTITY sim \"&#8764;\">\n" + "<!ENTITY cong \"&#8773;\">\n"
			+ "<!ENTITY asymp \"&#8776;\">\n" + "<!ENTITY ne \"&#8800;\">\n"
			+ "<!ENTITY equiv \"&#8801;\">\n" + "<!ENTITY le \"&#8804;\">\n"
			+ "<!ENTITY ge \"&#8805;\">\n" + "<!ENTITY sub \"&#8834;\">\n"
			+ "<!ENTITY sup \"&#8835;\">\n" + "<!ENTITY nsub \"&#8836;\">\n"
			+ "<!ENTITY sube \"&#8838;\">\n" + "<!ENTITY supe \"&#8839;\">\n"
			+ "<!ENTITY oplus \"&#8853;\">\n"
			+ "<!ENTITY otimes \"&#8855;\">\n" + "<!ENTITY perp \"&#8869;\">\n"
			+ "<!ENTITY sdot \"&#8901;\">\n" + "<!ENTITY lceil \"&#8968;\">\n"
			+ "<!ENTITY rceil \"&#8969;\">\n"
			+ "<!ENTITY lfloor \"&#8970;\">\n"
			+ "<!ENTITY rfloor \"&#8971;\">\n" + "<!ENTITY lang \"&#9001;\">\n"
			+ "<!ENTITY rang \"&#9002;\">\n" + "<!ENTITY loz \"&#9674;\">\n"
			+ "<!ENTITY spades \"&#9824;\">\n"
			+ "<!ENTITY clubs \"&#9827;\">\n"
			+ "<!ENTITY hearts \"&#9829;\">\n"
			+ "<!ENTITY diams \"&#9830;\">\n";

	private Node document;

	private boolean addEntitiesDeclaration = true;
	private boolean bodyContentDelivered = false;
	private String xml;
	
	/**
	 * This constructor initializes the DOM with the provided xml.<br/>
	 * The default initialization does not include the HTML entities declarations
	 * in the DocType. Use the other constructor {@link #DOMHelper(String, boolean)}
	 * to have the possibility to include this declarations.
	 * 
	 * @param xml
	 *            the xml document
	 */
	public DOMHelper(String xml) {
		this(xml, true, false);
	}

	/**
	 * This constructor initializes the DOM with the provided xml and it is able
	 * to add HTML enitites declarations in the DocType.
	 * 
	 * @param xml
	 *            the xml document
	 * @param addEntitiesDeclaration
	 *            if it is true it adds HTML entities declarations in the
	 *            DocType
	 */
	public DOMHelper(String xml, boolean addEntitiesDeclaration) {
		this(xml, addEntitiesDeclaration, false);
	}

	/**
	 * This constructor initializes the DOM with the provided xml abd it is able
	 * to add HTML entities declarations in the DocType.<br/>
	 * If <code>bodyContentDelivered</code> is true the xml content provided is
	 * appended to the body of the HTML document.
	 * 
	 * @param xml
	 *            the xml document
	 * @param addEntitiesDeclaration
	 *            if true HTML entities declarations are aadded to the DocType
	 * @param bodyContentDelivered
	 *            if true the xml provided is appended to the body of the HTML
	 *            document
	 */
	public DOMHelper(String xml, boolean addEntitiesDeclaration,
			boolean bodyContentDelivered) {
		this.bodyContentDelivered = bodyContentDelivered;
		this.addEntitiesDeclaration = addEntitiesDeclaration;
		this.xml = xml;
	}

	/**
	 * This method sets the xml document.
	 * 
	 * @param xml
	 *            the xml document
	 */
	public void setXml(String xml) {
		this.xml = xml;
		this.document = null;
	}

	/**
	 * This method determines if the provided node generates a carriage return.
	 * 
	 * @param node
	 *            the node
	 * @return true if the node generates a carriage return
	 */
	public static boolean isReturnTag(Node node) {
		String nodeName = node.getNodeName().toLowerCase();
		return "br".equals(nodeName) // break row
				|| "hr".equals(nodeName) // horizontal rule
				// images in the text
				|| "img".equals(nodeName) //
				// titles
				|| "h1".equals(nodeName) // title level 1
				|| "h2".equals(nodeName) // title level 2
				|| "h3".equals(nodeName) // title level 3
				|| "h4".equals(nodeName) // title level 4
				|| "h5".equals(nodeName) // title level 5
				|| "h6".equals(nodeName) // title level 6
				|| "h7".equals(nodeName) // title level 7
				// paragraphs
				|| "p".equals(nodeName) //
				// tables
				|| "td".equals(nodeName) // columns
				|| "th".equals(nodeName) // table headers
				|| "caption".equals(nodeName) // table captions
		;
	}

	/**
	 * This method returns the plain text extracted from the xml provided to
	 * this helper.
	 * 
	 * @param luceneExtraction
	 *            true if you want Lucene compatibility
	 * @return the plain text extracted
	 */
	public String getAllPlainText(boolean luceneExtraction) {
		initDomDocument();
		return new DOMTextExtractor(document, luceneExtraction).getText();
	}

	/**
	 * This method returns the plain text extracted from the BODY of the HTML
	 * document.
	 * 
	 * @param luceneExtraction
	 *            true if you want Lucene compatibility
	 * @return the plain text
	 */
	public String getAllPlainTextFromBody(boolean luceneExtraction) {
		initDomDocument();
		return new DOMTextExtractor(getBodyNode(document), luceneExtraction)
				.getText();
	}

	/**
	 * This method adds the DocType declaration with HTML entities to the xml
	 * document.
	 * 
	 * @param xml
	 *            the xml document
	 * @return the xml document with the DocType declaration
	 */
	private String addDocTypeTo(String xml) {
		if (xml.indexOf("<!DOCTYPE") > -1)
			return xml;

		// FIXME this is a workaround to enable HTML entities management.
		// Consider the use of HTML parsers to avoid the inclusion of the HTML
		// entities declarations in the DocType

		String doctype = "<!DOCTYPE html [" + ENTITIES + "]>";

		if (xml.indexOf("<?xml") == -1)
			xml = doctype + xml;
		else {
			int index = xml.indexOf("?>", 1);
			xml = xml.substring(0, index + 2) + doctype
					+ xml.substring(index + 2);
		}
		return xml;
	}

	/**
	 * This method retrieves the BODY element form the DOM.
	 * 
	 * @param document
	 *            the DOM document
	 * @return the BODY element
	 */
	private Node getBodyNode(Node document) {
		Node html = navigateToFirstNodeByName(document, "html");
		if (html == null)
			throw new DOMHelperException(
					DOMHelperException.NOT_HTML,
					"The DOM corresponding to the provided XML does not seem to be a HTML document....it is impossible to proceed");
		Node body = navigateToFirstNodeByName(html, "body");
		if (body == null)
			throw new DOMHelperException(
					DOMHelperException.NOT_HTML,
					"The DOM corresponding to the provided XML have no BODY....it is impossible to proceed");
		return body;
	}

	/**
	 * This method creates the DOM corresponding to the provided xml.
	 * 
	 * @param xml
	 *            the xml document
	 * @return DOM the created DOM
	 * @throws Exception
	 */
	private Node getDOMDocument(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		ByteArrayInputStream encXML = new ByteArrayInputStream(
				xml.getBytes("UTF8"));

		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(encXML).getDocumentElement();
	}

	/**
	 * This method creates the DOM from the xml.
	 */
	private void initDomDocument() {
		if (document == null) {
			String xml = this.xml;
			if (bodyContentDelivered)
				xml = "<html><head></head><body>" + xml + "</body></html>";
			if (addEntitiesDeclaration)
				xml = addDocTypeTo(xml);
			try {
				document = getDOMDocument(xml);
			} catch (Exception e) {
				throw new DOMHelperException(
						DOMHelperException.CONVERSION_PROBLEM,
						"DOMHelper initialization: Problem during XML to DOM conversion");
			}
		}
	}

	/**
	 * This method retrieves the first node with the provided node name starting
	 * from the root node.
	 * 
	 * @param node
	 *            the root node
	 * @param nodeName
	 *            the searched node name
	 * @return the first node with the provide node name
	 */
	private Node navigateToFirstNodeByName(Node node, String nodeName) {
		if (node.getNodeName() != null
				&& node.getNodeName().equalsIgnoreCase(nodeName.toLowerCase()))
			return node;
		NodeList nodeList = node.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node child = nodeList.item(i);
			node = navigateToFirstNodeByName(child, nodeName);
			if (node != null)
				return node;
		}

		return null;
	}

}
