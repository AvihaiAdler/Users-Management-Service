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
	
	/*
	 * checks valid email
	 * checks email has only one @ separator
	 * checks email left side of the separator contains at least 2 chars
	 * checks email starts with a letter
	 * check email postfix to match the postfix list above
	 */
	public static boolean checkEmail(String email) {
		if(email == null)
			return false;
		
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
	
	/*
	 * checks valid name
	 * checks name consist of only 2 strings
	 * check name consists of first and last name
	 * checks first letter of both first & last name to be a letter
	 */
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
	
	/*
	 * checks valid password
	 * checks password at least 5 chars long
	 * checks password contains at least 1 digit
	 * checks password not consist of digits entirely
	 */
	public static boolean checkPassword(String pwd) {
		if(pwd == null)
			return false;
		if(pwd.isBlank())
			return false;
		
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
	
	/*
	 * checks if date is a valid date:
	 * checks the date format
	 * if the date received is futuristic - returns false
	 * if the date received is more than 100 years in the past - returns false 
	 */
	public static boolean checkDate(String date) {
		if(date == null)
			return false;
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
	
	/*
	 * check is any role is an empty string
	 */
	public static boolean checkRoles(String[] roles) {
		var emptyRoles = Arrays.asList(roles)
				.stream()
				.filter(s -> s.isEmpty())
				.collect(Collectors.toList());
		
		if(emptyRoles.size() == 0)
			return true;
		return false;
	}
}
