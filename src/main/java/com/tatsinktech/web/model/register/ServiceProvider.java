/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.register;

import com.tatsinktech.web.model.AbstractModel;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author olivier
 */
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "listProduct")
@Table(name = "service")
public class ServiceProvider extends AbstractModel<Long> {

    @Column(name = "service_name", nullable = false, unique = true)
    private String serviceName;

    @Column(name = "receive_channel", nullable = true)
    private String receiveChannel;

    @Column(name = "send_channel", nullable = true)
    private String sendChannel;

    @Column(name = "service_provider", nullable = true)
    private String serviceProvider;
    
    @Column(name = "operator", nullable = true)
    private String operator;

    @Column(name = "isResetProviderID", nullable = true)
    private Boolean isResetProviderID;

    @Column(name = "service_provider_id", nullable = true)
    private String serviceProviderID;

    @Column(name = "service_provider_salt", nullable = true)
    private String serviceProviderSalt;

    @OneToMany(mappedBy = "service")
    private Set<Product> listProduct = new HashSet<>();


}
