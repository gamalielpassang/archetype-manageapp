/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alpha.gitparamreader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import lombok.Data;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 *
 * @author Nappster-SPRINT-PAY
 */
@Data
public class GitParamGetter {
    
    private final String targetBase = "/tmp/targetdirectory";
    
    private String targetCurrent;
    
    private String repositoryPath;
    
    private String configfilePath;
    
    private String banchName;
    
    private Git repository;
    
    private Properties propertyfile = null;

    public GitParamGetter(String repository) throws GitAPIException, FileNotFoundException{
        
        this.repositoryPath = repository;
        this.targetCurrent = targetBase+"-"+(new Date().getTime());
        this.repository = Git.cloneRepository()
                .setURI(repository)
                .setDirectory(new File(targetCurrent))
                .call();
        
    }
    
    public GitParamGetter(String repository, String configfile) throws GitAPIException, FileNotFoundException{
        
        this.configfilePath = configfile;
        this.repositoryPath = repository;
        this.targetCurrent = targetBase+"-"+(new Date().getTime());
        this.repository = Git.cloneRepository()
                .setURI(repository)
                .setDirectory(new File(targetCurrent))
                .call();
        
        this.propertyfile = this.loadPropertiesFromClassPath(configfile);
        
    }
    
    public GitParamGetter(String repository, String configfile, String branch) throws GitAPIException, FileNotFoundException{
        this.configfilePath = configfile;
        this.repositoryPath = repository;
        this.banchName = branch;
        this.targetCurrent = targetBase+"-"+(new Date().getTime());
        this.repository = Git.cloneRepository()
                .setURI(repository)
                .setDirectory(new File(targetCurrent))
                .call();
        
        this.propertyfile = this.loadPropertiesFromClassPath(this.targetCurrent + configfile);
        
    }
    
    public Properties loadPropertiesFromClassPath(String resourceName) throws FileNotFoundException {

        Properties properties = new Properties();
        InputStream input = new FileInputStream(resourceName);
        try {
                properties.load(input);
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
        return properties;
    }
    
    public String getParam(String key) throws Exception{
        if(propertyfile == null)
            throw new Exception("Configuration file not set on initialization");
        return propertyfile.getProperty(key);
    }

    public String getParam(String configfile, String key) throws Exception{
        if(this.configfilePath == null || !this.configfilePath.equals(configfile)){
            Properties prop = this.loadPropertiesFromClassPath(this.targetCurrent + configfile);
            if(prop != null){
                if(this.configfilePath == null){
                    this.configfilePath = configfile;
                    this.propertyfile = prop;
                }else
                    return prop.getProperty(key);
            }
        }
        return propertyfile.getProperty(key);
    }

    public void setConfigFile(String configfile) throws FileNotFoundException{
        this.configfilePath = configfile;
        this.propertyfile = this.loadPropertiesFromClassPath(this.targetCurrent + configfile);
    }
    
}
