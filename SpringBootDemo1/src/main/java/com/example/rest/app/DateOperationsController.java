package com.example.rest.app;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DateOperationsController {

	// Get Request - Path Param Example
	@GetMapping("/greetingswithtime/{name}")
	public String getGreetingsWithTime(@PathVariable String name) {
		String greetings = "Hello " + name + " !!! Saying hello at : " + LocalDateTime.now();
		return greetings;
	}

	// Example get request to find the number of days between 2 dates
	@GetMapping("/daysbetween")
	public ResponseEntity<String> daysbetween(@RequestParam("date1") String date1,
			@RequestParam("date2") String date2) {

		LocalDate dateLocal1 = null;
		ResponseEntity<String> resEntity;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

		try {
			dateLocal1 = LocalDate.parse(date1, formatter);
		} catch (Exception e) {
			resEntity = new ResponseEntity<String>("Incorrect Date1 field " + date1, HttpStatus.BAD_REQUEST);
			return resEntity;
		}
		LocalDate dateLocal2 = null;
		try {
			dateLocal2 = LocalDate.parse(date2, formatter);
		} catch (Exception e) {
			resEntity = new ResponseEntity<String>("Incorrect Date2 field " + date2, HttpStatus.BAD_REQUEST);
			return resEntity;
		}

		Period period = Period.between(dateLocal1, dateLocal2);
		if (dateLocal1.isAfter(dateLocal2)) {
			period = Period.between(dateLocal2, dateLocal1);
		}

		String response = String.format("No. of days in between %s and %s is %s years, %s months and %s days", date1,
				date2, period.getYears(), period.getMonths(), period.getDays());
		resEntity = new ResponseEntity<String>(response, HttpStatus.OK);

		return resEntity;
	}

	//Get request to get a person's age in days
	@GetMapping("/ageindays")
	public ResponseEntity<String> getAgeInDays(@RequestParam(value = "bday", defaultValue = "null") String bday) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

		if ("null".equalsIgnoreCase(bday)) {
			return new ResponseEntity<String>("Missing input parameter - bday ", HttpStatus.BAD_REQUEST);
		}
		LocalDate bdayLocal = null;
		try {
			bdayLocal = LocalDate.parse(bday, formatter);
		} catch (Exception e) {
			return new ResponseEntity<String>("Incorrect date format: " + bday, HttpStatus.BAD_REQUEST);
		}

		if (bdayLocal.isAfter(LocalDate.now())) {
			return new ResponseEntity<String>(
					String.format("Incorrect entry, birthday - %s cannot be after today.", bday),
					HttpStatus.BAD_REQUEST);
		}

		long noOfDays = ChronoUnit.DAYS.between(bdayLocal, LocalDate.now());

		return new ResponseEntity<String>(String.format("You are %s days old", noOfDays), HttpStatus.OK);

	}

}
