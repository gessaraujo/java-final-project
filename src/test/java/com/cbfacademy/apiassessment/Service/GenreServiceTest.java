package com.cbfacademy.apiassessment.Service;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Entity.Genre;
import com.cbfacademy.apiassessment.Model.Repository.GenreRepository;
import com.cbfacademy.apiassessment.Model.Service.GenreService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GenreServiceTest {
    @Mock
    private GenreRepository repository;

    @InjectMocks
    private GenreService service;

    @Test
    public void createTest() throws Exception {
        Genre genre = new Genre();
        genre.setName("Action");

        when(repository.findAll()).thenReturn(Collections.emptyList());

        Assertions.assertDoesNotThrow(() -> service.create(genre));

        Assertions.assertNotNull(genre.getId());
        Mockito.verify(repository).save(genre);
    }

    @Test
    public void createWithExistingNameTest() throws Exception {
        Genre genre = new Genre();
        genre.setName("Action");

        when(repository.findAll()).thenReturn(Collections.singletonList(genre));

        Exception exception = Assertions.assertThrows(Exception.class, () -> service.create(genre));
        Assertions.assertEquals("The name of the genre already exists", exception.getMessage());

        Mockito.verify(repository, never()).save(genre);
    }

    @Test
    public void createWithEmptyNameTest() throws FileNotFoundException, CustomException {
        Genre genre = new Genre();
        genre.setName("");

        Exception exception = Assertions.assertThrows(Exception.class, () -> service.create(genre));
        Assertions.assertEquals("Error creating the genre, the name is empty", exception.getMessage());

        Mockito.verify(repository, never()).save(genre);
    }

    @Test
    public void findAllTest() throws CustomException, FileNotFoundException {
        List<Genre> expectedGenres = Collections.emptyList();
        when(repository.findAll()).thenReturn(expectedGenres);

        List<Genre> actualGenres = service.findAll();

        Assertions.assertEquals(expectedGenres, actualGenres);
    }

    @Test
    public void updateTest() throws Exception {
        Genre genre = new Genre();
        genre.setId(UUID.randomUUID());
        genre.setName("Updated Action");

        when(repository.findAll()).thenReturn(Collections.emptyList());

        Assertions.assertDoesNotThrow(() -> service.update(genre));

        Mockito.verify(repository).update(genre);
    }

    @Test
    public void updateWithExistingNameTest() throws Exception {
        Genre genre = new Genre();
        genre.setId(UUID.randomUUID());
        genre.setName("Action");

        Genre existingGenre = new Genre();
        existingGenre.setId(UUID.randomUUID());
        existingGenre.setName("Comedy");

        when(repository.findAll()).thenReturn(Collections.singletonList(existingGenre));

        Assertions.assertDoesNotThrow(() -> service.update(genre));
        Mockito.verify(repository).update(genre);
    }

    @Test
    public void updateWithEmptyNameTest() throws CustomException {
        Genre genre = new Genre();
        genre.setId(UUID.randomUUID());
        genre.setName("");

        Exception exception = Assertions.assertThrows(Exception.class, () -> service.update(genre));
        Assertions.assertEquals("Error creating the genre, the name is empty", exception.getMessage());

        Mockito.verify(repository, never()).update(genre);
    }

    @Test
    public void deleteTest() throws CustomException {
        String id = UUID.randomUUID().toString();

        Assertions.assertDoesNotThrow(() -> service.delete(id));

        Mockito.verify(repository).delete(UUID.fromString(id));
    }

    @Test
    public void deleteWithEmptyIdTest() throws CustomException {
        String id = "";

        CustomException exception = Assertions.assertThrows(CustomException.class, () -> service.delete(id));
        Assertions.assertEquals("ID is empty", exception.getMessage());

        Mockito.verify(repository, never()).delete(any(UUID.class));
    }
}
