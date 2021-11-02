package store.miscellaneous;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HelperClass {
	private static String[] validMailServersPostfix = {"com", "org", "edu", "il"};
	private static final int pwd_max_len = 5;
	
	public static boolean checkEmail(String email) {
		var splitted = email.split("@");
		if(splitted.length != 2)
			return false;
		
		//email 'lvalue' must be consist of 2 letters at least 
		if(splitted[0].length() < 2)
			return false;
		
		if(!Character.isAlphabetic(splitted[0].charAt(0)))
			return false;
		
		//check postfix
		var postfix = splitted[1].split("\\.");
		List<String> postfixList = Arrays.asList(validMailServersPostfix);
		if(!postfixList.contains(postfix[postfix.length-1]))
			return false;
		
		return true;
	}
	
	public static boolean checkName(Map<String, String> name) {
		if(name == null)
			return false;
		if(name.size() != 2)
			return false;
		
		var firstName = name.get("first");
		var lastName = name.get("last");
		if(firstName == null || lastName == null)
			return false;
		if(!Character.isAlphabetic(firstName.charAt(0)) || !Character.isAlphabetic(lastName.charAt(0)))
			return false;
		
		return true;
	}
	
	public static boolean checkPassword(String pwd) {
		if(pwd.length() < pwd_max_len)
			return false;
		
		
		var digits = pwd.chars()		//get an IntStream with all the chars codes
				.mapToObj(c -> (char)c)	//map each to a char
				.filter(c -> Character.isDigit(c))	//filter the chars who're digits
				.collect(Collectors.toList());
		
		if(digits.size() < 1)
			return false;
		if(digits.size() == pwd.length())
			return false;
		
		return true;
	}
	
	public static boolean checkDate(String date) {
		LocalDate now = LocalDate.now();
		
		LocalDate recieved;
		try {
			recieved = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		} catch (DateTimeParseException e) {
			return false;
		}
		
		if(recieved.getYear() > now.getYear())
			return false;
		else if(recieved.getYear() == now.getYear() && recieved.getDayOfYear() > now.getDayOfYear())
			return false;
		else if(recieved.getYear() <= now.getYear() - 100)
			return false;
		
		return true;
	}
	
	public static boolean checkRoles(String[] roles) {
		var emptyRoles = Arrays.asList(roles)
				.stream()
				.filter(s -> s.isBlank())
				.collect(Collectors.toList());
		
		if(emptyRoles.size() == 0)
			return false;
		return true;
	}
}
