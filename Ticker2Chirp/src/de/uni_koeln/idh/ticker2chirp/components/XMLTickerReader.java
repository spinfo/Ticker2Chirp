package de.uni_koeln.idh.ticker2chirp.components;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.uni_koeln.idh.ticker2chirp.data.FifaCodes;
import de.uni_koeln.idh.ticker2chirp.data.FootballMatch;

/**
 * Class that reads Ticker messages from files with a special XML format that are 
 * provided by http://fussballlinguistik.de/korpora/
 * @author jhermes
 *
 */
public class XMLTickerReader {
	
	private FifaCodes codes;
	private SortedMap<String, Integer> hashtags;
	private File outputFolder; 
	
	/**
	 * Generated a new XMLTickerReader object. Needs FifaCodes and an output folder.
	 * @param codes
	 * @param outputFolderName
	 */
	public XMLTickerReader(FifaCodes codes, String outputFolderName) {
		this.codes = codes;
		this.hashtags = new TreeMap<String,Integer>();
		outputFolder = new File(outputFolderName);
		if(!outputFolder.exists()) {
			outputFolder.mkdirs();
		}
	}

	/**
	 * Reads the specified XML file and produces the result Map.
	 * @param fileName destination of the XML-input-File
	 * @param export If true, Games will be serialized in the output folder
	 * @return A Map with hashtags as keys and lists of FootballMatches with these hashtags. 
	 */
	public Map<String,List<FootballMatch>> processFile(String fileName, boolean export) {
		Map<String,List<FootballMatch>> toReturn = new TreeMap<String,List<FootballMatch>>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			File file = new File(fileName);
			if (file.exists()) {
				Document doc = db.parse(file);
				Element docEle = doc.getDocumentElement();

				// Print root element of the document
				System.out.println("Root element of the document: "
						+ docEle.getNodeName());

				NodeList games = docEle.getElementsByTagName("text");

				// Print total student elements in document
				System.out
						.println("Total games: " + games.getLength());

				if (games != null && games.getLength() > 0) {
					for (int i = 0; i < games.getLength(); i++) {

						Node node = games.item(i);
						String ht1 = "";
						String ht2 = "";

						if (node.getNodeType() == Node.ELEMENT_NODE) {

							System.out
									.println("=====================");

							Element e = (Element) node;
							NodeList nodeList = e.getElementsByTagName("url");
							String url = nodeList.item(0).getChildNodes().item(0).getNodeValue();

							nodeList = e.getElementsByTagName("title");
							String title = nodeList.item(0).getChildNodes().item(0).getNodeValue();
							
							nodeList = e.getElementsByTagName("team1");
							String homeTeam = nodeList.item(0).getChildNodes().item(0).getNodeValue();
							
							nodeList = e.getElementsByTagName("team2");
							String awayTeam = nodeList.item(0).getChildNodes().item(0).getNodeValue();
							
							nodeList = e.getElementsByTagName("date");
							String date = nodeList.item(0).getChildNodes().item(0).getNodeValue();

							nodeList = e.getElementsByTagName("kickoff");
							String kickoff = nodeList.item(0).getChildNodes().item(0).getNodeValue();
							
							FootballMatch game = new FootballMatch(url, title, homeTeam, awayTeam, date, kickoff, codes);
							
							ht1=game.getHashtag();
							Integer count = hashtags.put(ht1,1);
							
							if(count!=null) {
								hashtags.put(ht1,count+1);
							}
							ht2=game.getAlterHashtag();
							count = hashtags.put(ht2,1);
							
							if(count!=null) {
								hashtags.put(ht2,count+1);
							}
							
							
							NodeList minuteList = e.getElementsByTagName("time");
							
							NodeList messageList = e.getElementsByTagName("p");
							
							System.out.println(minuteList.getLength() + "\t" + messageList.getLength()); 
							for(int j=0; j<minuteList.getLength();j++) {
								String minute = "0'";
								try {
									minute = minuteList.item(j).getChildNodes().item(0).getNodeValue();
								}
								catch(Exception ex) {
									//Pre match messages
									minute = "0'";
								}
								String message = "";
								try {
									message = messageList.item(j).getChildNodes().item(0).getNodeValue();
								}
								catch(Exception ex) {
									//Empty message
									continue;
								}							
								game.addMessage(minute, message);
								
							}
							
							List<FootballMatch> inGames = toReturn.get(game.getHashtag());
							if(inGames== null) {
								inGames = new ArrayList<FootballMatch>();
							}
							inGames.add(game);
							toReturn.put(game.getHashtag(), inGames);
							toReturn.put(game.getAlterHashtag(), inGames);
							
							if (export) {
								File outputFile = new File(outputFolder.getName() + "/" + ht1);
								while (outputFile.exists()) {
									outputFile = new File(outputFile + "_");
								}
								System.out.println(outputFile.getAbsolutePath());
								try {
									PrintWriter out = new PrintWriter(new FileWriter(outputFile));
									out.println(game.export(ht1));
									out.flush();
									out.close();
								} catch (Exception f) {
									f.printStackTrace();
								}
								outputFile = new File(outputFolder.getName() + "/" + ht2);
								while (outputFile.exists()) {
									outputFile = new File(outputFile + "_");
								}
								System.out.println(outputFile.getAbsolutePath());
								try {
									PrintWriter out = new PrintWriter(new FileWriter(outputFile));
									out.println(game.export(ht2));
									out.flush();
									out.close();
								} catch (Exception f) {
									f.printStackTrace();
								}
								//System.out.println(game.export());
							}
							
							
						}
					}
				} else {
					System.exit(1);
				}
				printHashTags();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	/**
	 * Prints all hashtags with count of matches to the console.
	 */
	public void printHashTags() {
		Set<String> keySet = hashtags.keySet();
		for (String string : keySet) {
			System.out.println(string + " " + hashtags.get(string));
		}
		
	}

}
