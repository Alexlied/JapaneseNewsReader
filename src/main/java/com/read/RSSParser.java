package com.read;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class RSSParser {

	/**
	 * @param list
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String readKanjiList(String listLocation) throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(listLocation))) {
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
