/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sprintpay.kyc.conformity.initializer;

import javax.annotation.PreDestroy;
import org.springframework.stereotype.Component;

/**
 *
 * @author no-name
 */
@Component
public class TerminateBean {
    
    @PreDestroy
    public void onDestroy() throws Exception {
        System.out.println(" ");
        //System.out.println("Spring Container is destroyed!");
    }
    
}
