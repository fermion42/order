package com.acegear.horizon.domain.models.jpa.converter;

import com.acegear.horizon.domain.models.jpa.EventOrderSignUpExtra;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javaslang.jackson.datatype.JavaslangModule;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

/**
 * Created by guoweike on 17/6/30.
 */
@Converter(autoApply = true)
public class SignUpExtraConverter  implements AttributeConverter<List<EventOrderSignUpExtra>, String> {

    private ObjectMapper mapper;

    public SignUpExtraConverter() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaslangModule());
    }

    @Override
    public String convertToDatabaseColumn(List<EventOrderSignUpExtra> attribute) {
        if (attribute == null) return null;
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<EventOrderSignUpExtra> convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            return mapper.readValue(dbData, new TypeReference<List<EventOrderSignUpExtra>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

