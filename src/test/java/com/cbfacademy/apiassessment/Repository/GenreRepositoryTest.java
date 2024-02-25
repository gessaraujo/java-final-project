package com.cbfacademy.apiassessment.Repository;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Entity.Genre;
import com.cbfacademy.apiassessment.Model.Repository.DataBaseRepository;
import com.cbfacademy.apiassessment.Model.Repository.Entities;
import com.cbfacademy.apiassessment.Model.Repository.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GenreRepositoryTest {
    @Mock
    private DataBaseRepository repository;

    @InjectMocks
    private GenreRepository genreRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void save_ValidGenre_Success() throws CustomException {
        Genre genre = new Genre();
        genre.setId(UUID.randomUUID());

        Entities entities = new Entities();
        entities.setGenres(new ArrayList<>());

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertDoesNotThrow(() -> genreRepository.save(genre));

        Mockito.verify(repository, times(1)).getAll();
        Mockito.verify(repository, times(1)).saveAll(entities);
    }

    @Test
    public void findAll_ReturnsListOfGenres() throws CustomException, FileNotFoundException {
        Entities entities = new Entities();
        entities.setGenres(new ArrayList<>());

        when(repository.getAll()).thenReturn(entities);

        List<Genre> genres = genreRepository.findAll();

        Assertions.assertNotNull(genres);
        Assertions.assertEquals(0, genres.size());
    }

    @Test
    public void findById_ExistingId_ReturnsGenre() throws CustomException {
        UUID id = UUID.randomUUID();

        Genre genre = new Genre();
        genre.setId(id);

        Entities entities = new Entities();
        entities.setGenres(new ArrayList<>());
        entities.getGenres().add(genre);

        when(repository.getAll()).thenReturn(entities);

        Genre foundGenre = Assertions.assertDoesNotThrow(() -> genreRepository.findById(id));

        Assertions.assertNotNull(foundGenre);
        Assertions.assertEquals(id, foundGenre.getId());
    }

    @Test
    public void findById_NonExistingId_ReturnsNull() throws CustomException {
        UUID id = UUID.randomUUID();

        Entities entities = new Entities();
        entities.setGenres(new ArrayList<>());

        when(repository.getAll()).thenReturn(entities);

        Genre foundGenre = Assertions.assertDoesNotThrow(() -> genreRepository.findById(id));

        Assertions.assertNull(foundGenre);
    }

    @Test
    public void update_ExistingGenre_Success() throws CustomException {
        Genre genre = new Genre();
        genre.setId(UUID.randomUUID());

        Entities entities = new Entities();
        entities.setGenres(new ArrayList<>());
        entities.getGenres().add(genre);

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertDoesNotThrow(() -> genreRepository.update(genre));

        Mockito.verify(repository, times(1)).getAll();
        Mockito.verify(repository, times(1)).saveAll(entities);
    }

    @Test
    public void delete_ExistingGenre_Success() throws CustomException {
        UUID id = UUID.randomUUID();

        Genre genre = new Genre();
        genre.setId(id);

        Entities entities = new Entities();
        entities.setGenres(new ArrayList<>());
        entities.getGenres().add(genre);

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertDoesNotThrow(() -> genreRepository.delete(id));

        Mockito.verify(repository, times(1)).getAll();
        Mockito.verify(repository, times(1)).saveAll(entities);
    }

    @Test
    public void update_NonExistingGenre_ThrowsCustomException() throws CustomException {
        Genre genre = new Genre();
        genre.setId(UUID.randomUUID());

        Entities entities = new Entities();
        entities.setGenres(new ArrayList<>());

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertThrows(CustomException.class, () -> genreRepository.update(genre));
    }

    @Test
    public void delete_NonExistingGenre_ThrowsCustomException() throws CustomException {
        UUID id = UUID.randomUUID();

        Entities entities = new Entities();
        entities.setGenres(new ArrayList<>());

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertThrows(CustomException.class, () -> genreRepository.delete(id));
    }

    @Test
    public void findById_ExceptionInRepository_ThrowsCustomException() throws CustomException {
        when(repository.getAll()).thenThrow(CustomException.class);

        Assertions.assertThrows(CustomException.class, () -> genreRepository.findById(UUID.randomUUID()));
    }
}
