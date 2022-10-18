package com.valex.service;

import static com.valex.domain.mother.UserMother.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

import com.valex.domain.dto.UserDto;
import com.valex.domain.exception.ConflictException;
import com.valex.domain.mapper.impl.UserMapper;
import com.valex.domain.exception.NotFoundException;
import com.valex.domain.model.User;
import com.valex.repository.UserRepository;
import com.valex.service.impl.UserServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceUnitTest {

  @InjectMocks
  private UserServiceImpl userService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private UserMapper userMapper;

  @BeforeEach
  void setUp () {
    openMocks(this);
  }

  @Test
  void whenFindByIdOrThrowWithExistingIdThenReturnAnUser () {
    Optional<User> optionalUser = getOptionalUser();
    User user = optionalUser.get();

    given(userRepository.findById(anyLong())).willReturn(optionalUser);

    User response = userService.findByIdOrFail(user.getId());

    then(response.getClass()).isEqualTo(User.class);
    then(response.getId()).isEqualTo(user.getId());
  }

  @Test
  void whenFindByIdOrThrowWithNoExistingIdThenReturnThrowNotFoundException () {
    given(userRepository.findById(anyLong())).willReturn(getEmptyOptionalUser());

    try {
      userService.findByIdOrFail(anyLong());

    } catch (Exception e) {
      then(e.getClass()).isEqualTo(NotFoundException.class);
      then(e.getMessage()).isEqualTo("User not found");
    }
  }

  @Test
  void whenFindAllThenReturnUserList () {
    UserDto userDto = getUserDto();
    User user = getUser();

    given(userRepository.findAll()).willReturn(List.of(user));
    given(userMapper.modelToDto(anyList())).willReturn(List.of(userDto));

    List<UserDto> response = userService.findAll();

    verify(userMapper).modelToDto(List.of(user));
    verify(userRepository).findAll();

    then(response.size()).isEqualTo(1);
    then(response.get(0).getId()).isEqualTo(userDto.getId());
    then(response.get(0).getClass()).isEqualTo(UserDto.class);
  }

  @Test
  void whenCreateThenReturnUserDto () {
    User user = getUser();
    UserDto userDtoWithId = getUserDto();
    UserDto userDtoWithoutId = getUserDtoWithoutId();

    given(userRepository.findByEmail(anyString())).willReturn(null);
    given(userMapper.dtoToModel(any(UserDto.class))).willReturn(user);
    given(userRepository.save(any(User.class))).willReturn(user);
    given(userMapper.modelToDto(any(User.class))).willReturn(userDtoWithId);

    UserDto response = userService.create(userDtoWithoutId);

    verify(userMapper).dtoToModel(userDtoWithoutId);
    verify(userMapper).modelToDto(user);

    then(response.getClass()).isEqualTo(UserDto.class);
    then(response.getId()).isEqualTo(userDtoWithId.getId());
  }

  @Test
  void whenTryCreateExistEmailUserThenReturnConflictException () {
    given(userRepository.findByEmail(anyString())).willReturn(getUser());

    try {
      userService.create(getUserDtoWithoutId());

    } catch (Exception e) {
      then(e.getClass()).isEqualTo(ConflictException.class);
      then(e.getMessage()).isEqualTo("This email already exist");
    }
  }

  @Test
  void deleteWithSuccess () {
    given(userRepository.findById(anyLong())).willReturn(getOptionalUser());
    willDoNothing().given(userRepository).deleteById(anyLong());

    userService.delete(anyLong());

    verify(userRepository, times(1)).deleteById(anyLong());
  }
}