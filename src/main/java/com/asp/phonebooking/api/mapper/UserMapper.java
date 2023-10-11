package com.asp.phonebooking.api.mapper;

import com.asp.phonebooking.api.dto.ReadUserDTO;
import com.asp.phonebooking.api.dto.UserRole;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Mapper
public interface UserMapper {

    @Mapping(target = "roles", source = "authorities")
    ReadUserDTO toReadUserDto(UserDetails userDetails);

    static Set<UserRole> roles(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .map(UserRole::valueOf)
                .collect(Collectors.toUnmodifiableSet());
    }
}
