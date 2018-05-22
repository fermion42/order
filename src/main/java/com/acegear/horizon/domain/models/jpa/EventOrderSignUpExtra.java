package com.acegear.horizon.domain.models.jpa;

import com.acegear.horizon.domain.models.constraint.SignTemplateType;
import com.acegear.horizon.domain.models.jpa.converter.ContentPartConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by guoweike on 17/6/30.
 */
@Entity
public class EventOrderSignUpExtra {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;

    private String value;

    private SignTemplateType type;

    public EventOrderSignUpExtra() {
    }

    public EventOrderSignUpExtra(String name, String value, SignTemplateType type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SignTemplateType getType() {
        return type;
    }

    public void setType(SignTemplateType type) {
        this.type = type;
    }
}
