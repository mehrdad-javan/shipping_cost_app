package se.lexicon.shipping_cost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.lexicon.shipping_cost.entity.Box;
import se.lexicon.shipping_cost.repository.BoxRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//@Controller
//@RequestMapping("/shipping")
public class ShippingControllerOld {

    BoxRepository boxRepository;

    //@Autowired
    public void setBoxRepository(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    @GetMapping("/")
    public String getAll(Model model) {
        List<Box> boxes = new ArrayList<>();
        Iterator<Box> iterator = boxRepository.findAll().iterator();
        iterator.forEachRemaining(boxes::add);

        model.addAttribute("boxes", boxes);
        return "boxList";
    }

    @GetMapping("/addForm")
    public String addBoxForm(Model model) {
        Box box = new Box();
        model.addAttribute("box", box);
        return "boxForm";
    }


    @PostMapping("/add")
    public String addBox(@ModelAttribute("box") @Valid Box box, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        System.out.println("box.toString() = " + box.toString());
        if (box.getName().toLowerCase().contains("box")) {
            FieldError error = new FieldError("box", "name", "Name should not be contain box word ");
            bindingResult.addError(error);
        }

        if (bindingResult.hasErrors()) {
            return "boxForm";
        }

        double shippingCost =box.calcShippingCost();
        box.setCost(shippingCost);

        Box savedBox = boxRepository.save(box);
        redirectAttributes.addFlashAttribute("message", "Operation is Done. Tracking Code:" + savedBox.getId());
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/shipping/";
    }


}
