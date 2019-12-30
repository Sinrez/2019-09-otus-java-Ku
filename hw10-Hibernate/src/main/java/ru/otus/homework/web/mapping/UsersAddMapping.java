package ru.otus.homework.web.mapping;

import ru.otus.homework.model.AddressDataSet;
import ru.otus.homework.model.PhoneDataSet;
import ru.otus.homework.model.User;
import ru.otus.homework.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class UsersAddMapping extends HttpServlet {
    private final String URL_STRING;
    private UserService userService;

    public UsersAddMapping(UserService userService, String address, String port) {
        this.userService = userService;
        URL_STRING = "http://" + address + ":" + port + "/";
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;Windows-1251");
        userService.create(
                createUserFromRequest(request)
        );
        response.setStatus(HttpServletResponse.SC_FOUND);
        response.setHeader("Location", URL_STRING);
        response.sendRedirect(URL_STRING);
    }

    private User createUserFromRequest(HttpServletRequest request) {
        return new User(
                request.getParameter("name"),
                Integer.parseInt(request.getParameter("age")),
                new AddressDataSet(
                        request.getParameter("street")
                ),
                Collections.singleton(
                        new PhoneDataSet(
                                request.getParameter("number")
                        )
                )
        );
    }
}
