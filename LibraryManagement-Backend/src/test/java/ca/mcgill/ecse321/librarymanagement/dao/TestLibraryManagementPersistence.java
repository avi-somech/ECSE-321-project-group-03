package ca.mcgill.ecse321.librarymanagement.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.librarymanagement.LibraryManagementApplication;
import ca.mcgill.ecse321.librarymanagement.model.Book;
import ca.mcgill.ecse321.librarymanagement.model.Library;
import ca.mcgill.ecse321.librarymanagement.model.LibrarySchedule;
import ca.mcgill.ecse321.librarymanagement.model.Room;
import ca.mcgill.ecse321.librarymanagement.model.RoomSchedule;
import ca.mcgill.ecse321.librarymanagement.model.Schedule;
import ca.mcgill.ecse321.librarymanagement.model.TimeSlot;
import ca.mcgill.ecse321.librarymanagement.model.User;
import ca.mcgill.ecse321.librarymanagement.model.Librarian;
import ca.mcgill.ecse321.librarymanagement.model.HeadLibrarian;
import ca.mcgill.ecse321.librarymanagement.model.MusicAlbum;
import ca.mcgill.ecse321.librarymanagement.model.Movie;
import ca.mcgill.ecse321.librarymanagement.model.Newspaper;
import ca.mcgill.ecse321.librarymanagement.model.StaffSchedule;
import ca.mcgill.ecse321.librarymanagement.model.Title;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestLibraryManagementPersistence {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private MusicAlbumRepository musicAlbumRepository;
	
	@Autowired
	private NewspaperRepository newspaperRepository;
	
	@Autowired
	private LibraryScheduleRepository libraryScheduleRepository;
	
	@Autowired
	private RoomScheduleRepository roomScheduleRepository;
	
	@Autowired
	private StaffScheduleRepository staffScheduleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HeadLibrarianRepository headLibrarianRepository;
	
	@Autowired
	private LibrarianRepository librarianRepository;
	
	@Autowired
	private TimeSlotRepository timeSlotRepository;
	
	@Autowired
	private LibraryRepository libraryRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	
	@BeforeEach
	@AfterEach
	public void clearDatabase() {
		timeSlotRepository.deleteAll();
		bookRepository.deleteAll();
		movieRepository.deleteAll();
		musicAlbumRepository.deleteAll();
		newspaperRepository.deleteAll();
		roomRepository.deleteAll();
		headLibrarianRepository.deleteAll();
		librarianRepository.deleteAll();
		userRepository.deleteAll();
		libraryRepository.deleteAll();
		roomScheduleRepository.deleteAll();
		staffScheduleRepository.deleteAll();
		libraryScheduleRepository.deleteAll();
	}
	
	/*
	 * 
	 * sam
	 * 
	 * 
	 * */
	
	@Test
	public void testPersistAndLoadBook() {
		
//		//Create all constructor fields
		Date date = new Date(2001, 5, 1);	//Lees birthday in case you wanna get him a gift. 
		String image = "imageLink";
		String bookName = "bookName";
		String author = "author";
		String synopsis = "synopsis";
		String genre = "genre";
		
		//Create Book object with parameters. ^^
		Book book = new Book(date, image, bookName, author, synopsis, genre);
				
		//Save the book to the DB.
		Book savedBook = bookRepository.save(book);
		int savedBookId = savedBook.getTitleId();
	
		//Get rid of the book.
		book = null;
		
		//Use the CRUD method to query the book from the DB. 
		book = bookRepository.findBookByTitleId(savedBookId);
						
		//Check that the object was properly queried from DB.
		assertNotNull(book);
		
		//Test that all the data was properly saved.
		assertEquals(date, book.getReleaseDate());
		assertEquals(image, book.getImage());
		assertEquals(bookName, book.getName());
		assertEquals(author, book.getAuthor());
		assertEquals(synopsis, book.getSynopsis());
		assertEquals(genre, book.getGenre());

	}
	
	@Test
	public void testPersistAndLoadNewspaper() {
		
	}
	
	@Test
	public void testPersistAndLoadMusicAlbum() {
		
	}
	
	@Test
	public void testPersistAndLoadMovie() {
		
	}
	
	
	/*
	 * 
	 * adam
	 * 
	 * 
	 * */
	
	@Test
	public void testPersistAndLoadUser() {
		
	}
	
	@Test
	public void testPersistAndLoadLibrarian() {
		
	}
	

	
	/*
	 * 
	 * liam
	 * 
	 * 
	 * */
	
	
	@Test
	public void testPersistAndLoadStaffSchedule() {
		
	}
	
	@Test
	public void testPersistAndLoadHeadLibrarian() {
		
	}
	
	/*
	 * 
	 * tara
	 * 
	 * 
	 * */
	
	@Test
	public void testPersistAndLoadRoom() {
//		
//		RoomSchedule roomSchedule = new RoomSchedule();
//		LibrarySchedule librarySchedule = new LibrarySchedule();
//		Library library = new Library(librarySchedule);
//		Room room = new Room(roomSchedule, library);
//		
//		LibrarySchedule savedLibrarySchedule = libraryScheduleRepository.save(librarySchedule);
//		Library savedLibrary = libraryRepository.save(library);
//		RoomSchedule savedRoomSchedule = roomScheduleRepository.save(roomSchedule);
//		Room savedRoom = roomRepository.save(room);
//
//		int savedLibraryScheduleId = savedLibrarySchedule.getScheduleId();
//		int savedLibraryId = savedLibrary.getLibraryId();
//		int savedRoomScheduleId = savedRoomSchedule.getScheduleId();
//		int savedRoomId = savedRoom.getRoomId();
//		
//		librarySchedule = null;
//		library = null;
//		roomSchedule = null;
//		room = null;
//		
//		librarySchedule = libraryScheduleRepository.findLibraryScheduleByScheduleId(savedLibraryScheduleId);
//		library = libraryRepository.findLibraryByLibraryId(savedLibraryId);
//		roomSchedule = roomScheduleRepository.findRoomScheduleByScheduleId(savedRoomScheduleId);
//		room = roomRepository.findRoomByRoomId(savedRoomId);
//		
//		assertNotNull(librarySchedule);
//		assertNotNull(library);
//		assertNotNull(roomSchedule);
//		assertNotNull(room);
//		assertEquals(roomSchedule, room.getRoomSchedule());
//		assertEquals(library, room.getLibrary());	
//		
	}
	
	@Test
	public void testPersistAndLoadRoomSchedule() {
		
	}
	
	/*
	 * 
	 * avi
	 * 
	 * 
	 * */
	
	@Test
	public void testPersistAndLoadLibrary() {
		
	}
	
	@Test
	public void testPersistAndLoadLibrarySchedule() {
		
	}
	
	@Test
	public void testPersistAndLoadTimeSlot() {
		
	}
	
	
	
}









