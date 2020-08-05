/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.thread;

import com.tatsinktech.push.config.Load_Configuration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.tatsinktech.push.model.repository.Mo_PushRepository;

/**
 *
 * @author olivier.tatsinkou
 */
@Component
public class ScanProcess {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static int val = 0;

    @Autowired
    private Mo_PushRepository loadRepo;

    @Scheduled(fixedDelayString  = "${application.web.scheduler-fixedDelay}")
    public void scheduleTaskWithFixedDelay() {
        logger.info("############## LOAD MO_PUSH #########################");
        loadRepo.loadMoPush(new Date());
    }
}
