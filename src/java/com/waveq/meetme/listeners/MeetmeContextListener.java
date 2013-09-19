/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waveq.meetme.listeners;

import java.util.Date;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.waveq.meetme.config.DBManager;

/**
 * Web application lifecycle listener.
 * @author Szym
 */
public class MeetmeContextListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DBManager.getManager().createEntityManagerFactory();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBManager.getManager().closeEntityManagerFactory();
    }
}
