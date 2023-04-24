package ru.clevertec.ecl.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.dao.DAOInterface;
import ru.clevertec.ecl.dao.GiftCertificateToTagDAO;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.ItemNotFoundException;
import ru.clevertec.ecl.exception.RequestParamsNotValidException;
import ru.clevertec.ecl.exception.ServiceException;
import ru.clevertec.ecl.objectmother.TagObjectMother;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @InjectMocks
    TagService service;

    @Mock
    DAOInterface<Tag> tagDao;

    @Mock
    GiftCertificateToTagDAO giftCertificateToTagDao;

    @Test
    void testGetAll() {
        when(tagDao.findAll()).thenReturn(Optional.of(TagObjectMother.getTags()));

        var result = service.getAll();

        verify(tagDao).findAll();
        assertNotNull(result);
    }

    @Test
    void testGetByExistingId() {
        var tag = TagObjectMother.getTag();
        when(tagDao.findById(1)).thenReturn(Optional.of(tag));

        var result = service.getById(1);

        verify(tagDao).findById(tag.getId());
        assertNotNull(result);
    }

    @Test
    void testGetByNonExistingId() {
        when(tagDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrowsExactly(ItemNotFoundException.class,
                () -> service.getById(1));
    }

    @Test
    void testUpdateShouldThrowException() {
        assertThrowsExactly(UnsupportedOperationException.class, ()
                -> service.update(new Tag(1, "name")));
    }

    @Test
    void testDeleteByNonExistingId() {
        var nonExistingId = 1;
        when(tagDao.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrowsExactly(ItemNotFoundException.class,
                () -> service.delete(nonExistingId));
    }

    @Test
    void testDeleteByExistingId() {
        var tag = TagObjectMother.getTag();
        when(tagDao.findById(anyInt())).thenReturn(Optional.of(tag));
        when(giftCertificateToTagDao.deleteByTagId(tag.getId())).thenReturn(1);

        var actual = service.delete(tag.getId());

        assertTrue(actual);
    }

    @Test
    void testCreateSuccess() {
        var tag = TagObjectMother.getTag();
        when(tagDao.create(any(Tag.class))).thenReturn(Optional.of(tag));

        var actual = service.create(tag);

        verify(tagDao).create(tag);
        assertNotNull(actual);
    }

    @Test
    void testCreateShouldThrowException() {
        var tag = Tag.builder().build();
        when(tagDao.create(any(Tag.class))).thenReturn(Optional.empty());

        assertThrowsExactly(ServiceException.class,
                () -> service.create(tag));
    }

    @Test
    void testSaveExistingTagShouldThrowException() {
        var tag = Tag.builder().id(4).build();

        assertThrowsExactly(UnsupportedOperationException.class, ()
                -> service.save(tag));
    }

    @Test
    void testSaveNonExistingTag() {
        var tag = Tag.builder().name("name").build();
        when(tagDao.create(any(Tag.class))).thenReturn(Optional.of(tag));

        var actual = service.save(tag);

        verify(tagDao).create(tag);
        assertNotNull(actual);
    }

    @Test
    void testGetByShouldReturnTag() {
        var params = TagObjectMother.getSearchParams();
        var tag = TagObjectMother.getTag();
        when(tagDao.findByName(tag.getName())).thenReturn(Optional.of(tag));

        var actual = service.getBy(params);

        assertNotNull(actual);
        assertEquals(1, actual.size());
    }

    @Test
    void testGetByShouldReturnThrowException() {
        var params = TagObjectMother.getSearchParams();
        var tag = TagObjectMother.getTag();
        when(tagDao.findByName(tag.getName())).thenReturn(Optional.empty());

        assertThrowsExactly(ItemNotFoundException.class,
                () -> service.getBy(params));
    }

    @Test
    void testGetByShouldReturnThrowAnException() {
        var params = Map.of("not name", "not name");

        assertThrowsExactly(RequestParamsNotValidException.class,
                () -> service.getBy(params));
    }
}
