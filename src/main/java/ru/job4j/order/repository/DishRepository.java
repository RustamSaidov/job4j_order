package ru.job4j.order.repository;

import jakarta.annotation.PreDestroy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.job4j.order.model.Dish;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Data
public class DishRepository {

    @Value("${api-url}")
    private String url;
    private final RestTemplate client;

    public void init() {
    }

    public Dish save(Dish dish) {
        System.out.println(url);
        return client.postForEntity(
                url, dish, Dish.class
        ).getBody();
    }


    public boolean deleteById(int id) {
        return client.exchange(
                String.format("%s?id=%s", url, id),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class
        ).getStatusCode() != HttpStatus.NOT_FOUND;
    }

    public Optional<Dish> findById(int id) {
        return Optional.ofNullable(client.getForEntity(
                String.format("%s/getById?id=%s", url, id),
                Dish.class
        ).getBody());
    }

    public List<Dish> findByName(String name) {
        return getList(String.format(
                "%s/getByName?name=%s", url, name
        ));
    }

    private List<Dish> getList(String url) {
        List<Dish> body = client.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Dish>>() {
                }
        ).getBody();
        return body == null ? Collections.emptyList() : body;
    }

    @PreDestroy
    public void close() throws Exception {
    }
}
