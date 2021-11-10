package cl.ubb.testing.safeit.repositories;

import cl.ubb.testing.safeit.models.EmailBody;

public interface EmailPort {

	boolean sendEmail(EmailBody emailBody);

}
