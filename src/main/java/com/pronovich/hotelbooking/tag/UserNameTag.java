package com.pronovich.hotelbooking.tag;

import com.pronovich.hotelbooking.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

@SuppressWarnings("serial")
public class UserNameTag extends TagSupport {

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            String out;
            if (user.getRole() != null) {
                if ("admin".equalsIgnoreCase(user.getRole().toString())) {
                    out = user.toString() + " <label class=\"red\"> admin </label>";
                } else {
                    out = user.toString();
                }
                pageContext.getOut().write(out);
            }
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}

