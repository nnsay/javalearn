package com.neuralgalaxy.commons.web.resolver;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.neuralgalaxy.commons.asserts.AssertException;
import com.neuralgalaxy.commons.message.MessageService;
import com.neuralgalaxy.commons.utilities.Locales;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220227
 */
@Slf4j
public class AssertExceptionJsonSerializer extends JsonSerializer<AssertException> {


    MessageService messageService;

    public AssertExceptionJsonSerializer(MessageService service) {
        this.messageService = service;
    }

    @Override
    public void serialize(AssertException e, JsonGenerator jg,
                          SerializerProvider serializerProvider) throws IOException {

        String code = e.getCode();
        String message = e.getMessage();

        String overwriteMessage = this.messageService.getOverwriteMessage(Locales.mustGet(), code);
        if (overwriteMessage != null) {
            if (log.isDebugEnabled()) {
                e.printStackTrace();
                log.debug("overwrite error message: {} from {} -> {}", code, message, overwriteMessage);
            }
            message = overwriteMessage;
        }

        jg.writeStartObject();
        jg.writeFieldName("error");
        jg.writeString(code);
        jg.writeFieldName("message");
        jg.writeString(message);
        jg.writeEndObject();
    }
}
