package se.lexicon.shipping_cost.service;

import se.lexicon.shipping_cost.entity.Box;
import se.lexicon.shipping_cost.exception.RecordDuplicateException;
import se.lexicon.shipping_cost.exception.RecordNotFountException;

import java.util.List;
import java.util.Optional;

public interface BoxService {
    List<Box> getAll();

    Box findById(String id) throws RecordNotFountException;

    List<Box> findByName(String name) throws RecordNotFountException;

    List<Box> findByCountry(String country) throws RecordNotFountException;

    List<Box> findByNameAndCountry(String name, String country) throws RecordNotFountException;

    Box save(Box box) throws RecordDuplicateException;

}
