package com.dss.controller;

import com.dss.entity.Actor;
import com.dss.entity.Movie;
import com.dss.exception.DuplicateMovieException;
import com.dss.exception.MovieNotFoundException;
import com.dss.model.AddMovieRequest;
import com.dss.model.SearchMovieRequest;
import com.dss.model.UpdateMovieRequest;
import com.dss.service.MovieService;
import com.dss.util.ResponseMsgConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @MockBean
    private MovieService movieService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    ObjectWriter ow;

    private Actor actor;
    private Movie movie;

    private AddMovieRequest addMovieRequest;

    private UpdateMovieRequest updateMovieRequest;

    private SearchMovieRequest searchMovieRequest;
    MovieController movieController;

    @BeforeEach
    void setUp() {
        movieController = new MovieController();
        movieController.setMovieService(movieService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(movieController)
                .setControllerAdvice(DSSExceptionHandler.class)
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ow = objectMapper.writer().withDefaultPrettyPrinter();

        actor = Actor.builder()
                .actorId(1)
                .firstName("TEST")
                .lastName("TEST")
                .age(32).gender("M")
                .movies(new ArrayList<>()).build();
        addMovieRequest = AddMovieRequest.builder().movieId(1).cost(250).image("test.png")
                .title("MOVIE TITLE TEST")
                .yearOfRelease(2000).build();
        movie = Movie.builder()
                .movieId(1).cost(250).image("test.png")
                .title("MOVIE TITLE TEST")
                .yearOfRelease(2000)
                .actors(new ArrayList<>()).build();
        updateMovieRequest = UpdateMovieRequest.builder()
                .movieId(1).cost(200).image("updatedTest,png")
                .build();

        searchMovieRequest = SearchMovieRequest.builder()
                .movieId(1).title("TEST MOVIE").build();
    }

    @Test
    void getAllMovies() throws Exception {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);
        Mockito.when(movieService.getAllMovie()).thenReturn(movieList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/dss2/api/movie/getAll"))
                .andExpect(MockMvcResultMatchers.status().isFound());
    }

    @Test
    @DisplayName("Successfully added a movie.")
    void addMovie() throws Exception {
        String requestJson = ow.writeValueAsString(addMovieRequest);
        Mockito.when(movieService.addMovie(any(AddMovieRequest.class))).thenReturn(ResponseMsgConstant.SUCCESSFULLY_ADDED_MOVIE_MSG.concat(" (id: "
                + movie.getMovieId() + "\t Title:" + movie.getTitle() + ")"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/dss2/api/movie/add")
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Successfully validated the movie id for duplication.")
    void addMovieThrowDuplicateMovieException() throws Exception {
        String requestJson = ow.writeValueAsString(addMovieRequest);
        Mockito.when(movieService.addMovie(any(AddMovieRequest.class))).thenThrow(new DuplicateMovieException(ResponseMsgConstant.FAILED_TO_ADD_MOVIE_MSG));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/dss2/api/movie/add")
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("Successfully updated the movie.")
    void updateMovie() throws Exception {
        String requestJson = ow.writeValueAsString(updateMovieRequest);
        Mockito.when(movieService.updateMovie(any(UpdateMovieRequest.class))).thenReturn(anyString());
        this.mockMvc.perform(MockMvcRequestBuilders.put("/dss2/api/movie/update")
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Check if the movie is found")
    void updateMovieThrowMovieNotFoundException() throws Exception {
        String requestJson = ow.writeValueAsString(updateMovieRequest);
        Mockito.when(movieService.updateMovie(any(UpdateMovieRequest.class))).thenThrow(new MovieNotFoundException(ResponseMsgConstant.FAILED_TO_UPDATE_MOVIE_MSG));
        this.mockMvc.perform(MockMvcRequestBuilders.put("/dss2/api/movie/update")
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Successfully deleted the movie.")
    void deleteMovie() throws Exception {
        Mockito.when(movieService.deleteMovie(anyLong())).thenReturn(anyString());
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/dss2/api/movie/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Successfully added actor details to the movie.")
    void updateMovieAddActor() throws Exception {
        Mockito.when(movieService.addActorDetailsToTheMovie(anyLong(), anyLong())).thenReturn(ResponseMsgConstant.SUCCESSFULLY_ADDED_ACTOR_DETAILS_TO_MOVIE_MSG);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/dss2/api/movie//update/1/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void searchMovie() throws Exception {
        String requestJson = ow.writeValueAsString(searchMovieRequest);
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);
        Mockito.when(movieService.searchMovie(any(SearchMovieRequest.class))).thenReturn(movieList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/dss2/api/movie/search")
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isFound());
    }
}
