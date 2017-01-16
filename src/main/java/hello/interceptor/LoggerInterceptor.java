package hello.interceptor;

import com.nimbusds.jwt.SignedJWT;
import hello.loggers.LoggerInterface;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class LoggerInterceptor extends HandlerInterceptorAdapter {

    static Logger logger = Logger.getLogger(LoggerInterceptor.class);

    @Autowired
    private LoggerInterface[] loggers;

    static{
        BasicConfigurator.configure();
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("Authorization").replace("Bearer", "");
        String username = SignedJWT.parse(accessToken).getJWTClaimsSet().getCustomClaim("username").toString();
        String lineToLog = "The user " + username + " is requesting " + request.getRequestURI() + " at " + LocalDateTime.now().toString();
        Arrays.stream(loggers).forEach(l -> l.write(lineToLog));
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        logger.info("After handling the request");
        super.postHandle(request, response, handler, modelAndView);
    }
}