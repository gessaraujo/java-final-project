package com.cbfacademy.apiassessment.Repository;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Entity.Content.Movie;
import com.cbfacademy.apiassessment.Model.Repository.DataBaseRepository;
import com.cbfacademy.apiassessment.Model.Repository.Entities;
import com.cbfacademy.apiassessment.Model.Repository.MovieRepository;
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
public class MovieRepositoryTest {
    @Mock
    private DataBaseRepository repository;

    @InjectMocks
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void save_ValidMovie_Success() throws CustomException {
        Movie movie = new Movie();
        movie.setId(UUID.randomUUID());

        Entities entities = new Entities();
        entities.setMovies(new ArrayList<>());

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertDoesNotThrow(() -> movieRepository.save(movie));

        Mockito.verify(repository, times(1)).getAll();
        Mockito.verify(repository, times(1)).saveAll(entities);
    }

    @Test
    public void findAll_ReturnsListOfMovies() throws CustomException, FileNotFoundException {
        Entities entities = new Entities();
        entities.setMovies(new ArrayList<>());

        when(repository.getAll()).thenReturn(entities);

        List<Movie> movies = movieRepository.findAll();

        Assertions.assertNotNull(movies);
        Assertions.assertEquals(0, movies.size());
    }

    @Test
    public void findById_ExistingId_ReturnsMovie() throws CustomException, FileNotFoundException {
        UUID id = UUID.randomUUID();

        Movie movie = new Movie();
        movie.setId(id);

        Entities entities = new Entities();
        entities.setMovies(new ArrayList<>());
        entities.getMovies().add(movie);

        when(repository.getAll()).thenReturn(entities);

        Movie foundMovie = Assertions.assertDoesNotThrow(() -> movieRepository.findById(id));

        Assertions.assertNotNull(foundMovie);
        Assertions.assertEquals(id, foundMovie.getId());
    }

    @Test
    public void findById_NonExistingId_ThrowsCustomException() throws CustomException {
        UUID id = UUID.randomUUID();

        Entities entities = new Entities();
        entities.setMovies(new ArrayList<>());

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertThrows(CustomException.class, () -> movieRepository.findById(id));
    }

    @Test
    public void update_ExistingMovie_Success() throws CustomException {
        Movie movie = new Movie();
        movie.setId(UUID.randomUUID());

        Entities entities = new Entities();
        entities.setMovies(new ArrayList<>());
        entities.getMovies().add(movie);

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertDoesNotThrow(() -> movieRepository.update(movie));

        Mockito.verify(repository, times(1)).getAll();
        Mockito.verify(repository, times(1)).saveAll(entities);
    }

    @Test
    public void delete_ExistingMovie_Success() throws CustomException {
        UUID id = UUID.randomUUID();

        Movie movie = new Movie();
        movie.setId(id);

        Entities entities = new Entities();
        entities.setMovies(new ArrayList<>());
        entities.getMovies().add(movie);

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertDoesNotThrow(() -> movieRepository.delete(id));

        Mockito.verify(repository, times(1)).getAll();
        Mockito.verify(repository, times(1)).saveAll(entities);
    }

    @Test
    public void update_NonExistingMovie_ThrowsCustomException() throws CustomException {
        Movie movie = new Movie();
        movie.setId(UUID.randomUUID());

        Entities entities = new Entities();
        entities.setMovies(new ArrayList<>());

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertThrows(CustomException.class, () -> movieRepository.update(movie));
    }

    @Test
    public void delete_NonExistingMovie_ThrowsCustomException() throws CustomException {
        UUID id = UUID.randomUUID();

        Entities entities = new Entities();
        entities.setMovies(new ArrayList<>());

        when(repository.getAll()).thenReturn(entities);

        Assertions.assertThrows(CustomException.class, () -> movieRepository.delete(id));
    }

    @Test
    public void findById_ExceptionInRepository_ThrowsCustomException() throws CustomException {
        when(repository.getAll()).thenThrow(CustomException.class);

        Assertions.assertThrows(CustomException.class, () -> movieRepository.findById(UUID.randomUUID()));
    }
}
