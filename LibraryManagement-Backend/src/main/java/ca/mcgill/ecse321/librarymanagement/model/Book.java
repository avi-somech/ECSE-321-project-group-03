/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarymanagement.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;


// line 65 "model.ump"
// line 156 "model.ump"

@Entity
public class Book extends Title
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Book Attributes

  @Id
  private String aTitleId;
  
  private String author;
  private Date publicationDate;
  private String synopsis;
  private String genre;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  
  public Book(Date aReleaseDate, String aImage, String aName, String aTitleID, String aAuthor, String aSynopsis, String aGenre)
  {
    super(aReleaseDate, aImage, aName, aTitleID);
    this.aTitleId = aTitleID;
    author = aAuthor;
    synopsis = aSynopsis;
    genre = aGenre;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAuthor(String aAuthor)
  {
    boolean wasSet = false;
    author = aAuthor;
    wasSet = true;
    return wasSet;
  }

  public boolean setPublicationDate(Date aPublicationDate)
  {
    boolean wasSet = false;
    publicationDate = aPublicationDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setSynopsis(String aSynopsis)
  {
    boolean wasSet = false;
    synopsis = aSynopsis;
    wasSet = true;
    return wasSet;
  }

  public boolean setGenre(String aGenre)
  {
    boolean wasSet = false;
    genre = aGenre;
    wasSet = true;
    return wasSet;
  }

  public String getAuthor()
  {
    return author;
  }

  public Date getPublicationDate()
  {
    return publicationDate;
  }

  public String getSynopsis()
  {
    return synopsis;
  }

  public String getGenre()
  {
    return genre;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "author" + ":" + getAuthor()+ "," +
            "synopsis" + ":" + getSynopsis()+ "," +
            "genre" + ":" + getGenre()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "publicationDate" + "=" + (getPublicationDate() != null ? !getPublicationDate().equals(this)  ? getPublicationDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}