package com.service;

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

		// N1 kanji list from http://www.tanos.co.uk/jlpt/skills/kanji/

		String listLocation = "jlptN1Kanji.txt";
		String xmlLocation = "https://rss.wor.jp/rss1/yomiuri/latestnews.rdf";

		String kanjiList = readKanjiList(listLocation);
		if (kanjiList.isEmpty()) {
			System.out.println("Kanji list is empty.");
		}

		String content = readFromRSS(xmlLocation, kanjiList);
		if (content.isEmpty()) {
			System.out.println("Nothing read from RSS feed.");
		}
		createTextFile(file, content);
	}

	/**
	 * @param file
	 * @param content
	 */
	public static void createTextFile(File file, String content) {
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

	/**
	 * @param rss
	 * @param kanjiList
	 * @return
	 * @throws IOException
	 */
	public static String readFromRSS(String rss, String kanjiList) throws IOException {
		// try {
		URL rssUrl = new URL(rss);
		BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream(), "UTF-8"));
		StringBuilder outputContent = new StringBuilder();
		String line;
		boolean isGoodTitle = false;

		while ((line = in.readLine()) != null) {
			// adds title of news article with selected kanji
			if (line.contains("<title>")) {

				int firstPos = line.indexOf("<title>");
				String temp = line.substring(firstPos);
				temp = temp.replace("<title>", "");
				int lastPos = temp.indexOf("</title>");
				temp = temp.substring(0, lastPos);

				for (char ch : kanjiList.toCharArray()) {
					String singleKanji = Character.toString(ch);
					StringBuilder listOfFoundKanji = new StringBuilder();

					if (temp.contains(singleKanji)) {
						listOfFoundKanji.append(singleKanji);

						System.out.println("current list:" + listOfFoundKanji.toString());

						outputContent.append(temp + " [Found this kanji: " + singleKanji + "]" + "\n");
						isGoodTitle = true;
						// break;
					}
				}
			}
			// adds html link to the article
			if (line.contains("<link>") && isGoodTitle == true) {

				isGoodTitle = false;
				int firstPos = line.indexOf("<link>");
				String temp = line.substring(firstPos);
				temp = temp.replace("<link>", "");
				int lastPos = temp.indexOf("</link>");
				temp = temp.substring(0, lastPos);
				outputContent.append(temp + "\n\n");
			}
		}

		in.close();
		return outputContent.toString();
	}

	/**
	 * @param list
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readKanjiList(String list) throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(list))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			return sb.toString();

		}
	}

}
