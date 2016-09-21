package ua.kiev.allexb.carrental.view.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author allexb
 * @version 1.0 16.09.2016
 */
public class EnumListVewTag extends SimpleTagSupport {

    private Class<? extends Enum> item;
    private Object current;
    private String name;
    private String id;
    private int size;
    private boolean required;
    private boolean disabled;

    public EnumListVewTag() {
    }

    public Class<? extends Enum> getItem() {
        return item;
    }

    public void setItem(Class<? extends Enum> item) {
        this.item = item;
    }

    public Object getCurrent() {
        return current;
    }

    public void setCurrent(Object current) {
        this.current = current;
    }

    public void setCurrent(Enum current) {
        this.current = current;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public void doTag() throws JspException, IOException {
        if (item == null || item.getEnumConstants().length <= 0) {
            throw new JspTagException("Null or empty Enum");
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("<select ");
        if (required) buffer.append("required ");
        if (size >= 1) buffer.append("size=\"" + size + "\" ");
        else {
            throw new JspTagException("Invalid \"enum\" tag attribute value: size=" + size);
        }
        buffer.append("name=\"" + name + "\" ");
        if (id != null && !id.isEmpty()) buffer.append("id=\"" + id + "\" ");
        if (disabled) buffer.append("disabled ");
        buffer.append(">\n");
        Object[] items = item.getEnumConstants();
        boolean selected = (current != null) && current.getClass().equals(item.getEnumConstants()[0].getClass()) && Arrays.asList(items).contains(current);
        for (int i = 0; i < items.length; i++) {
            if (!selected && i == 0) {
                buffer.append("<option selected = \"selected\">" + items[i] + "</option>");
            } else {
                if (items[i].equals(current)) {
                    buffer.append("<option selected = \"selected\">" + items[i] + "</option>");
                } else {
                    buffer.append("<option>" + items[i] + "</option>");
                }
            }
            buffer.append("\n");
        }
        buffer.append("</select>");
        getJspContext().getOut().write(buffer.toString());
    }
}
