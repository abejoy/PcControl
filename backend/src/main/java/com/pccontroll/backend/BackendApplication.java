package com.pccontroll.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@CrossOrigin
@SpringBootApplication
@RestController
public class BackendApplication {

	@GetMapping("/hello")
	public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
		User myUser1 = new User("Tom", "tom@gmail.com", "07555374636", "384367467583", "1998-02-24", "24", "nwl");
		String potato = new ExcelHandler().addUser(myUser1);
		return String.format("Hello %s!", potato);
	}

	@PostMapping("/contact-form")
	public String contactForm(@RequestBody User user) {
		String fileSaveMessage =  new ExcelHandler().addUser(user);
		if (!fileSaveMessage.equals("Your message was sent, thank you!")) {
			return fileSaveMessage;
		}
		SendMail mailSender = new SendMail();

		String userMessage = "Dear " + user.getContactName() + ",\nthankyou for registering for the LKCYL camp, you will need to now give us money fam £25 otherwise you cant come \nKind Regards,\nLKCYL team";
		String adminMessage = "Yo guys,\nyou will keep getting this email every time someone registers but we are doing this for logging pourposes so that no data gets lost\nIt might be a good idea to move all these to a seprate folder so that we dont get spammed\n\nthe person registered for the camp is" + user.toString() + "\n\n\n also check out the attachment in this email";

		//send to the person
		String senttoperson = mailSender.sendMail(user, false, userMessage);

		//send to me
		User me = new User("jesvinjoril98@yahoo.co.in");
		String senttome = mailSender.sendMail(me, true, adminMessage);

		String successMessage = "Sent message successfully....";
		if(senttome.equals(successMessage) && senttoperson.equals(successMessage)) {
			return fileSaveMessage;
		}

		return "email failure";

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
