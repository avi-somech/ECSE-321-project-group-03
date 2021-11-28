package ca.mcgill.ecse321.librarymanagement.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarymanagement.dao.ClientRepository;
import ca.mcgill.ecse321.librarymanagement.dao.LibrarianRepository;
import ca.mcgill.ecse321.librarymanagement.dao.LibraryRepository;
import ca.mcgill.ecse321.librarymanagement.dao.RoomRepository;
import ca.mcgill.ecse321.librarymanagement.dao.RoomReservationRepository;
import ca.mcgill.ecse321.librarymanagement.dao.ScheduleRepository;
import ca.mcgill.ecse321.librarymanagement.dao.TimeslotRepository;
import ca.mcgill.ecse321.librarymanagement.dao.TitleRepository;
import ca.mcgill.ecse321.librarymanagement.dao.TitleReservationRepository;
import ca.mcgill.ecse321.librarymanagement.dto.ClientDto;
import ca.mcgill.ecse321.librarymanagement.model.Client;
import ca.mcgill.ecse321.librarymanagement.model.Librarian;
import ca.mcgill.ecse321.librarymanagement.model.Library;
import ca.mcgill.ecse321.librarymanagement.model.Room;
import ca.mcgill.ecse321.librarymanagement.model.Room.RoomType;
import ca.mcgill.ecse321.librarymanagement.model.RoomReservation;
import ca.mcgill.ecse321.librarymanagement.model.Schedule;
import ca.mcgill.ecse321.librarymanagement.model.Timeslot;
import ca.mcgill.ecse321.librarymanagement.model.Title;
import ca.mcgill.ecse321.librarymanagement.model.Title.TitleType;
import ca.mcgill.ecse321.librarymanagement.model.TitleReservation;
import ca.mcgill.ecse321.librarymanagement.model.User;

@Service
public class LibraryManagementService {

	@Autowired
	private TitleRepository titleRepository;

	@Autowired
	private LibraryRepository libraryRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private LibrarianRepository librarianRepository;

	@Autowired
	private TimeslotRepository timeslotRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private RoomReservationRepository roomReservationRepository;

	@Autowired
	private TitleReservationRepository titleReservationRepository;

	@Transactional
	public Title createTitle(String name, String description, String genre, boolean isAvailable, TitleType titleType,
			Library library) {

		if (name == null || name.trim().length() == 0) {
			throw new IllegalArgumentException("Title cannot be empty!");
		}

		if (description == null || description.trim().length() == 0) {
			throw new IllegalArgumentException("Description cannot be empty!");
		}

		if (genre == null || description.trim().length() == 0) {
			throw new IllegalArgumentException("Genre cannot be empty!");
		}

		if (titleType == null) {
			throw new IllegalArgumentException("TitleType cannot be empty!");
		}

		Title title = new Title(name, description, genre, isAvailable, titleType);
		library.addTitle(title);
		titleRepository.save(title);
		libraryRepository.save(library);
		return title;
	}

	@Transactional
	public Title updateTitle(String name, String description, String genre, TitleType titleType, Library library) {

		if (name == null || name.trim().length() == 0) {
			throw new IllegalArgumentException("Title cannot be empty!");
		}

		if (description == null || description.trim().length() == 0) {
			throw new IllegalArgumentException("Title cannot be empty!");
		}

		if (genre == null || description.trim().length() == 0) {
			throw new IllegalArgumentException("Title cannot be empty!");
		}

		if (titleType == null) {
			throw new IllegalArgumentException("Title cannot be empty!");
		}

		Title title = null;

		// update all copies of a title being updated
		for (Title t : library.getTitles()) {
			if (t.getName().equals(name)) {
				t.setDescription(description);
				t.setGenre(genre);
				t.setName(name);
				t.setTitleType(titleType);
				title = t;
				titleRepository.save(t);
			}
		}

		if (title == null) {
			throw new IllegalArgumentException("title does not exist");
		}

		libraryRepository.save(library);
		return title;
	}

	@Transactional
	public Title getTitle(int titleId) {
		Title title = titleRepository.findTitleByTitleId(titleId);
		if (title == null) {
			throw new IllegalArgumentException("Title does not exist!");
		}
		return title;
	}

