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
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.ItemNotFoundException;
import ru.clevertec.ecl.exception.RequestParamsNotValidException;
import ru.clevertec.ecl.objectmothers.TagObjectMother;
import ru.clevertec.ecl.service.impl.TagService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TagControllerTest {
    private static final String BASE_URL = "/api/tags";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TagService tagService;

    @Nested
    class CreateTagTest {
        @Test
        void testCreateTagSuccess() throws Exception {
            var input = TagObjectMother.getTagWithoutId();
            var output = TagObjectMother.getTag();
            when(tagService.create(input))
                    .thenReturn(output);

            mockMvc.perform(
                            post(BASE_URL)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(input)))
                    .andExpect(status().isCreated());
        }

        @Test
        void testCreateTagSuccessShouldReturnBadRequest() throws Exception {
            var input = new Tag();
            var output = new ErrorResponse(100400, "Empty body");

            when(tagService.create(input))
                    .thenThrow(new RequestParamsNotValidException("Empty body"));

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
    class FindTagByIdTest {
        @Test
        void testGetTagSuccess() throws Exception {
            var id = 1;
            var output = TagObjectMother.getTag();
            var expected = "{\"id\":1,\"name\":\"tag name\",\"_links\":{\"self\":{\"href\":\"http://localhost/api/tags/1\"},\"tags\":{\"href\":\"http://localhost/api/tags?page=1&size=20&sort=id&sort_mode=asc\"}}}";
            when(tagService.getById(id))
                    .thenReturn(output);

            mockMvc.perform(
                            get(String.format("%s/%d", BASE_URL, id))
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(expected));
        }

        @Test
        void testGetTagShouldReturnNotFound() throws Exception {
            var id = 99;
            var expected = "{\"code\":100404,\"message\":\"Tag with id 99 not found\"}";
            when(tagService.getById(id))
                    .thenThrow(new ItemNotFoundException(String.format("Tag with id %d not found", id)));

            mockMvc.perform(
                            get(String.format("%s/%d", BASE_URL, id))
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(expected));
        }
    }

    @Nested
    class DeleteTagTest {
        @Test
        void testDeleteTagSuccess() throws Exception {
            var id = 1;
            when(tagService.delete(id))
                    .thenReturn(true);

            mockMvc.perform(
                            delete(String.format("%s/%d", BASE_URL, id))
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }

        @Test
        void testDeleteTagShouldReturnNotFound() throws Exception {
            var id = 1;
            when(tagService.delete(id))
                    .thenThrow(new ItemNotFoundException(String.format("Tag with id %d not found", id)));
            var expected = "{\"code\":100404,\"message\":\"Tag with id 1 not found\"}";

            mockMvc.perform(
                            delete(String.format("%s/%d", BASE_URL, id))
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(expected));
        }
    }

    @Nested
    class GetAllTagsTest {
        @Test
        void testGetAllShouldReturnSuccessWithPagination() throws Exception {
            var pagination = TagObjectMother.getTagPagination();
            pagination.setContent(TagObjectMother.getTagList());
            when(tagService.getAll(1, 20, "id", "asc"))
                    .thenReturn(pagination);
            var expected = "{\"content\":{\"_embedded\":{\"tagList\":[{\"id\":1138,\"name\":\"Tag name too\",\"_links\":{\"self\":{\"href\":\"http://localhost:8080/api/tags/1138\"},\"tags\":{\"href\":\"http://localhost:8080/api/tags?page=1&size=20&sort=id&sort_mode=asc\"}}},{\"id\":1139,\"name\":\"Tag name too\",\"_links\":{\"self\":{\"href\":\"http://localhost:8080/api/tags/1139\"},\"tags\":{\"href\":\"http://localhost:8080/api/tags?page=1&size=20&sort=id&sort_mode=asc\"}}}]},\"_links\":{\"self\":{\"href\":\"http://localhost:8080/api/tags?page=1&size=20&sort=id&sort_mode=asc\"}}},\"size\":20,\"currentPage\":1,\"overallPages\":1,\"_links\":{\"self\":{\"href\":\"http://localhost:8080/api/tags?page=1&size=20&sort=id&sort_mode=asc\"}}}";


            mockMvc.perform(
                            get(BASE_URL + "?page=1&size=20&sort=id&sort_mode=asc")
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(expected));
        }
    }

    @Nested
    class UpdateTagTest {
        @Test
        void updateTagShouldReturnUnsupportedOperation() throws Exception {
            var output = "{\"code\":666,\"message\":\"Request method 'PATCH' is not supported\"}";
            when(tagService.update(any(Tag.class)))
                    .thenThrow(new UnsupportedOperationException("Update operation is not allowed for tag"));


            mockMvc.perform(
                            patch(BASE_URL)
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(output));
        }
    }
}