package media;

import search.JsonRequestor;

/**
 * Represents a single movie as specified in the IMDB API. Note: Currently, only
 * supports JSON files as described in the Title Search Results. This may be
 * changed later.
 *
 * @author Will Morris, Matthew Potter, Immanuel Semelfort
 * @version 12/08/2022
 */
public class Movie
{

  /**
   * Sentinel value for any rating in the ratings array.
   */
  public static final double NO_RATING = Double.NEGATIVE_INFINITY;
  /**
   * Sentinel string for trailer links.
   */
  public static final String DEFAULT_TRAILER = "DEFAULT_TRAILER";

  /**
   * String indicating that a queried movie has no YouTube trailer on IMDb.
   */
  public static final String NO_WIKI = "NO_WIKI";

  /**
   * Sentinel string for awards.
   */
  public static final String DEFAULT_AWARD = "DEFAULT_AWARD";

  /**
   * Sentinel string for wiki links.
   */
  public static final String DEFAULT_WIKI = "DEFAULT_WIKI";

  /**
   * Sentinel string for crew.
   */
  public static final String DEFAULT_CREW = "DEFAULT_CREW";

  private String id;
  private ResultTypes type;
  private String imageLink;
  private String title;
  private String description;
  private double[] ratings;
  private String trailerLink;
  private String award;
  private String wiki;
  private String crew;
  private boolean retrievedRatings;

  /**
   * Default constructor.
   */
  public Movie()
  {
    id = null;
    type = null;
    imageLink = null;
    title = null;
    description = null;
    ratings = null;
    trailerLink = DEFAULT_TRAILER;
    award = DEFAULT_AWARD;
    wiki = DEFAULT_WIKI;
    crew = DEFAULT_CREW;
    retrievedRatings = false;
  }

  /**
   * Explicit value constructor. Sets up a Movie with the information given by a
   * single search query.
   * 
   * @param id
   *          the Movie's ID on IMDb
   * @param type
   *          the Media's type as defined by the search
   * @param imageLink
   *          the link to the Movie's cover image
   * @param title
   *          the title of the Movie
   * @param description
   *          the IMDb search description of the Movie
   */
  public Movie(String id, ResultTypes type, String imageLink, String title,
      String description)
  {
    this.id = id;
    this.type = type;
    this.imageLink = imageLink;
    this.title = title;
    this.description = description;
    this.ratings = null;
    this.trailerLink = DEFAULT_TRAILER;
    this.award = DEFAULT_AWARD;
    this.wiki = DEFAULT_WIKI;
    this.crew = DEFAULT_CREW;
    this.retrievedRatings = false;
  }

  /**
   * Explicit value constructor for every value. Sets up a Movie with the
   * associated information.
   * 
   * @param id
   *          the Movie's ID on IMDb
   * @param type
   *          the Media's type as defined by the search
   * @param imageLink
   *          the link to the Movie's cover image
   * @param title
   *          the title of the Movie
   * @param description
   *          the IMDb search description of the Movie
   * @param ratings
   *          the list of doubles defining this Movie's ratings
   * @param trailerLink
   *          the link to the trailer of this Movie
   * @param award
   *          the awards this Movie has won
   * @param wiki
   *          the wikipedia link of this Movie
   * @param crew
   *          the crew list of this Movie
   * @param retrievedRatings
   *          whether the movie's ratings have been retrieved yet
   */
  public Movie(String id, ResultTypes type, String imageLink, String title,
      String description, double[] ratings, String trailerLink, String award,
      String wiki, String crew, boolean retrievedRatings)
  {
    this.id = id;
    this.type = type;
    this.imageLink = imageLink;
    this.title = title;
    this.description = description;
    this.ratings = ratings;
    this.trailerLink = trailerLink;
    this.award = award;
    this.wiki = wiki;
    this.crew = crew;
    this.retrievedRatings = retrievedRatings;
  }

  /**
   * Getter for id.
   * 
   * @return the id
   */
  public String getId()
  {
    return id;
  }

  /**
   * Getter for the Media's type.
   * 
   * @return the Media's type
   */
  public ResultTypes getType()
  {
    return type;
  }

  /**
   * Getter for imageLink.
   * 
   * @return the image link string
   */
  public String getImageLink()
  {
    return imageLink;
  }

  /**
   * Getter for title.
   * 
   * @return the title
   */
  public String getTitle()
  {
    return title;
  }

  /**
   * Getter for the search description.
   * 
   * @return the search description
   */
  public String getDescription()
  {
    return description;
  }

  /**
   * Fetches the rating list for a movie. Grabs from internet if one does not
   * already exist. The returned rating list contains the ratings order as:
   * IMDb, Metacritic, the Movie Database, Rotten Tomatoes, and Film Affinity.
   * Any rating may be null if that data was not available. If all values are
   * null, the ratings array itself should be null.
   *
   * @return The array of all ratings.
   */
  public double[] getRatings()
  {
    if (!retrievedRatings)
    {
      ratings = JsonRequestor.queryRating(id);
    }
    retrievedRatings = true;
    return ratings;
  }

