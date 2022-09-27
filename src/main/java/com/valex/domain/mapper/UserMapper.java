package com.valex.domain.mapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.valex.domain.dto.UserDto;
import com.valex.domain.model.Card;
import com.valex.domain.model.User;
import com.valex.domain.request.UserRequest;
import com.valex.utils.Encoder;
import java.util.Collection;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.ToString.Exclude;
import org.mapstruct.Mapper;
import org.springframework.security.core.GrantedAuthority;

@Mapper (componentModel = "spring")
public class UserMapper {

  public UserDto requestToDto (UserRequest userRequest) {
    UserDto userDto = new UserDto();
    userDto.setName(userRequest.getName());
    userDto.setEmail(userRequest.getEmail());
    userDto.setPassword(userRequest.getPassword());
    userDto.setCpf(userRequest.getCpf());

    return userDto;
  }

  public User requestToModel (UserRequest userRequest) {
    UserDto userDto = this.requestToDto(userRequest);

    User user = new User();
    user.setName(user.getName());
    user.setEmail(userDto.getEmail());
    user.setCpf(userDto.getCpf());
    user.setPassword(Encoder.encode(userDto.getPassword()));

    return user;
  }

}
