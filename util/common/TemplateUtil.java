package org.solar.editor.core.util.common;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class TemplateUtil {
    public static void main(String[] args) {

        Map map = new HashMap();
        map.put("a", 1234567);
        System.out.println(processStringTemplate("${a}", map));

    }

    public static String process(String template_file_path, Object dataModel) {
        File template_file = new File(template_file_path);
        return process(template_file, dataModel);
    }

    public static String process(File template_file, Object dataModel) {
        // step1 创建freeMarker配置实例
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setNumberFormat("###############.###");
        // 去掉逗号
        Writer out = null;
        try {
            // step2 获取模版路径
            String template_path = template_file.getParent();
            configuration.setDirectoryForTemplateLoading(new File(template_path));
            // step3 创建数据模型
            // step4 加载模版文件
            Template template = configuration.getTemplate(template_file.getName());
            // step5 生成数据
            StringWriter stringWriter = new StringWriter();
            // step6 输出文件
            template.process(dataModel, stringWriter);
            String result = stringWriter.toString();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     *
     */
    public static String processStringTemplate(String templateContent, Object dataModel) {
        if (dataModel == null) {
            return templateContent;
        }
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setNumberFormat("###############.###");
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate("myTemplate", templateContent);
        cfg.setTemplateLoader(stringLoader);
        try {
            Template template = cfg.getTemplate("myTemplate", "utf-8");
            StringWriter stringWriter = new StringWriter();
            template.process(dataModel, stringWriter);
            String result = stringWriter.toString();
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}