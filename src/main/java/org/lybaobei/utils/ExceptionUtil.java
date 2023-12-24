package org.lybaobei.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Slf4j
public class ExceptionUtil {

    private static final Logger log = LoggerFactory.getLogger(ExceptionUtil.class);

    public static String getMessage(Exception e){
        String swStr = null;
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
//            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
            swStr = sw.toString();
        } catch (IOException ex) {
//            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return swStr;
    }
}
