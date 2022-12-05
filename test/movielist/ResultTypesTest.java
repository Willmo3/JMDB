package movielist;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import media.ResultTypes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

/**
 * Tests the ResultTypes enumerated class.
 * 
 * @author Matthew Potter
 * @version 12/04/2022
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResultTypesTest
{
  private static ResultTypes testType = ResultTypes.INVALID;

  /**
   * Instantiates each enum for coverage.
   */
  @Test
  @Order(1)
  void testEachEnumString()
  {
    testType = ResultTypes.COMPANY;
    assertEquals("Company", testType.getType());
    testType = ResultTypes.EPISODE;
    assertEquals("Episode", testType.getType());
    testType = ResultTypes.KEYWORD;
    assertEquals("Keyword", testType.getType());
    testType = ResultTypes.NAME;
    assertEquals("Name", testType.getType());
    testType = ResultTypes.SERIES;
    assertEquals("Series", testType.getType());
    testType = ResultTypes.TITLE;
    assertEquals("Title", testType.getType());
  }

  /**
   * Tests the matchType function.
   */
  @Test
  @Order(2)
  void testMatchType()
  {
    assertEquals(ResultTypes.INVALID, testType.matchType(null));
    assertEquals(ResultTypes.INVALID, testType.matchType(""));
    assertEquals(ResultTypes.TITLE, testType.matchType("Title"));
    assertEquals(ResultTypes.INVALID, testType.matchType("Not Real"));
  }
}