	@Transactional
	public List<Title> getAllTitles() {
		return toList(titleRepository.findAll());
	}

	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

	@Transactional
	public Library getLibrary() {
		try {
			Library library = toList(libraryRepository.findAll()).get(0);
		}

		catch (Exception e) {
			Library library = new Library();
			libraryRepository.save(library);
			return library;
		}

		return toList(libraryRepository.findAll()).get(0);
	}

	public void removeLibrary(Library library) {
		library.delete();
		libraryRepository.save(library);
	}

	@Transactional
	public Client createClient(String aUsername, String aPassword, String aFullname, String aResidentialAddress,
			String aEmail, boolean aIsResident, boolean aIsOnline, Library library)

	{
		if (aUsername == null || aUsername.trim().length() == 0) {
			throw new IllegalArgumentException("Client information cannot be empty!");
		}

		if (aPassword == null || aPassword.trim().length() == 0) {
			throw new IllegalArgumentException("Client information cannot be empty!");
		}

		if (aFullname == null || aFullname.trim().length() == 0) {
			throw new IllegalArgumentException("Client information cannot be empty!");
		}

		for (User u : library.getUsers()) {
			if (u.getUsername().equals(aUsername)) {
				throw new IllegalArgumentException("username already exists");
			}
		}

		for (User u : library.getUsers()) {
			if (u.getUsername() == aUsername) {
				throw new IllegalArgumentException("username already exists");
			}
		}

		Client client = new Client(aUsername, aPassword, aFullname, aResidentialAddress, aEmail, aIsResident,
				aIsOnline);
		library.addUser(client);
		clientRepository.save(client);
		libraryRepository.save(library);

		return client;
	}

	@Transactional
	public List<Client> getAllClients() {
		return toList(clientRepository.findAll());
	}

	@Transactional
	public Client getClient(int clientId) {
		Client client = clientRepository.findClientByUserId(clientId);

		if (client == null) {
			throw new IllegalArgumentException("Client does not exist!");
		}
		return client;
	}

	@Transactional
	public Librarian createLibrarian(String username, String password, String fullName, boolean isHeadLibrarian,
			Library library) {
		
		if (username == null || username.trim().length() == 0) {
			throw new IllegalArgumentException("Librarian username cannot be empty!");
		}

		if (password == null || password.trim().length() == 0) {
			throw new IllegalArgumentException("Librarian password cannot be empty!");
		}

		if (fullName == null || fullName.trim().length() == 0) {
			throw new IllegalArgumentException("Librarian full name cannot be empty!");
		}

		Librarian headLibrarian = null;

		for (User u : library.getUsers()) {
			if (u.getUsername() == username) {
				throw new IllegalArgumentException("Username already exists");
			}
		}

		for (User u : library.getUsers()) {
			if (u instanceof Librarian) {
				Librarian l = (Librarian) u;
				if (l.getIsHeadLibrarian()) {
					headLibrarian = l;
				}
			}
		}

		if (username == null || username.trim().length() == 0) {
			throw new IllegalArgumentException("Librarian information cannot be empty!");
		}

		if (password == null || password.trim().length() == 0) {
			throw new IllegalArgumentException("Librarian information cannot be empty!");
		}

		if (fullName == null || fullName.trim().length() == 0) {
			throw new IllegalArgumentException("Librarian information cannot be empty!");
		}

		if (isHeadLibrarian && headLibrarian != null) {
			throw new IllegalArgumentException("Head Librarian already exists!");
		}

		Librarian librarian = new Librarian(username, password, fullName, isHeadLibrarian);
		library.addUser(librarian);
		scheduleRepository.save(librarian.getStaffSchedule());
		librarianRepository.save(librarian);
		libraryRepository.save(library);
		return librarian;
	}
	
	@Transactional
	public List<Librarian> getAllLibrarians() {
		return toList(librarianRepository.findAll());
	}

