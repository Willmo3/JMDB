package cast;

/**
 * Class representing a person who worked in some way on a piece of media.
 * 
 * @author Matthew Potter
 * @version 12/09/2022
 */
public class Worker
{
  protected String name;
  protected String description;

  /**
   * Constructs a worker with the passed job and name.
   * 
   * @param name
   *          the name of this worker
   * @param description
   *          the description of this worker's job
   */
  public Worker(String name, String description)
  {
    this.name = name;
    this.description = description;
    if (description == null || description.isBlank())
    {
      this.description = null;
    }
  }

  @Override
  public String toString()
  {
    // if a description is given, state it, otherwise state the name only
    if (description == null)
    {
      return name;
    }
    return String.format("%s %s", description, name);
  }
}
