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
		return new ExcelHandler().addUser(user);
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
