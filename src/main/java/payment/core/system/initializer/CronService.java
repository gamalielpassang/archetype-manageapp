/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payment.core.system.initializer;

import java.io.FileNotFoundException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nappster-SPRINT-PAY
 */
@Component
@EnableScheduling
public class CronService {
    
    @Value("${repository.url}")
    String reopUrl;
    
    @Value("${repository.configfile}")
    String configFileName;
    
    @Value("${repository.branch}")
    String branch;
    
    @Value("${repository.key}")
    String keyName;
    
    @Scheduled(cron = "${cron}")
    public void doOperation() throws GitAPIException, FileNotFoundException, Exception{
    
        GitParamGetter getter = new GitParamGetter(reopUrl, configFileName, branch);
        
        String paramValue = getter.getParam(keyName);
        
        System.out.println("value = "+paramValue);
        // Ici effectuer le traitement en fonction de la vaiable
        
    }
    
}
