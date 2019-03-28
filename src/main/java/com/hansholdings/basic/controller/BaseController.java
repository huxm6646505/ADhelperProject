package com.hansholdings.basic.controller;

import com.hansholdings.basic.beans.DateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Date;

/**
 * Created by zhaohl on 2015/5/8.
 */
public class BaseController {

    protected static final String ERROR_PAGE = "/admin/common/error";

    @InitBinder
    protected void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.registerCustomEditor(Date.class, new DateEditor(true));
    }
}
