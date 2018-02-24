package com.read;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.FileNotFoundException;
import org.junit.Test;

public class RSSParserTest {

	@Test(expected = FileNotFoundException.class)
	public void whenReadingMissingKanjiListLocationReturnNull() throws IOException {
		// Arrange
		String listLocation = "falseLocation.txt";
		
		RSSParser parser = new RSSParser();
		
		// Act
		parser.readKanjiList(listLocation);
		
		// Assert
		
	}

}
