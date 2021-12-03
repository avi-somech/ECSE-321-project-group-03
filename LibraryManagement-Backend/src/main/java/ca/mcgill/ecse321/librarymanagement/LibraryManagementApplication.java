package ca.mcgill.ecse321.librarymanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class LibraryManagementApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApplication.class, args);
		
	}

	// greeting
	@RequestMapping("/")
	public String greeting(){		
		return "Hello world this is a test!";
	}

}
