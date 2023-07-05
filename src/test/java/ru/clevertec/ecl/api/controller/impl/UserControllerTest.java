package ru.clevertec.ecl.api.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.ecl.entity.User;
import ru.clevertec.ecl.objectmothers.OrderObjectMother;
import ru.clevertec.ecl.objectmothers.UserObjectMother;
import ru.clevertec.ecl.service.impl.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    private static final String BASE_URL = "/api/users";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Nested
    class CreateUserTest {
        @Test
        void testCreateUserShouldReturnUnsupported() throws Exception {
            var output = "{\"code\":100405,\"message\":\"Create operation for User is not supported\"}";
            when(userService.create(any(User.class)))
                    .thenThrow(new UnsupportedOperationException("Create operation for User is not supported"));

            mockMvc.perform(
                            post(BASE_URL)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(new User())))
                    .andExpect(status().isMethodNotAllowed())
                    .andExpect(content().string(output));
        }

    }

    @Nested
    class UpdateUserTest {
        @Test
        void testUpdateUserShouldReturnUnsupported() throws Exception {
            var output = "{\"code\":100405,\"message\":\"Update operation for User is not supported\"}";
            when(userService.create(any(User.class)))
                    .thenThrow(new UnsupportedOperationException("Update operation for User is not supported"));

            mockMvc.perform(
                            patch(BASE_URL)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(new User())))
                    .andExpect(status().isMethodNotAllowed())
                    .andExpect(content().string(output));
        }
    }

    @Nested
    class DeleteUserTest {
        @Test
        void testDeleteUserShouldReturnUnsupported() throws Exception {
            var output = "{\"code\":100405,\"message\":\"Delete operation for User is not supported\"}";
            when(userService.create(any(User.class)))
                    .thenThrow(new UnsupportedOperationException("Delete operation for User is not supported"));

            mockMvc.perform(
                            delete(BASE_URL)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(new User())))
                    .andExpect(status().isMethodNotAllowed())
                    .andExpect(content().string(output));
        }
    }

    @Nested
    class AddCertificate {
        @Test
        void testAddUserCertificateSuccess() throws Exception {
            var user = UserObjectMother.getUser();
            var order = OrderObjectMother.getOrder();
            user.addOrder(order);
            var userId = 1;
            var giftCertificateId = 11;
            var output = "{\"id\":1,\"firstName\":\"Riley\",\"lastName\":\"Stokes\",\"orders\":[{\"id\":3000,\"giftCertificate\":{\"id\":10112,\"name\":\"string\",\"description\":\"string\",\"price\":4.0,\"createDate\":\"2023-07-04T13:04:59.2804217\",\"lastUpdateDate\":\"2023-07-04T13:04:59.2804217\",\"duration\":4,\"tags\":[]},\"cost\":4.0,\"purchaseDate\":\"2023-07-04T15:49:36.0102754\"}],\"_links\":{\"self\":{\"href\":\"http://localhost:8080/api/users/1\"},\"users\":{\"href\":\"http://localhost:8080/api/users?page=1&size=20&sort=id&sort_mode=asc\"}}}";
            when(userService.addCertificate(userId, giftCertificateId))
                    .thenReturn(user);

            mockMvc.perform(
                            put(String.format("%s/%d/%d", BASE_URL, userId, giftCertificateId))
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(new User())))
                    .andExpect(status().isMethodNotAllowed())
                    .andExpect(content().string(output));
        }
    }
}