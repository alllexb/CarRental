package ua.kiev.allexb.carrental.view.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * @author allexb
 * @version 1.0 16.09.2016
 */
public class AccessControlViewDenyTag extends BodyTagSupport {
    @Override
    public int doStartTag() throws JspException {
        AccessControlViewTag parent = (AccessControlViewTag) findAncestorWithClass(this, AccessControlViewTag.class);
        if (parent == null) {
            throw new JspTagException("deny tag not inside acv tag");
        }
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doAfterBody() throws JspException {
        AccessControlViewTag parent = (AccessControlViewTag) findAncestorWithClass(this, AccessControlViewTag.class);
        if (!parent.isAccept()) {
            try {
                BodyContent body = getBodyContent();
                JspWriter out = body.getEnclosingWriter();
                out.print(body.getString());
            } catch (IOException ioe) {
                System.out.println("Error in \"acv\" -> \"deny\" Tag: " + ioe);
            }
        }
        return SKIP_BODY;
    }
}
