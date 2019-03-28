package com.hansholdings.autoconfigure.frame;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

@Component
@ConfigurationProperties(prefix = "hansholdings.frame")
public class FrameVariablesInterceptor extends HandlerInterceptorAdapter {

    private Map<String, String> variables;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && variables != null && !variables.isEmpty()) {
            Set<String> keySet = variables.keySet();
            for (String key : keySet) {
                modelAndView.addObject(key, variables.get(key));
            }
        }
    }
    
    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }
    
    public Map<String, String> getVariables() {
        return variables;
    }
}
