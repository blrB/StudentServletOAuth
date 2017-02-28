package by.bsuir.aipos.servlet;

import org.apache.log4j.Logger;

public class StudentLogger {

    /**
     * Logger for student servlet
     */
    private static final Logger logger = Logger.getLogger(StudentLogger.class);

    public static Logger getLogger() {
        return logger;
    }
}
