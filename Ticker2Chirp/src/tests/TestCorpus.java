package tests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import de.uni_koeln.idh.ticker2chirp.components.XMLTickerReader;
import de.uni_koeln.idh.ticker2chirp.data.FifaCodes;
import de.uni_koeln.idh.ticker2chirp.data.FootballMatch;

public class TestCorpus {

	@Test
	public void testCorpus() {
		String 	corpusFilePath = "data/buli0607.xml";

		String fifaCodesFilePath = "data/FifaCodes"; 
	
		FifaCodes codes = new FifaCodes(fifaCodesFilePath);
		XMLTickerReader reader = new XMLTickerReader(codes, "ticker");
		Map<String, List<FootballMatch>> result = reader.processFile(corpusFilePath, false);	
		Set<String> keySet = result.keySet();
		for (String string : keySet) {
			System.out.print(string + "\t");
			List<FootballMatch> list = result.get(string);
			for (FootballMatch footballMatch : list) {
				System.out.print(footballMatch.getDate().substring(footballMatch.getDate().length()-4) +" ");
			}
			System.out.println();
		}
		
	}

}
