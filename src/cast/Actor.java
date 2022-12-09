package cast;

/**
 * A class representing an actor who played a role in a piece of media.
 * 
 * @author Matthew Potter
 * @version 12/09/2022
 */
public class Actor extends Worker
{

  /**
   * Constructs an actor with the passed job and name.
   * 
   * @param name
   *          the name of this worker
   * @param role
   *          the role of this actor in a piece of media
   */
  public Actor(String name, String role)
  {
    super(name, role);
  }
  
  @Override
  public String toString()
  {
    // in this instance, description stores the role of the actor
    return String.format("%s as %s", name, description);
  }
}
