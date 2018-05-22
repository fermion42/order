package com.acegear.horizon.domain.models.jpa.converter;

import com.acegear.horizon.domain.models.jpa.ContentPart;
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
public class ContentPartConverter implements AttributeConverter<List<ContentPart>, String> {

    private ObjectMapper mapper;

    public ContentPartConverter() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaslangModule());
    }

    @Override
    public String convertToDatabaseColumn(List<ContentPart> attribute) {
        if (attribute == null) return null;
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ContentPart> convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            return mapper.readValue(dbData, new TypeReference<List<ContentPart>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

