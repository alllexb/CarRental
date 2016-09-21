package ua.kiev.allexb.carrental.controller.validator;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.data.dao.util.DateUtil;
import ua.kiev.allexb.carrental.model.Client;
import ua.kiev.allexb.carrental.utils.ApplicationLogger;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author allexb
 * @version 1.0 17.09.2016
 */
public class ClientValidator extends Validator<Client> {
    static final Logger logger = ApplicationLogger.getLogger(ClientValidator.class);

    public ClientValidator(Client client) {
        super(client);
    }

    @Override
    public boolean isValid() {
        boolean error = false;
        errorMessage = new StringBuffer();
        logger.info("Client with ID:" + model.getId() + " prepared to validation.");
        String firstName = model.getFirstName();
        String lastName = model.getLastName();
        if (firstName.isEmpty() && lastName.isEmpty()) {
            errorMessage.append("Client always must have first or last name. Enter at least one of them, please.\n");
            error = true;
        }
        Date birthday = model.getBirthday();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1920, Calendar.JANUARY, 1);
        Date minDate = calendar.getTime();
        if (birthday != null && (birthday.getTime() < minDate.getTime() || birthday.getTime() > (new Date()).getTime())) {
            errorMessage.append("Clients birthday has to be in the range from ");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            errorMessage.append(formatter.format(minDate));
            errorMessage.append(" to ");
            errorMessage.append(formatter.format(new Date()));
            errorMessage.append(". Please, enter it correctly.\n");
            error = true;
        }
        int dLNumber = model.getdLNumber();
        if (dLNumber <= 0) {
            errorMessage.append("Client always must have driver's license number. Enter it, please.\n");
            error = true;
        }
        int lengthOfDrivingExperience = model.getLengthOfDrivingExperience();
        if (lengthOfDrivingExperience < 0) {
            errorMessage.append("Length of driving experience has to be positive value. Please enter it correctly.\n");
            error = true;
        }
        logger.info("Data validation completed.");
        return !error;
    }

}