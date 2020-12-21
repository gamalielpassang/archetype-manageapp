/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sprintpay.kyc.conformity.initializer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * 
 */
@Component
@EnableScheduling
public class CronService {
    
    String repoUrl;
    
    String configFileName;
    
    String branch;
    
    String keyName;
    
    /*@Autowired
    private ApplicationContext appContext;*/
    
    @Scheduled(cron = "* * * * * *")
    public void doOperation() throws GitAPIException, FileNotFoundException, Exception{
        
        Properties ppt = loadPropertiesFromClassPath("app.properties");
        
        repoUrl = ppt.getProperty("repository.url");
        configFileName = ppt.getProperty("repository.configfile");
        branch = ppt.getProperty("repository.branch");
        keyName = ppt.getProperty("repository.key");
                
        GitParamGetter getter = new GitParamGetter(repoUrl, configFileName, branch);
        
        String paramValue = getter.getParam(keyName);
        if(!Boolean.valueOf(paramValue)){
            ConfigurableApplicationContext ctx = new SpringApplicationBuilder(PcsInitializerApplication.class).web(WebApplicationType.NONE).run();
            ctx.getBean(TerminateBean.class);
            ctx.close();
        }
    }

    private Properties loadPropertiesFromClassPath(String resourceName) {

        Properties properties = new Properties();
        InputStream input = CronService.class.getClassLoader().getResourceAsStream(resourceName);
        try {
                properties.load(input);
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
        return properties;

    }

}
