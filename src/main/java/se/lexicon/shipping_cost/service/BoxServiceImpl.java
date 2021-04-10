package se.lexicon.shipping_cost.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import se.lexicon.shipping_cost.entity.Box;
import se.lexicon.shipping_cost.exception.ArgumentException;
import se.lexicon.shipping_cost.exception.RecordDuplicateException;
import se.lexicon.shipping_cost.exception.RecordNotFountException;
import se.lexicon.shipping_cost.repository.BoxRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class BoxServiceImpl implements BoxService {

    BoxRepository boxRepository;

    @Autowired
    public void setBoxRepository(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    @Override
    public List<Box> getAll() {
        List<Box> boxes = new ArrayList<>();
        Iterator<Box> iterator = boxRepository.findAll().iterator();
        iterator.forEachRemaining(boxes::add);
        return boxes;
    }

    @Override
    public Box findById(String id) throws RecordNotFountException {
        if (id == null) throw new ArgumentException("id is not valid");
        Optional<Box> optionalBox = boxRepository.findById(id);
        if (optionalBox.isPresent()) return optionalBox.get();
        else throw new RecordNotFountException("Data not found");
    }

    @Override
    public List<Box> findByName(String name) throws RecordNotFountException {
        if (name == null || name.length() == 0) throw new ArgumentException("name is not valid");
        List<Box> boxes = boxRepository.findByNameIgnoreCase(name);
        if (boxes.size() != 0) return boxes;
        else throw new RecordNotFountException("Data not found");
    }

    @Override
    public Box save(Box box) throws RecordDuplicateException {
        if (box == null) throw new ArgumentException("box object is not valid");
        if (box.getId() != null) throw new ArgumentException("id should be null");
        if (box.getName() == null || box.getName().trim().length() == 0)
            throw new ArgumentException("name should not be null");

        try {
            return boxRepository.save(box);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new RecordDuplicateException("duplicate exception");
        }
    }
}
