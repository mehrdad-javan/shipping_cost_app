package se.lexicon.shipping_cost.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.shipping_cost.entity.Box;

import java.util.List;

public interface BoxRepository extends CrudRepository<Box, String> {
    List<Box> findByNameIgnoreCase(String name);

    List<Box> findByCountryIgnoreCase(String country);

}
