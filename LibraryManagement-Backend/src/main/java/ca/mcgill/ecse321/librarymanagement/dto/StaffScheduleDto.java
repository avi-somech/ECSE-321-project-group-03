package ca.mcgill.ecse321.librarymanagement.dto;

import java.util.List;

import ca.mcgill.ecse321.librarymanagement.model.Librarian;
import ca.mcgill.ecse321.librarymanagement.model.TimeSlot;

public class StaffScheduleDto {
	private List<Librarian> librarians;
	private int scheduleId;
	private List<TimeSlot> timeSlots;
	
	public StaffScheduleDto() {
		
	}
	
	public int getScheduleId() {
		return this.scheduleId;
	}
	
	public List<TimeSlot> getTimeSlots() {
		return this.timeSlots;
	}
	
	public List<Librarian> getLibrarians() {
		return this.librarians;
	}
	
	
}
