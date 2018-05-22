package com.acegear.horizon.config.http;

import com.acegear.horizon.domain.models.jpa.UserToken;
import com.acegear.horizon.domain.repository.jpa.UserTokenRepository;

import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class UserRequestWrapper extends HttpServletRequestWrapper {
    private UserTokenRepository userTokenRepository;

    public UserRequestWrapper(HttpServletRequest request, UserTokenRepository userTokenRepository) {
        super(request);
        this.userTokenRepository = userTokenRepository;
    }

    @Override
    public String getHeader(String name) {
        return super.getHeader(name);
    }

    @Override
    public long getDateHeader(String name) {

        return super.getDateHeader(name);
    }

    @Override
    public int getIntHeader(String name) {

        return super.getIntHeader(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        Vector<String> vector = new Vector<>();
        if (name.equals("X-Consumer-Username")) {
            String userTokenId = super.getHeader(name);
            if (userTokenId != null){
                UserToken userToken = userTokenRepository.findOne(userTokenId);
                if (userToken != null) {
                    vector.add(userToken.getUserId().toString());
                } else {
                    vector.add("0");
                }
            } else {
                vector.add("0");
            }
            return vector.elements();
        }
        return super.getHeaders(name);
    }
}
