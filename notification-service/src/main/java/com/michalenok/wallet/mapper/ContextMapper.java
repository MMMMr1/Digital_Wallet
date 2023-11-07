package com.michalenok.wallet.mapper;

import com.michalenok.wallet.kafka.schema.Verification;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.thymeleaf.context.Context;
import java.util.HashMap;
import java.util.Map;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContextMapper {

    default Context verificationToContext (Verification messageDto) {
        Context context = new Context();
        Map<String, Object> forVerificationContext = new HashMap<>();
        forVerificationContext.put("code", messageDto.getCode());
        forVerificationContext.put("mail", messageDto.getMail());
        context.setVariables(forVerificationContext);
        return context;
    }
}
