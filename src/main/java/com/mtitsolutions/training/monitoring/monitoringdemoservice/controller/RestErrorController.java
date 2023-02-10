package com.mtitsolutions.training.monitoring.monitoringdemoservice.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RestErrorController implements ErrorController {
    @RequestMapping("/error")
    public Map<String, Object> handleError(HttpServletRequest request) {
        var res = new HashMap<String, Object>();
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null) {
            res.put("status", Integer.valueOf(status.toString()));
        }

        return res;
    }
}
