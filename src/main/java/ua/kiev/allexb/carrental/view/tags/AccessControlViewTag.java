package ua.kiev.allexb.carrental.view.tags;

import ua.kiev.allexb.carrental.model.Administrator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author allexb
 * @version 1.0 16.09.2016
 */
public class AccessControlViewTag extends TagSupport {
    private Administrator admin;

    public AccessControlViewTag() {
    }

    public Administrator getAdmin() {
        return admin;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

    @Override
    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }

    public boolean isAccept() {
        return admin != null && admin.getId() != 0;
    }
}
