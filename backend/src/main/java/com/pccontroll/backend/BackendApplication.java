package com.pccontroll.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@CrossOrigin
@SpringBootApplication
@RestController
public class BackendApplication {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/hello")
	public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
		User myUser1 = new User("Tom", "tom@gmail.com", "07555374636", "384367467583", "1998-02-24", "24", "nwl", Gender.Male, 34.4);
		String potato = new ExcelHandler().addUser(myUser1);
		return String.format("Hello %s!", potato);
	}

	@PostMapping("/contact-form")
	public String contactForm(@RequestBody User user) {

		if(user.getContactEmail().equals("potato@gmail.com"))  {
			return "admindash";
		}
		SendMail mailSender = new SendMail();
		String userMessage = "Dear " + user.getContactName() + ",\nThank you for registering for the annual LKCYL camp. Your details will be saved under the Data Protection Act.\n\nThe camp will begin on Friday 30th September and ending on Sunday 2nd October, taking place in Moat Mount Outdoors Centre, Barnet Way, London NW7 5AL.\n\n To manage the cost of accommodation and food for 3 days we will be charging £55 per person, with a deposit amount of £25. We currently have limited spaces and will be accepting people on a first come first service basis, your registration process will only be confirmed once you have transferred the deposit amount to the LKCA bank account (details below), so please transfer £25 as soon as possible before spaces run out.\n When transferring the deposit please use the reference 'camp' followed by your name. eg 'camp Kevin'. \n\n\nLondon Knanaya Catholics Association\nSort code: 40 39 04\nAcc no: 52001586\nReference: Camp (your name)\n\n\nPlease also follow us on our Instagram page (london_kcyl) if you haven't already, as we will be sending out some information related to the camp on there nearer to the time. Looking forward to hearing some suggestions and if you have any queries kindly email us on kcyl.london@gmail.com\n \nKind Regards,\nLKCYL team";
		List<User> userList = new ArrayList<>();
		userList.add(user);
		//send to the person
		String senttoperson = mailSender.sendMail(userList, false, userMessage);


		userRepository.saveAll(userList);


		String adminMessage = "Yo guys,\nyou will keep getting this email every time someone registers but we are doing this for logging pourposes so that no data gets lost\nIt might be a good idea to move all these to a separate folder so that we don't get spammed\n\nthe person registered for the camp is: " + user.toString() + "\n\n\n also check out the attachment in this email";
		List<User> adminUsers = new ArrayList<>();
		String[] adminEmails = {"kcyl.london@gmail.com", "albertsabu@gmail.com", "christiejohnsjibi1@gmail.com"};
		for (String adminEmail : adminEmails) {
			User admin = new User(adminEmail);
			adminUsers.add(admin);
		}

		String sendtoAdmins = mailSender.sendMail(adminUsers, false, adminMessage);

		String successMessage = "Sent message successfully....";
		if(sendtoAdmins.equals(successMessage) && senttoperson.equals(successMessage)) {
			return "Thank you For registering for the camp, please check your email for information regarding your deposit, to confirm your registration";
		}

		return "email failure";

	}

	@GetMapping("/get-all")
	public List<List<String>> getall() {
		List<User> users = userRepository.findAll();
		List<List<String>> toret = new ArrayList<>();
		users.forEach(user -> {
			List<String> userString = new ArrayList<>();
			userString.add(user.getContactName());
			userString.add(user.getGender().name());
			userString.add(String.valueOf(user.getAmountPaid()));
			userString.add(user.getAge());
			userString.add(user.getUnit());
			userString.add(user.getContactEmail());
			userString.add(user.getContactPhone());
			userString.add(user.getParentPhone());
			toret.add(userString);
		});

		return toret;
	}



	private static String potato() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("bash", "-c", "ls /Users/abrahamjoys/");

		try {

			Process process = processBuilder.start();

			StringBuilder output = new StringBuilder();

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				System.out.println("Success!");
				System.out.println(output);
				return output.toString();
			} else {
				//abnormal...
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
