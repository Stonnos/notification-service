package com.notification.mapping;

import com.notification.dto.EmailRequest;
import com.notification.model.Email;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Implements mapping mail request to email entity.
 */
@Mapper
public abstract class EmailRequestMapper {

    /**
     * Maps email request to email entity.
     *
     * @param emailRequest - email request
     * @return email entity
     */
    @Mappings(
            @Mapping(target = "status", constant = "NEW")
    )
    public abstract Email map(EmailRequest emailRequest);
}
