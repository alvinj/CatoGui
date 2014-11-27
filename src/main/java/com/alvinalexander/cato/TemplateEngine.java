package com.alvinalexander.cato;

import java.io.*;
import java.util.*;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class TemplateEngine {

    /**
     * This method applies the data its given to the templateString it's given.
     * The `rootTemplateDir` also lets you specify the root of your templates directory. This lets you
     * use `include` and `import` statements in your templates.
     *  
     * @param rootTemplateDir Like _/Users/Al/Projects/Scala/CatoGui/resources/templates/Sencha_
     * @param templateString
     * @param data
     * @return
     * @throws Exception
     */
    public static String applyDataToTemplate(String rootTemplateDir, String templateString, HashMap<String, Object> data) 
    throws Exception {
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(rootTemplateDir));
        Template template = new Template("transformer", new StringReader(templateString), cfg);
        Writer output = new StringWriter();
        template.process(data, output);
        return output.toString();
    }

}

