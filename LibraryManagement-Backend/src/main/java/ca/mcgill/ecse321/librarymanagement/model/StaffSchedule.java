/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarymanagement.model;

import java.util.*;

// line 21 "model.ump"
// line 121 "model.ump"
public class StaffSchedule extends Schedule
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //StaffSchedule Associations
  private List<Librarian> librarians;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public StaffSchedule()
  {
    super();
    librarians = new ArrayList<Librarian>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Librarian getLibrarian(int index)
  {
    Librarian aLibrarian = librarians.get(index);
    return aLibrarian;
  }

  public List<Librarian> getLibrarians()
  {
    List<Librarian> newLibrarians = Collections.unmodifiableList(librarians);
    return newLibrarians;
  }

  public int numberOfLibrarians()
  {
    int number = librarians.size();
    return number;
  }

  public boolean hasLibrarians()
  {
    boolean has = librarians.size() > 0;
    return has;
  }

  public int indexOfLibrarian(Librarian aLibrarian)
  {
    int index = librarians.indexOf(aLibrarian);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLibrarians()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Librarian addLibrarian(String aUsername, String aPassword, String aEmailaddress, String aFullName, String aResAddress, int aUserId, Library aLibrary)
  {
    return new Librarian(aUsername, aPassword, aEmailaddress, aFullName, aResAddress, aUserId, aLibrary, this);
  }

  public boolean addLibrarian(Librarian aLibrarian)
  {
    boolean wasAdded = false;
    if (librarians.contains(aLibrarian)) { return false; }
    StaffSchedule existingStaffSchedule = aLibrarian.getStaffSchedule();
    boolean isNewStaffSchedule = existingStaffSchedule != null && !this.equals(existingStaffSchedule);
    if (isNewStaffSchedule)
    {
      aLibrarian.setStaffSchedule(this);
    }
    else
    {
      librarians.add(aLibrarian);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLibrarian(Librarian aLibrarian)
  {
    boolean wasRemoved = false;
    //Unable to remove aLibrarian, as it must always have a staffSchedule
    if (!this.equals(aLibrarian.getStaffSchedule()))
    {
      librarians.remove(aLibrarian);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLibrarianAt(Librarian aLibrarian, int index)
  {  
    boolean wasAdded = false;
    if(addLibrarian(aLibrarian))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLibrarians()) { index = numberOfLibrarians() - 1; }
      librarians.remove(aLibrarian);
      librarians.add(index, aLibrarian);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLibrarianAt(Librarian aLibrarian, int index)
  {
    boolean wasAdded = false;
    if(librarians.contains(aLibrarian))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLibrarians()) { index = numberOfLibrarians() - 1; }
      librarians.remove(aLibrarian);
      librarians.add(index, aLibrarian);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLibrarianAt(aLibrarian, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=librarians.size(); i > 0; i--)
    {
      Librarian aLibrarian = librarians.get(i - 1);
      aLibrarian.delete();
    }
    super.delete();
  }

}