	@Transactional
	public Timeslot createLibraryTimeslot(Time startTime, Time endTime, Date date, Schedule librarySchedule,
			Library library) {
		
		if (startTime == null || endTime == null || date == null || librarySchedule == null) {
			throw new IllegalArgumentException("Library time slot cannot be empty");
		}

		for (Timeslot t : library.getLibrarySchedule().getTimeslots()) {

			if (isOverlapping(t, date, startTime, endTime)) {
				throw new IllegalArgumentException("Library time slot cannot overlap existing time slot");
			}
		}

		if (startTime.after(endTime) || startTime.equals(endTime)) {
			throw new IllegalArgumentException("start time must be before end time");
		}

		Date today = new Date(Calendar.getInstance().getTime().getTime());
		if (today.after(date)) {
			throw new IllegalArgumentException("Library time slot cannot be created in the past");
		}

		Timeslot timeslot = new Timeslot(startTime, endTime, date);
		librarySchedule.addTimeslot(timeslot);
		timeslotRepository.save(timeslot);
		scheduleRepository.save(librarySchedule);
		libraryRepository.save(library);

		return timeslot;
	}

	@Transactional
	public void removeLibraryScheduleTimeslot(int timeslotId) {
		Library library = getLibrary();
		Schedule librarySchedule = library.getLibrarySchedule();
		Timeslot timeslot = null;

		// find time slot
		for (Timeslot t : library.getLibrarySchedule().getTimeslots()) {
			if (t.getTimeSlotId() == timeslotId) {
				timeslot = t;
			}
		}

		if (timeslot == null) {
			throw new IllegalArgumentException("Timeslot does not exist");
		}

		librarySchedule.removeTimeslot(timeslot);
		timeslotRepository.delete(timeslot);
		scheduleRepository.save(librarySchedule);
		libraryRepository.save(library);
	}

	@Transactional
	public List<Timeslot> getAllLibraryTimeslots() {

		Library library = getLibrary();

		return library.getLibrarySchedule().getTimeslots();
	}

	@Transactional
	public List<Timeslot> getAllLibrarianTimeslots(int librarianId) {
		// filter through only the library timeslots
		Librarian librarian = librarianRepository.findLibrarianByUserId(librarianId);

		return librarian.getStaffSchedule().getTimeslots();
	}

	@Transactional
	public List<Room> getAllRooms() {
		return toList(roomRepository.findAll());
	}

	@Transactional
	public Room getRoom(int roomId) {
		Room room = roomRepository.findRoomByRoomId(roomId);
		if (room == null) {
			throw new IllegalArgumentException("Room does not exist!");
		}
		return room;
	}
	
	@Transactional
	public Room updateRoom(int roomId, Boolean isAvailable, Library library) {

		Room room = null;
		
		room = roomRepository.findRoomByRoomId(roomId);
		
		if (room == null) {
			throw new IllegalArgumentException("Room does not exist");
		}
		
		room.setIsAvailable(isAvailable);

		roomRepository.save(room);
		libraryRepository.save(library);
		return room;
	}

	@Transactional
	public List<RoomReservation> getAllRoomReservations(int roomId) {
		List<RoomReservation> roomReservations = toList(roomReservationRepository.findAll());
		ArrayList<RoomReservation> curRoomReservations = new ArrayList<RoomReservation>();
		
		for (RoomReservation rr : roomReservations) {
			if (rr.getRoom().getRoomId() == roomId) {
				curRoomReservations.add(rr);
			}
		}
		
		if (curRoomReservations.size() == 0) {
			throw new IllegalArgumentException("This Room Reservation does not exist");
		}

		return curRoomReservations;
	}

	@Transactional
	public Room createRoom(int capacity, boolean isAvailable, RoomType roomType, Library library) {

		if (capacity <= 0) {
			throw new IllegalArgumentException("capacity must be greater than 0");
		}

		if (roomType == null) {
			throw new IllegalArgumentException("invalid room type");
		}

		Room room = new Room(capacity, isAvailable, roomType);
		library.addRoom(room);
		roomRepository.save(room);
		libraryRepository.save(library);
		return room;
	}

