package com.example.wineshop.controllers;

import javax.validation.Valid;

import com.example.wineshop.models.Type;
import com.example.wineshop.models.Wine;
import com.example.wineshop.exceptions.WineNotFoundException;
import com.example.wineshop.repositories.WineRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WineController {
    private final WineRepository repository;

    WineController(WineRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/wine/{id}")
    Wine one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new WineNotFoundException(id));
    }

    @GetMapping("/wines")
    List<Wine> all() {
        return repository.findAll();
    }

    @GetMapping("/wines/best10")
    List<Wine> top10() {
        return repository.findTopRating()
                .stream()
                .collect(Collectors.toList());
        //return repository.findAll();
    }

    @GetMapping("/wines/pricier10")
    List<Wine> pricier10() {
        return repository.findTopPrice()
                .stream()
                .collect(Collectors.toList());
        //return repository.findAll();
    }

    @GetMapping("/wines/ratio10")
    List<Wine> ratio10() {
        return repository.findTopRatio()
                .stream()
                .collect(Collectors.toList());
        //return repository.findAll();
    }

    @GetMapping("/wines/years10")
    List<Wine> years10() {
        return repository.findTopYears()
                .stream()
                .collect(Collectors.toList());
        //return repository.findAll();
    }

    @PostMapping("/wines")
    Wine newType(@RequestBody @Valid Wine newWine, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new WineNotFoundException(newWine.getId());
        }else{
            return repository.save(newWine);
        }
    }

    @PutMapping("/api/wine/{id}")
    Wine replaceEmployee(@RequestBody Wine newWine, @PathVariable Long id) {

        return repository.findById(id)
                .map(wine -> {
                    wine.setName(newWine.getName());
                    //Modificar bodega (To do)
                    wine.setYear(newWine.getYear());
                    wine.setRating(newWine.getRating());
                    wine.setNum_reviews(newWine.getNum_reviews());
                    //Modificar region (To do)
                    wine.setPrice(newWine.getPrice());
                    //Modificar type (To do)
                    wine.setBody(newWine.getBody());
                    wine.setAcidity(newWine.getAcidity());

                    return repository.save(wine);
                })
                .orElseGet(() -> {
                    newWine.setId(id);
                    return repository.save(newWine);
                });
    }

    @DeleteMapping("/api/wine/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
