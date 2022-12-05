package media;

/**
 * The defined type of movies that can be returned by a searchAll.
 * 
 * @author Matthew Potter
 * @version 12/04/2022
 */
public enum ResultTypes
{
  /**
   * A title, such as a film.
   */
  TITLE("Title"),

  /**
   * A TV series.
   */
  SERIES("Series"),

  /**
   * A person.
   */
  NAME("Name"),

  /**
   * A TV series episode.
   */
  EPISODE("Episode"),

  /**
   * A company.
   */
  COMPANY("Company"),

  /**
   * A keyword.
   */
  KEYWORD("Keyword"),

  /**
   * Denotes an Invalid result type. An Invalid result type is one that matches
   * none of the above enumerated values.
   */
  INVALID("INVALID");

  private String type;

  private ResultTypes(String type)
  {
    this.type = type;
  }

  /**
   * Getter for the MovieTypes' text version.
   * 
   * @return the text of each movie type
   */
  public String getType()
  {
    return type;
  }

  /**
   * Converts the passed typeString into the matching ResultTypes enum if it
   * matches one of the valid choices.
   * 
   * @param typeString
   *          the String denoting the result type received from the search
   * @return the matching ResultTypes, or INVALID if invalid
   */
  public ResultTypes matchType(String typeString)
  {
    if (typeString == null || typeString.isEmpty())
    {
      return INVALID;
    }
    for (ResultTypes cur : ResultTypes.values())
    {
      if (typeString.equals(cur.getType()))
      {
        return cur;
      }
    }
    return INVALID;
  }
}
