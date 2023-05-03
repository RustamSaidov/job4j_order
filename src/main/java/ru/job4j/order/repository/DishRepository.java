package ru.job4j.order.repository;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.job4j.order.model.Dish;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Data
public class DishRepository {

    @Value("http://localhost:8080/dish")
    private String url;
    private final RestTemplate client;

    public Optional<Dish> findById(int id) {
        return Optional.ofNullable(client.getForEntity(
                String.format("%s/%s", url, id),
                Dish.class
        ).getBody());
    }

    private List<Dish> getList(String url) {
        List<Dish> body = client.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Dish>>() {
                }
        ).getBody();
        return body == null ? Collections.emptyList() : body;
    }
}
