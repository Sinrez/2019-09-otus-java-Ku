package ru.otus.homework.web.mapping;

import com.google.gson.Gson;
import ru.otus.homework.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UsersGetAllServlet extends HttpServlet {
    private final UserService userService;
    private final Gson gson;

    public UsersGetAllServlet(UserService userService) {
        this.userService = userService;
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(gson.toJson(userService.getAll()));
        printWriter.flush();
    }
}
