package ru.otus.homework.web.mapping;

import freemarker.template.TemplateException;
import ru.otus.homework.service.TemplateService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CreateUserMenu extends HttpServlet {
    private TemplateService templateService;
    private String address;
    private String port;

    public CreateUserMenu(String templatesPath, String address, String port) throws IOException {
        this.templateService = new TemplateService(templatesPath);
        this.address = address;
        this.port = port;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> root = new HashMap<>();
        root.put("address", address);
        root.put("port", port);
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter printWriter = response.getWriter();
        try {
            templateService.process("CreateUser.ftl", root, response.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        printWriter.flush();

    }
}
