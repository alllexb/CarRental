package ua.kiev.allexb.carrental.controller.validator;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.model.Administrator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author allexb
 * @version 1.0 20.09.2016
 */
public class AdministratorValidator extends Validator<Administrator> {

    static final Logger logger = Logger.getLogger(AdministratorValidator.class);

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public AdministratorValidator(Administrator administrator) {
        super(administrator);
    }

    @Override
    public boolean isValid() {
        boolean error = false;
        errorMessage = new StringBuffer();
        logger.info("Administrator with ID:" + model.getId() + " prepared to validation.");
        String firstName = model.getFirstName();
        String lastName = model.getLastName();
        if (firstName.isEmpty() && lastName.isEmpty()) {
            errorMessage.append("Administrator always must have first or last name. Enter at least one of them, please.\n");
            error = true;
        }
        String email = model.getEmail();
        if(email.isEmpty()) {
            errorMessage.append("Administrator always must have e-mail. \n");
            error = true;
        } else {
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
            if (!matcher.find()) {
                errorMessage.append("Administrator e-mail has wrong format. \n");
                error = true;
            }
        }
        String login = model.getLogin();
        if(login.isEmpty()) {
            errorMessage.append("Administrator always must have login. \n");
            error = true;
        }
        String password = model.getPassword();
        if(password.isEmpty()) {
            errorMessage.append("Administrator always must have password. \n");
            error = true;
        }
        logger.info("Data validation completed.");
        return !error;
    }
}
