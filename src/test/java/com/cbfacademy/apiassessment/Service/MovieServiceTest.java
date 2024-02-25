package com.cbfacademy.apiassessment.Service;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Entity.Content.Movie;
import com.cbfacademy.apiassessment.Model.Entity.Genre;
import com.cbfacademy.apiassessment.Model.Repository.GenreRepository;
import com.cbfacademy.apiassessment.Model.Repository.MovieRepository;
import com.cbfacademy.apiassessment.Model.Service.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
public class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void createMovie_ValidMovie_Success() throws FileNotFoundException, CustomException, CustomException, FileNotFoundException {
        Movie movie = new Movie();
        movie.setTitle("Movie Title");
        Genre genre = new Genre();
        genre.setId(UUID.randomUUID());
        movie.setGenre(genre);

        when(genreRepository.findById(movie.getGenre().getId())).thenReturn(genre);
        when(movieRepository.findAll()).thenReturn(Collections.emptyList());

        Assertions.assertDoesNotThrow(() -> movieService.create(movie));

        Mockito.verify(movieRepository, times(1)).save(movie);
    }

    @Test
    public void createMovie_InvalidTitle_ThrowsCustomException() throws FileNotFoundException, CustomException {
        Movie movie = new Movie();

        Assertions.assertThrows(CustomException.class, () -> movieService.create(movie));

        Mockito.verify(movieRepository, never()).save(movie);
    }

    @Test
    public void createMovie_GenreNotFound_ThrowsCustomException() throws CustomException, FileNotFoundException {
        Movie movie = new Movie();
        movie.setTitle("Movie Title");
        Genre genre = new Genre();
        genre.setId(UUID.randomUUID());
        movie.setGenre(genre);

        when(genreRepository.findById(movie.getGenre().getId())).thenReturn(null);

        Assertions.assertThrows(CustomException.class, () -> movieService.create(movie));

        verify(movieRepository, never()).save(movie);
    }

    @Test
    public void findAll_ReturnsListOfMovies() throws FileNotFoundException, CustomException {
        List<Movie> movies = Arrays.asList(new Movie(), new Movie());

        when(movieRepository.findAll()).thenReturn(movies);

        Assertions.assertEquals(movies, movieService.findAll());
    }

    @Test
    public void updateMovie_ValidMovie_Success() throws FileNotFoundException, CustomException {
        Movie movie = new Movie();
        movie.setTitle("Updated Movie");
        Genre genre = new Genre();
        genre.setId(UUID.randomUUID());
        movie.setGenre(genre);

        when(genreRepository.findById(movie.getGenre().getId())).thenReturn(genre);

        Assertions.assertDoesNotThrow(() -> movieService.update(movie));

        verify(movieRepository, times(1)).update(movie);
    }

    @Test
    public void deleteMovie_ValidId_Success() throws CustomException {
        String id = UUID.randomUUID().toString();

        Assertions.assertDoesNotThrow(() -> movieService.delete(id));

        verify(movieRepository, times(1)).delete(UUID.fromString(id));
    }

    @Test
    public void deleteMovie_EmptyId_ThrowsCustomException() throws CustomException {
        String id = "";

        Assertions.assertThrows(CustomException.class, () -> movieService.delete(id));

        verify(movieRepository, never()).delete(any());
    }
}
