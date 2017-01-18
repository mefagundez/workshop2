package hello.loggers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ConsoleLogger implements LoggerInterface {
    static Logger logger = Logger.getLogger(ConsoleLogger.class);

    @Override
    public void write(String line) {
        logger.info(line);
    }
}