  /**
   * Fetches the trailer for a movie. Grabs one from the internet if the link's
   * information has not already been retrieved from the internet.
   *
   * @return The link to the trailer on YouTube, or null if no trailer exists.
   */
  public String getTrailerLink()
  {
    // the Media has no trailer associated with it on IMDb
    if (trailerLink == null || trailerLink.isBlank())
    {
      trailerLink = null;
      return trailerLink;
    }

    // the Media hasn't had its trailer set yet
    if (trailerLink.equals(DEFAULT_TRAILER))
    {
      trailerLink = JsonRequestor.queryTrailer(id);
    }
    return trailerLink;
  }

  /**
   * Fetches the Awards for a movie. Grabs one from the Internet if one does not
   * already exist.
   *
   * @return Awards.
   */
  public String getAward()
  {
    if (award == null || award.isBlank())
    {
      award = null;
      return award;
    }
    if (award != null && award.equals(DEFAULT_AWARD))
    {
      award = JsonRequestor.queryAwards(id);
    }
    return award;
  }

  /**
   * Fetches the wikipedia page for a movie. Grabs one from the internet if the
   * link's information has not already been retrieved from the internet.
   *
   * @return The link to the wikipedia page, or null if no Wikipedia page
   *         exists.
   */
  public String getWiki()
  {
    if (wiki == null || wiki.isBlank())
    {
      wiki = null;
      return wiki;
    }

    if (wiki.equals(DEFAULT_WIKI))
    {
      wiki = JsonRequestor.queryWiki(id);
    }
    return wiki;
  }

  /**
   * Fetches the crew for a movie. Grabs one from the Internet if one does not
   * already exist.
   *
   * @return crew.
   */
  public String getCrew()
  {
    if (crew == null || crew.isBlank())
    {
      crew = null;
      return crew;
    }

    if (crew != null && crew.equals(DEFAULT_CREW))
    {
      crew = JsonRequestor.queryCrew(id);
    }
    return crew;
  }

  /**
   * Getter for retrievedRatings boolean.
   * 
   * @return whether the Movie's ratings have been retrieved yet.
   */
  public boolean isRetrievedRatings()
  {
    return retrievedRatings;
  }

  /**
   * Setter for ID.
   * 
   * @param id
   *          the ID to set this Media's ID to
   */
  public void setId(String id)
  {
    this.id = id;
  }

  /**
   * Setter for type.
   * 
   * @param type
   *          the ResultType to set this Media's type to
   */
  public void setType(ResultTypes type)
  {
    this.type = type;
  }

  /**
   * Setter for imageLink.
   * 
   * @param imageLink
   *          the String URL to set this Media's imageLink to
   */
  public void setImageLink(String imageLink)
  {
    this.imageLink = imageLink;
  }

  /**
   * Setter for title.
   * 
   * @param title
   *          the title to set this Media's title to
   */
  public void setTitle(String title)
  {
    this.title = title;
  }

  /**
   * Setter for description.
   * 
   * @param description
   *          the description to set this Media's description to
   */
  public void setDescription(String description)
  {
    this.description = description;
  }

  /**
   * Setter for ratings list. Makes ratings null if the passed list is
   * completely empty (all values are NO_RATING).
   * 
   * @param ratings
   *          the array of all ratings to set this array to, or null if the
   *          passed array is fully empty.
   */
  public void setRatings(double[] ratings)
  {
    // state that we have now set the ratings for this object
    this.retrievedRatings = true;
    // check for passing null list
    if (ratings == null)
    {
      this.ratings = null;
      return;
    }
    // check to see if the passed array has any ratings that are set
    for (RatingTypes rating : RatingTypes.values())
    {
      double curRating = ratings[rating.ordinal()];
      if (Double.isFinite(curRating))
      {
        // if the passed array has a set value, set ratings to it
        this.ratings = ratings;
        return;
      }
    }
    // if the passed array has no ratings, set ratings to null
    this.ratings = null;
  }

  /**
   * Setter for trailerLink.
   * 
   * @param trailerLink
   *          the YouTube trailer URL to set this Media's trailerLink to
   */
  public void setTrailerLink(String trailerLink)
  {
    this.trailerLink = trailerLink;
  }

  /**
   * Setter for award.
   * 
   * @param award
   *          the award to set this Media's award to
   */
  public void setAward(String award)
  {
    this.award = award;
  }

  /**
   * Setter for wiki.
   * 
   * @param wiki
   *          the Wikipedia URL to set this Media's Wikipedia link to
   */
  public void setWiki(String wiki)
  {
    this.wiki = wiki;
  }

  /**
   * Setter for crew.
   * 
   * @param crew
   *          the crew list to set this Media's crew list to
   */
  public void setCrew(String crew)
  {
    this.crew = crew;
  }

  /**
   * Setter for retrievedRatings boolean.
   * 
   * @param retrievedRatings
   *          whether this Movie's ratings list has been set.
   */
  public void setRetrievedRatings(boolean retrievedRatings)
  {
    this.retrievedRatings = retrievedRatings;
  }

  @Override
  public boolean equals(Object o)
  {
    if (o instanceof Movie)
    {
      // Make sure to not use pattern variables, since some versions of java
      // will complain.
      Movie movie = (Movie) o;
      return id.equals(movie.id) && imageLink.equals(movie.imageLink)
          && title.equals(movie.title) && description.equals(movie.description);
    }

    return false;
  }

  /**
   * Returns String representation of this movie. Format: title description;
   *
   * @return The formatted String.
   */
  @Override
  public String toString()
  {
    return title + " " + description;
  }
}
