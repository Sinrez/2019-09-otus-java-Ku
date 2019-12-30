package ru.otus.homework.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class TemplateService {
    private Configuration cfg;

    public TemplateService(String templatesPath) throws IOException {
        cfg = new Configuration(Configuration.VERSION_2_3_27);
        cfg.setDirectoryForTemplateLoading(new File(templatesPath));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
    }

    public void process(String ftlName, Map<String, Object> root, PrintWriter writer) throws IOException, TemplateException {
        Template temp = cfg.getTemplate(ftlName);
        temp.process(root, writer);
    }
}
