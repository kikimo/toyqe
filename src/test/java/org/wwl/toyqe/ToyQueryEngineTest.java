package org.wwl.toyqe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import net.sf.jsqlparser.parser.ParseException;

public class ToyQueryEngineTest {

	public void setUp() {
		
	}

	@Test
	public void testCreateTable() {
		QueryEngine queryEngine = new ToyQueryEngine();
		String createTable = "CREATE TABLE R(A int, B int, C int);";
		try {
			String ret = queryEngine.execute(createTable);
			assertEquals("table R created.", ret);
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testSimpleSelect() {
		// TODO: don't use singleton meta
		QueryEngine queryEngine = new ToyQueryEngine();
		String createTable = "CREATE TABLE R (A int, B int);";
		String query = "SELECT A FROM R WHERE A > 4";
		
		try {
			queryEngine.execute(createTable);
			String result = queryEngine.execute(query);
			// TODO: check result
			System.out.println(result);
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
}
