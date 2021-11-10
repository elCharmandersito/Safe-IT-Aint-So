package cl.ubb.testing.safeit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cl.ubb.testing.safeit.models.EmailBody;
import cl.ubb.testing.safeit.models.Usuario;
import cl.ubb.testing.safeit.services.EmailService;

@RestController
public class EmailController {

	@Autowired
	private EmailService emailService;
	
	
	@PostMapping(value = "/send/email", produces ="application/json")
	public ResponseEntity<EmailBody> sendEmail(@RequestBody EmailBody emailbody) {
		try {
			if (emailService.sendEmail(emailbody)) {
				return new ResponseEntity(emailbody, HttpStatus.CREATED);
			}
			return new ResponseEntity(HttpStatus.BAD_GATEWAY);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
	
}
