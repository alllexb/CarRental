package ua.kiev.allexb.carrental.controller.handler;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.utils.ApplicationLogger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author allexb
 * @version 1.0 18.08.2016
 */
@WebServlet(urlPatterns = {"/errorHandler"})
public class ErrorHandler extends HttpServlet {
    private static final long serialVersionUID = 4004338792160393545L;

    static final Logger logger = ApplicationLogger.getLogger(ErrorHandler.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Request to page: " + request.getServletPath());
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        if (statusCode != null) {
            request.setAttribute("error_code", String.valueOf(statusCode));
            switch (statusCode) {
                case 404:
                    request.setAttribute("error_message", "Sorry, the page not found");
                    request.setAttribute("error_comment", "The link you followed probably broken, or the page has been removed.");
                    break;
                case 403:
                    request.setAttribute("error_message", "Forbidden");
                    request.setAttribute("error_comment", "Access to this resource on server is denied!");
                    break;
                default:
                    if (throwable != null && (throwable instanceof SQLException)) {
                        request.setAttribute("error_message", "Sorry... Data base does not respond.");
                    } else {
                        request.setAttribute("error_code", "Undetermined");
                        request.setAttribute("error_message", "Server Error. Information about exception was missed.");
                    }
                    request.setAttribute("error_comment", "Try that again later, and if it still doesn't work, let us know. <br/>" +
                            "Support contact information in the bottom of the page.");
            }
        }
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/errorView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
