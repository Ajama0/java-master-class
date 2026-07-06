package com.abas.Users;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService underTest;


    @Test
    void findAllReturnsAllUsers() {
        //given
        List<User> users = List.of(
                new User(UUID.randomUUID(), "Abas"),
                new User(UUID.randomUUID(), "Jamila")
        );
        when(userDAO.getUsers()).thenReturn(users);

        //when
        List<User> result = underTest.findAll();

        //then
        assertThat(result).isEqualTo(users);
        verify(userDAO).getUsers();
        verifyNoMoreInteractions(userDAO);
    }

    @Test
    void findByIdReturnsUserWhenExists() {
        //given
        UUID id = UUID.randomUUID();
        User user = new User(id, "Abas");
        when(userDAO.getUsers()).thenReturn(List.of(user));

        //when
        User result = underTest.findById(id);

        //then
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo("Abas");
    }

    @Test
    void findByIdThrowsWhenUserDoesNotExist() {
        //given
        UUID id = UUID.randomUUID();
        when(userDAO.getUsers()).thenReturn(List.of());

        //when + then
        assertThatThrownBy(() -> underTest.findById(id))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("not found");
    }
}