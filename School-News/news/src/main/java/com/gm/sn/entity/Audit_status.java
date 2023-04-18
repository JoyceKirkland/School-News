package com.gm.sn.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * Book entity.
 */
@Data
@Entity
@Table(name = "audit_status")
@ToString
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@NoArgsConstructor
public class Audit_status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String status;


//    @ManyToOne
//    @JoinColumn(name="cid")
//    private Category category;


}
