package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.Flow;

@RestController
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @GetMapping("/api/v1/categories")
    Flux<Category> list()
    {
        return categoryRepository.findAll();
    }
    @GetMapping("/api/v1/categories/id/{id}")
    Mono<Category> getCategoryById(@PathVariable String id)
    {
        return categoryRepository.findById(id);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/categories")
    Mono<Void> create(@RequestBody Publisher<Category> categoryStream)
    {
        return categoryRepository.saveAll(categoryStream).then();
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/v1/categories/id/{id}")
    Mono<Category> update(@PathVariable String id,@RequestBody Category category)
    {
        category.setId(id);
        return categoryRepository.save(category);
    }
}