	@Transactional
	public Room updateRoom(int capacity, boolean isAvailable, RoomType roomType, Library library) {

		if (capacity <= 0) {
			throw new IllegalArgumentException("capacity must be greater than 0");
		}

		if (roomType == null) {
			throw new IllegalArgumentException("invalid room type");
		}

		Room room = new Room(capacity, isAvailable, roomType);
		library.addRoom(room);
		roomRepository.save(room);
		libraryRepository.save(library);
		return room;

	}

	@Transactional
	public void removeRoom(int roomId, Library library) {
		if (roomRepository.findRoomByRoomId(roomId) == null) {
			throw new IllegalArgumentException("room does not exist.");
		}
		Room room = roomRepository.findRoomByRoomId(roomId);
		library.removeRoom(room);
		libraryRepository.save(library);
	}

	@Transactional
	public RoomReservation createRoomReservation(Time startTime, Time endTime, Date date, int roomId, int clientId,
			Library library) {

		Room room = roomRepository.findRoomByRoomId(roomId);
		Client client = clientRepository.findClientByUserId(clientId);

		if (room == null && client == null) {
			throw new IllegalArgumentException("room reservations must include a valid client and room");
		}

		if (startTime.after(endTime) || startTime.equals(endTime)) {
			throw new IllegalArgumentException("start time must be before end time");
		}

		Date today = new Date(Calendar.getInstance().getTime().getTime());
		if (today.after(date)) {
			throw new IllegalArgumentException("Library time slot cannot be created in the past");
		}

		List<RoomReservation> roomReservations = toList(roomReservationRepository.findAll());

		for (RoomReservation rr : roomReservations) {
			if (rr.getRoom().equals(room)) {
				if (isOverlapping(rr, date, startTime, endTime)) {
					throw new IllegalArgumentException("room reservation is overlapping with an existing reservation");
				}
			}
		}

		RoomReservation roomReservation = new RoomReservation(startTime, endTime, date, room, client);
		library.addRoomReservation(roomReservation);
		libraryRepository.save(library);
		roomReservationRepository.save(roomReservation);
		return roomReservation;
	}

	@Transactional
	public void removeRoomReservation(int roomId, int userId, Library library) {
		RoomReservation roomReservation = null;
		
		for (RoomReservation roomReservationA : library.getRoomReservations()) {
			if (roomReservationA.getClient().getUserId() == userId && roomReservationA.getRoom().getRoomId() == roomId) {
				roomReservation = roomReservationA;
			}
		}
		
		if (roomReservation == null) {
			throw new IllegalArgumentException("Cannot find Room Reservation");
		}
		
		library.getRoomReservations().remove(roomReservation);
		roomReservationRepository.delete(roomReservation);
		libraryRepository.save(library);
	}

	@Transactional
	public Timeslot createStaffScheduleTimeslot(Time startTime, Time endTime, Date date, Library library,
			int librarianId) {

		Librarian librarian = librarianRepository.findLibrarianByUserId(librarianId);

		if (librarian == null) {
			throw new IllegalArgumentException("librarian does not exist");
		}

		if (startTime.after(endTime) || startTime.equals(endTime)) {
			throw new IllegalArgumentException("start time must be before end time");
		}

		Date today = new Date(Calendar.getInstance().getTime().getTime());
		if (today.after(date)) {
			throw new IllegalArgumentException("Librarian time slot cannot be created in the past");
		}

		for (Timeslot t : librarian.getStaffSchedule().getTimeslots()) {
			if (isOverlapping(t, date, startTime, endTime)) {
				throw new IllegalArgumentException("Librarian time slot cannot overlap existing time slot");
			}

			boolean dayHasHours = false;

			for (Timeslot lt : library.getLibrarySchedule().getTimeslots()) {
				if (lt.getDate().equals(today) || lt.getDate().after(today)) {
					if (lt.getDate().equals(date)
							&& (lt.getStartTime().before(startTime) || lt.getStartTime().equals(startTime))
							&& (lt.getEndTime().after(endTime) || lt.getEndTime().equals(endTime))) {
						throw new IllegalArgumentException(
								"Librarian time slot must be within opening hours of the library");
					}

					else if (lt.getDate().equals(date)) {
						dayHasHours = true;
					}
				}
			}

			if (!dayHasHours) {
				throw new IllegalArgumentException("Librarian time slot must be within opening hours of the library");
			}
		}

		Timeslot timeslot = new Timeslot(startTime, endTime, date);
		librarian.getStaffSchedule().addTimeslot(timeslot);
		timeslotRepository.save(timeslot);
		scheduleRepository.save(librarian.getStaffSchedule());
		libraryRepository.save(library);

		return timeslot;
	}

