package com.example.advisor_backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.repository.cdi.Eager;

@Setter
@Getter
@Entity
@Table(name = "node_factor_relation")
public class NodeFactorRelation {
    // getter/setter
    @Id
    private Long id;
    private Long nodeId;
    private Long factorId;

}