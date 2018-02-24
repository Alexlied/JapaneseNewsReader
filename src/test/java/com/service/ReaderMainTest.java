package com.service;

//import static org.hamcrest.MatcherAssert.assertThat;


import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class ReaderMainTest {

	@Test(expected = FileNotFoundException.class)
	public void whenReadingMissingKanjiListLocationReturnNull() throws IOException {
		// Arrange
		String listLocation = "falseLocation.txt";
		
		// Act
		ReaderMain.readKanjiList(listLocation);
		
		// Assert
		
	}

}
