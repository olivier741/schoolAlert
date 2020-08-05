/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.school;

import com.tatsinktech.web.model.AbstractModel;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author olivier.tatsinkou
 */
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "rel_stud_eval")
public class RelStudent_Evaluation extends AbstractModel<Long>{
        
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = true)
    private Student student; 
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "exam_time", nullable = true)
    public Date examTime;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "evaluation_id", nullable = true)
    private Evaluation evaluation; 
}