	@Transactional
	public void removeLibrarian(Library library, int librarianId) {

		Librarian librarian = librarianRepository.findLibrarianByUserId(librarianId);

		if (librarian == null) {
			throw new IllegalArgumentException("librarian does not exist");
		}	else if (librarian.getIsHeadLibrarian()) {
			throw new IllegalArgumentException("cannot fire head librarian");
		}

		librarianRepository.delete(librarian);
		library.removeUser(librarian);
		libraryRepository.save(library);
	}

	@Transactional
	public void removeStaffScheduleTimeslot(int timeslotId, int headLibrarianId, Library library) {
		
		Timeslot timeslot = null;
		
		timeslot = timeslotRepository.findTimeslotByTimeslotId(timeslotId);
		
		Librarian headLibrarian = librarianRepository.findLibrarianByUserId(headLibrarianId);
		
		if (headLibrarian.getIsHeadLibrarian() == false) {
			throw new IllegalArgumentException("Librarian that is not a Head Librarian cannot remove timeslot");
		}

		if (timeslot == null) {
			throw new IllegalArgumentException("timeslot does not exist");
		}


		Schedule staffSchedule = headLibrarian.getStaffSchedule();
		staffSchedule.removeTimeslot(timeslot);
		timeslotRepository.delete(timeslot);
		scheduleRepository.save(staffSchedule);
		librarianRepository.save(headLibrarian);
	}

	@Transactional
	public Librarian getLibrarian(int librarianId) {
		Librarian librarian = librarianRepository.findLibrarianByUserId(librarianId);

		if (librarian == null) {
			throw new IllegalArgumentException("Librarian does not exist!");
		}
		return librarian;
	}
	
	public Librarian updateLibrarian(String aUsername, String aPassword, String aFullname, String aResidentialAddress,
			String aEmail, boolean isResident, boolean isOnline, Library library) {

		Librarian librarian = null;

		if (aUsername == null || aUsername.trim().length() == 0) {
			throw new IllegalArgumentException("Librarian information cannot be empty!");
		}

		if (aPassword == null || aPassword.trim().length() == 0) {
			throw new IllegalArgumentException("Librarian information cannot be empty!");
		}

		if (aFullname == null || aFullname.trim().length() == 0) {
			throw new IllegalArgumentException("Librarian information cannot be empty!");
		}
		
		for (User u : library.getUsers()) {
			if (u.getUsername().equals(aUsername)) {
				librarian = (Librarian) u;
			}
		}

		librarian.setFullname(aFullname);
		librarian.setPassword(aPassword);
		
		librarianRepository.save(librarian);
		libraryRepository.save(library);

		return librarian;
	}

	@Transactional
	public TitleReservation createTitleReservation(Date returnDate, boolean isCheckedOut, String titleName,
			String username, Library library) {

		Client client = null;
		Title title = null;

		for (User u : library.getUsers()) {
			if (u instanceof Client && u.getUsername().equals(username)) {
				client = (Client) u;
			}
		}

		for (Title t : library.getTitles()) {
			if (t.getIsAvailable() && t.getName().equals(titleName)) {
				title = t;
			}
		}

		if (client == null) {
			throw new IllegalArgumentException("Client and Title must exist!");
		}

		if (title == null) {
			throw new IllegalArgumentException("Client and Title must exist!");
		}

		TitleReservation titleReservation = new TitleReservation(returnDate, isCheckedOut, title, client);
		library.addTitleReservation(titleReservation);
		title.setIsAvailable(false);

		titleRepository.save(title);
		titleReservationRepository.save(titleReservation);
		libraryRepository.save(library);

		return titleReservation;
	}

