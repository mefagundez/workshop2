package hello.loggers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

@Component
public class FileLogger implements LoggerInterface {

    static Logger logger = Logger.getLogger(FileLogger.class);

    @Override
    public void write(String line) {
        List<String> logLines = Collections.singletonList(line);
        Path file = Paths.get("log.txt");
        try {
            Files.write(file, logLines, UTF_8, APPEND, CREATE);
        } catch (IOException ex) {
            logger.info("Error trying to write the message to the file");
        }

    }
}
