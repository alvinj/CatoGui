package com.alvinalexander.cato;

import java.io.*;
import java.util.*;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class TemplateEngine {

    public static String applyDataToTemplate(String templateString, HashMap<String, Object> data) 
    throws Exception {
        Configuration cfg = new Configuration();
        Template template = new Template("transformer", new StringReader(templateString), cfg);
        Writer output = new StringWriter();
        template.process(data, output);
        return output.toString();
    }

}

