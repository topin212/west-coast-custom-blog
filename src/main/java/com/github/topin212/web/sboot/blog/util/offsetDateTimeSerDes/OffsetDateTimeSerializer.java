package com.github.topin212.web.sboot.blog.util.offsetDateTimeSerDes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeSerializer extends JsonSerializer<OffsetDateTime> {
    @Override
    public void serialize(OffsetDateTime offsetDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("timestamp", offsetDateTime.toInstant().getEpochSecond());

        jsonGenerator.writeStringField("iso", offsetDateTime.format(DateTimeFormatter.ISO_DATE_TIME));

        jsonGenerator.writeEndObject();
    }
}
