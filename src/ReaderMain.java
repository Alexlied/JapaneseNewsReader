import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class ReaderMain {

	public static void main(String[] args) throws IOException {

		File file = new File("newfile.txt");

		// readFromRSS("http://rss.asahi.com/rss/asahi/culture.rdf");
		// N1 kanji list from http://www.tanos.co.uk/jlpt/skills/kanji/

		String kanjiList = readKanjiList();
		String content = readFromRSS(
				"http://headlines.yahoo.co.jp/rss/bfj-dom.xml", kanjiList);
		createAndProcessRSS(file, content);
	}

	public static void createAndProcessRSS(File file, String content) {
		try (FileOutputStream fop = new FileOutputStream(file)) {

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			byte[] contentInBytes = content.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String readFromRSS(String rss, String kanji)
			throws IOException {
		// try {
		URL rssUrl = new URL(rss);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				rssUrl.openStream(), "UTF-8"));
		String sourceCode = "";
		String line;

		while ((line = in.readLine()) != null) {
			if (line.contains("<title>")) {

				int firstPos = line.indexOf("<title>");
				String temp = line.substring(firstPos);
				temp = temp.replace("<title>", "");
				int lastPos = temp.indexOf("</title>");
				temp = temp.substring(0, lastPos);

				for (char ch : kanji.toCharArray()) {
					String singleKanji = Character.toString(ch);

					if (temp.contains(singleKanji)) {
						System.err.println(singleKanji + " is here");
						sourceCode += temp + " [Found this kanji: "
								+ singleKanji + "]" + "\n";
						break;
					}
				}
			}
		}

		in.close();
		return sourceCode;
	}

	public static String readKanjiList() throws FileNotFoundException,
			IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(
				"jlptN1Kanji.txt"))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			return sb.toString();
			// System.out.println(everything);
		}
	}

}
