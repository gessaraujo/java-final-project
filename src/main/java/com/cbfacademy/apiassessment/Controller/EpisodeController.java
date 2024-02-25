package com.cbfacademy.apiassessment.Controller;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;
import com.cbfacademy.apiassessment.Model.Entity.Episode;
import com.cbfacademy.apiassessment.Model.Service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "episode")
public class EpisodeController {
    @Autowired
    private EpisodeService service;

    @PostMapping
    public void create(@RequestBody Episode episode) throws CustomException, FileNotFoundException {
        service.create(episode);
    }

    @GetMapping
    public List<Episode> findAll() throws FileNotFoundException, CustomException {
        return service.findAll();
    }

    @PutMapping
    public void update(@RequestBody Episode episode) throws FileNotFoundException, CustomException {
        service.update(episode);
    }

    @DeleteMapping
    public void delete(@RequestBody String id) throws CustomException {
        service.delete(id);
    }
}
