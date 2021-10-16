/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarymanagement.model;

import java.sql.Date;

import javax.persistence.Id;

// line 57 "model.ump"
// line 151 "model.ump"
public abstract class Title
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Title Attributes
  private Date releaseDate;
  private String image;
  private String name;
  private String titleID;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Title(Date aReleaseDate, String aImage, String aName, String aTitleID)
  {
    releaseDate = aReleaseDate;
    image = aImage;
    name = aName;
    titleID = aTitleID;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setReleaseDate(Date aReleaseDate)
  {
    boolean wasSet = false;
    releaseDate = aReleaseDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setImage(String aImage)
  {
    boolean wasSet = false;
    image = aImage;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setTitleID(String aTitleID)
  {
    boolean wasSet = false;
    titleID = aTitleID;
    wasSet = true;
    return wasSet;
  }

  public Date getReleaseDate()
  {
    return releaseDate;
  }

  public String getImage()
  {
    return image;
  }

  public String getName()
  {
    return name;
  }

  @Id
  public String getTitleID()
  {
    return titleID;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "image" + ":" + getImage()+ "," +
            "name" + ":" + getName()+ "," +
            "titleID" + ":" + getTitleID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "releaseDate" + "=" + (getReleaseDate() != null ? !getReleaseDate().equals(this)  ? getReleaseDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}