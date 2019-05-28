package me.chen.web;

import com.google.gson.Gson;
import me.chen.core.Reflex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @Author: ftdcs
 * @Date: 2019/05/17 0013 08:47
 * @Version 1.0
 */
@Controller
@RequestMapping("/test")
public class TestWeb {

    @Autowired
    Reflex reflex;

    @RequestMapping("/test")
    @ResponseBody
    public String test(@RequestParam(value="methodName")String methodName, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        Object invoke = reflex.invoke(methodName);
        String json;
        if(invoke instanceof String){
            json = (String) invoke;
        }else{
            json = gson.toJson(invoke);
        }
        if(json.contains("\n")){
            String[] strings = json.split("\\\\n");
            for (String string : strings) {
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.println(string);
            }
        }else{
            return json;
        }
        return null;
    }

    @RequestMapping("/methodList")
    @ResponseBody
    public List<Reflex.ClassDescribe> methodList(){
        List<Reflex.ClassDescribe> classDescribeList = reflex.getClassDescribeList();
        return classDescribeList;
    }

    @RequestMapping("/index")
    public String index(){
        return "pages/index";
    }
}
