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
import ru.clevertec.ecl.api.responce.ErrorResponse;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.exception.ItemNotFoundException;
import ru.clevertec.ecl.exception.RequestParamsNotValidException;
import ru.clevertec.ecl.objectmothers.GiftCertificateObjectMother;
import ru.clevertec.ecl.pagination.Pagination;
import ru.clevertec.ecl.service.impl.GiftCertificateService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GiftCertificateControllerTest {
    private static final String BASE_URL = "/api/certificates";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    GiftCertificateService giftCertificateService;

    @Nested
    class CreateGiftCertificateTest {
        @Test
        void testCreateGiftCertificateSuccess() throws Exception {
            var input = GiftCertificateObjectMother.getGiftCertificateWithoutId();
            var output = GiftCertificateObjectMother.getGiftCertificate();
            when(giftCertificateService.create(input)).thenReturn(output);

            mockMvc.perform(
                            post(BASE_URL)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(input)))
                    .andExpect(status().isCreated());
        }

        @Test
        void testCreateGiftCertificateSuccessShouldReturnBadRequest() throws Exception {
            var input = new GiftCertificate();
            var output = new ErrorResponse(100400, "Name, description, duration, price required");
            when(giftCertificateService.create(input))
                    .thenThrow(new RequestParamsNotValidException("Name, description, duration, price required"));

            mockMvc.perform(
                            post(BASE_URL)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(input)))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(objectMapper.writeValueAsString(output)));
        }
    }

    @Nested
    class FindCertificateByIdTest {
        @Test
        void testGetGiftCertificateSuccess() throws Exception {
            var id = 1;
            var output = GiftCertificateObjectMother.getGiftCertificate();
            var expected = "{\"id\":1,\"name\":\"name\",\"description\":\"description\",\"price\":4.0,\"createDate\":null,\"lastUpdateDate\":null,\"duration\":4,\"tags\":[],\"_links\":{\"self\":{\"href\":\"http://localhost/api/certificates/1\"},\"giftcertificates\":{\"href\":\"http://localhost/api/certificates?page=1&size=20&sort=id&sort_mode=asc\"}}}";
            when(giftCertificateService.getById(id))
                    .thenReturn(output);

            mockMvc.perform(
                            get(String.format("%s/%d", BASE_URL, id))
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(expected));
        }

        @Test
        void testGetGiftCertificateShouldReturnNotFound() throws Exception {
            var id = 99;
            var expected = "{\"code\":100404,\"message\":\"Gift certificate id 99 not found\"}";
            when(giftCertificateService.getById(id))
                    .thenThrow(new ItemNotFoundException(String.format("Gift certificate id %d not found", id)));

            mockMvc.perform(
                            get(String.format("%s/%d", BASE_URL, id))
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(expected));
        }
    }

    @Nested
    class DeleteGiftCertificateTest {
        @Test
        void testDeleteGiftCertificateSuccess() throws Exception {
            var id = 1;
            when(giftCertificateService.delete(id))
                    .thenReturn(true);

            mockMvc.perform(
                            delete(String.format("%s/%d", BASE_URL, id))
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }

        @Test
        void testDeleteGiftCertificateShouldReturnNotFound() throws Exception {
            var id = 1;
            when(giftCertificateService.delete(id)).thenThrow(new ItemNotFoundException(String.format("Gift certificate id %d not found", id)));
            var expected = "{\"code\":100404,\"message\":\"Gift certificate id 1 not found\"}";

            mockMvc.perform(delete(String.format("%s/%d", BASE_URL, id))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(expected));
        }
    }

    @Nested
    class GetAllGiftCertificatesTest {
        @Test
        void testGetAllShouldReturnSuccessWithPagination() throws Exception {
            var pagination = new Pagination<GiftCertificate>(2, 1, 1);
            pagination.setContent(GiftCertificateObjectMother.getListOfGiftCertificates());
            when(giftCertificateService.getAll(1, 20, "id", "asc"))
                    .thenReturn(pagination);
            var expected = "{\"content\":{\"_embedded\":{\"giftCertificateList\":[{\"id\":10112,\"name\":\"string\",\"description\":\"string\",\"price\":4.0,\"createDate\":\"2023-07-04T13:04:59.2804217\",\"lastUpdateDate\":\"2023-07-04T13:04:59.2804217\",\"duration\":4,\"tags\":[],\"_links\":{\"self\":{\"href\":\"http://localhost:8080/api/certificates/10112\"},\"giftcertificates\":{\"href\":\"http://localhost:8080/api/certificates?page=1&size=20&sort=id&sort_mode=asc\"}}},{\"id\":10113,\"name\":\"string\",\"description\":\"string\",\"price\":4.0,\"createDate\":\"2023-07-04T13:05:00.1805524\",\"lastUpdateDate\":\"2023-07-04T13:05:00.1805524\",\"duration\":4,\"tags\":[],\"_links\":{\"self\":{\"href\":\"http://localhost:8080/api/certificates/10113\"},\"giftcertificates\":{\"href\":\"http://localhost:8080/api/certificates?page=1&size=20&sort=id&sort_mode=asc\"}}}]},\"_links\":{\"self\":{\"href\":\"http://localhost:8080/api/certificates?page=1&size=20&sort=id&sort_mode=asc\"}}},\"size\":20,\"currentPage\":1,\"overallPages\":1,\"_links\":{\"self\":{\"href\":\"http://localhost:8080/api/certificates?page=1&size=20&sort=id&sort_mode=asc\"}}}";


            mockMvc.perform(
                            get(BASE_URL + "?page=1&size=20&sort=id&sort_mode=asc")
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(expected));
        }
    }
}