	// sets titleReservation IsCheckedOut=true
	@Transactional
	public TitleReservation updateTitleReservation(TitleReservation titleReservation, Library library) {
		titleReservation.setIsCheckedOut(true);

		titleReservationRepository.save(titleReservation);
		libraryRepository.save(library);

		return titleReservation;
	}

	@Transactional
	public TitleReservation updateTitleReservationParameters(Date returnDate, boolean isCheckedOut, String titleName,
			String username, Library library) {
		Client client = null;
		Title title = null;

		for (User u : library.getUsers()) {
			if (u instanceof Client && u.getUsername().equals(username)) {
				client = (Client) u;
			}
		}

		for (Title t : library.getTitles()) {
			if (t.getIsAvailable() && t.getName().equals(titleName)) {
				title = t;
			}
		}

		if (client == null) {
			throw new IllegalArgumentException("Client does not exist!");
		}

		if (title == null) {
			throw new IllegalArgumentException("Title is not available!");
		}

		TitleReservation titleReservation = new TitleReservation(returnDate, isCheckedOut, title, client);
		library.addTitleReservation(titleReservation);
		title.setIsAvailable(false);

		titleRepository.save(title);
		titleReservationRepository.save(titleReservation);
		libraryRepository.save(library);

		return titleReservation;

	}

	@Transactional
	public TitleReservation getTitleReservationByTitleId(int titleId) {
		List<TitleReservation> titleReservations = toList(titleReservationRepository.findAll());
		TitleReservation thisTitleReservation = null;
		for (TitleReservation tr : titleReservations) {
			if (tr.getTitle().getTitleId() == titleId) {
				thisTitleReservation = tr;
			}
		}

		return thisTitleReservation;
	}

	@Transactional
	public List<TitleReservation> getAllTitleReservations() {
		List<TitleReservation> titleReservations = toList(titleReservationRepository.findAll());
		return titleReservations;
	}

	@Transactional
	public void removeTitleReservation(int titleReservationId, Library library) {

		List<TitleReservation> titleReservations = toList(titleReservationRepository.findAll());
		TitleReservation thisTitleReservation = null;
		for (TitleReservation tr : titleReservations) {
			if (tr.getTitle().getTitleId() == titleReservationId) {
				thisTitleReservation = tr;
			}
		}
		library.removeTitleReservation(thisTitleReservation);

	}

	@Transactional
	public void removeTitle(Library library, int titleId) {
		Title title = null;
		
		for (Title titleA : library.getTitles()) {
			if (titleA.getTitleId() == titleId) {
				title = titleA;
			}
		}
		
		if (title == null) {
			throw new IllegalArgumentException("This title does not exist");
		}
		
		titleRepository.delete(title);
		library.removeTitle(title);
		libraryRepository.save(library);
	}

	@Transactional
	public boolean isOverlapping(Timeslot existingTimeslot, Date newDate, Time newStart, Time newEnd) {
		Date date = existingTimeslot.getDate();

		// is it on the same day?
		if (date.getYear() == newDate.getYear() && date.getMonth() == newDate.getMonth()
				&& date.getDay() == newDate.getDay()) {
			Time startTime = existingTimeslot.getStartTime();
			Time endTime = existingTimeslot.getEndTime();

			if (newStart.before(startTime) && newEnd.after(startTime)) {
				return true;
			}

			if (newStart.after(startTime) && newEnd.after(endTime)) {
				return true;
			}

			if (newStart.before(startTime) && newEnd.after(endTime)) {
				return true;
			}

			if (newStart.after(startTime) && newEnd.before(endTime)) {
				return true;
			}

			if (newStart.equals(startTime) && newEnd.equals(endTime)) {
				return true;
			}

			if (newStart.equals(startTime) && newEnd.after(endTime)) {
				return true;
			}

			if (newStart.equals(startTime) && newEnd.before(endTime)) {
				return true;
			}

			if (newStart.after(startTime) && newEnd.equals(endTime)) {
				return true;
			}

			if (newStart.before(startTime) && newEnd.equals(endTime)) {
				return true;
			}
		}

		return false;
	}

