/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.repository;

import com.tatsinktech.web.model.register.Action;
import com.tatsinktech.web.model.register.Command;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author olivier.tatsinkou
 */
@Repository
public interface CommandRepository extends PagingAndSortingRepository<Command, String> {

    Command findCommandById(long id);
    
    Command findCommandByCommandName(String commandName);

    Page<Command> findByIdContainingIgnoreCase(long id, Pageable pageable);

    Page<Command> findByCommandNameContainingIgnoreCase(String commandName, Pageable pageable);

    Page<Command> findByCommandCodeContainingIgnoreCase(String commandCode, Pageable pageable);

    Page<Command> findBySplitSeparatorContainingIgnoreCase(String splitSeparator, Pageable pageable);

    Page<Command> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);

    Page<Command> findByCreateTimeContainingIgnoreCase(Date create_time, Pageable pageable);

    Page<Command> findByUpdateTimeContainingIgnoreCase(Date update_time, Pageable pageable);

}
