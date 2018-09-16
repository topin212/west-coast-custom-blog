package com.github.topin212.web.sboot.blog.util.localDateTimeSerDes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("timestamp", Timestamp.valueOf(localDateTime).getTime());

        jsonGenerator.writeStringField("iso", localDateTime.format(DateTimeFormatter.ISO_DATE_TIME));

        jsonGenerator.writeEndObject();
    }
}