	@Transactional
	public Client loginClient(String username, String password) {

		Library library = getLibrary();
		Client client = null;

		for (User u : library.getUsers()) {
			if (u instanceof Client && u.getUsername().equals(username) && u.getPassword().equals(password)) {
				client = (Client) u;
			}
		}

		if (client == null) {
			throw new IllegalArgumentException("username and password incorrect");
		}

		return client;
	}

	@Transactional
	public Librarian loginLibrarian(String username, String password) {

		Library library = getLibrary();
		Librarian librarian = null;

		for (User u : library.getUsers()) {
			if (u instanceof Librarian && u.getUsername().equals(username) && u.getPassword().equals(password)) {
				librarian = (Librarian) u;
			}
		}

		if (librarian == null) {
			throw new IllegalArgumentException("username and password incorrect");
		}

		return librarian;
	}

	@Transactional
	public Timeslot getTimeslot(int timeslotId) {
		Timeslot timeSlot = timeslotRepository.findTimeslotByTimeslotId(timeslotId);

		if (timeSlot == null) {
			throw new IllegalArgumentException("Timeslot does not exist!");
		}
		return timeSlot;
	}

	@Transactional
	public Client updateClient(String aUsername, String aPassword, String aFullname, String aResidentialAddress,
			String aEmail, boolean isResident, boolean isOnline, Library library) {

		Client client = null;

		if (aUsername == null || aUsername.trim().length() == 0) {
			throw new IllegalArgumentException("Client information cannot be empty!");
		}

		if (aPassword == null || aPassword.trim().length() == 0) {
			throw new IllegalArgumentException("Client information cannot be empty!");
		}

		if (aFullname == null || aFullname.trim().length() == 0) {
			throw new IllegalArgumentException("Client information cannot be empty!");
		}

		for (User u : library.getUsers()) {
			if (u.getUsername().equals(aUsername)) {
				client = (Client) u;
			}
		}

		client.setEmail(aEmail);
		client.setFullname(aFullname);
		client.setPassword(aPassword);
		client.setResidentialAddress(aResidentialAddress);

		clientRepository.save(client);
		libraryRepository.save(library);

		return client;
	}
	
	@Transactional
	public Client updateClientPassword(String aUsername, String aPassword, Library library) {

		Client client = null;

		if (aPassword == null || aPassword.trim().length() == 0) {
			throw new IllegalArgumentException("Client information cannot be empty!");
		}

		for (User u : library.getUsers()) {
			if (u.getUsername().equals(aUsername)) {
				client = (Client) u;
			}
		}

		client.setPassword(aPassword);

		clientRepository.save(client);
		libraryRepository.save(library);

		return client;
	}
	
	@Transactional
	public Client updateClientEmail(String aUsername, String aEmail, Library library) {

		Client client = null;

		if (aEmail == null || aEmail.trim().length() == 0) {
			throw new IllegalArgumentException("Client information cannot be empty!");
		}

		for (User u : library.getUsers()) {
			if (u.getUsername().equals(aUsername)) {
				client = (Client) u;
			}
		}

		client.setEmail(aEmail);

		clientRepository.save(client);
		libraryRepository.save(library);

		return client;
	}
	
	@Transactional
	public Client updateClientResAddress(String aUsername, String aResidentialAddress, Library library) {

		Client client = null;

		if (aResidentialAddress == null || aResidentialAddress.trim().length() == 0) {
			throw new IllegalArgumentException("Client information cannot be empty!");
		}

		for (User u : library.getUsers()) {
			if (u.getUsername().equals(aUsername)) {
				client = (Client) u;
			}
		}

		client.setResidentialAddress(aResidentialAddress);

		clientRepository.save(client);
		libraryRepository.save(library);

		return client;
	}

	@Transactional
	public void removeClient(String username, Library library) {

		Client client = null;

		for (User u : library.getUsers()) {
			if (u.getUsername().equals(username)) {
				client = (Client) u;
			}
		}

		if (client == null) {
			throw new IllegalArgumentException("Client does not exist!");
		}

		library.removeUser(client);
		clientRepository.delete(client);
		libraryRepository.save(library);

	}

	@Transactional
	public RoomReservation getRoomReservation(int id) {
		RoomReservation rr = roomReservationRepository.findRoomReservationByTimeslotId(id);
		return rr;
	}

}