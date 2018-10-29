package ru.lvrv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.lvrv.models.SystemConfig;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Controller
public class MainController {
    @Autowired
    SystemConfig systemConfig;

    /**
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String empty() {
        return "redirect:/home";
    }


    /**
     *
     * @return
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    /**
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public String client(ModelMap modelMap) {

        Path config = Paths.get(systemConfig.getConfigFilePath());
        if (!Files.exists(config)) {
            return "error";
        }
        Properties properties = new Properties();
        String appServerPath, fileServerPath, dbname, login, pwd;
        try {
            properties.load(Files.newInputStream(config));
            appServerPath = properties.getProperty("appServerPath");
            modelMap.put("appServerPath", appServerPath);
            fileServerPath = properties.getProperty("fileServerPath");
            modelMap.put("fileServerPath", fileServerPath);
            dbname = properties.getProperty("dbname");
            modelMap.put("dbname", dbname);
            login = properties.getProperty("login");
            modelMap.put("login", login);
            pwd = properties.getProperty("pwd");
            modelMap.put("pwd", pwd);
        } catch (IOException e) {
            return "error";
        }

        return "config";
    }
}
