package se.lexicon.shipping_cost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.lexicon.shipping_cost.entity.Box;
import se.lexicon.shipping_cost.exception.RecordDuplicateException;
import se.lexicon.shipping_cost.exception.RecordNotFountException;
import se.lexicon.shipping_cost.repository.BoxRepository;
import se.lexicon.shipping_cost.service.BoxService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/shipping")
public class ShippingController {

    BoxService boxService;

    @Autowired
    public void setBoxService(BoxService boxService) {
        this.boxService = boxService;
    }


    @GetMapping("/")
    public String getAll(Model model) {
        List<Box> boxes = boxService.getAll();
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
        if (box.getName().toLowerCase().contains("box")) {
            FieldError error = new FieldError("box", "name", "Name should not be contain box word ");
            bindingResult.addError(error);
        }

        if (bindingResult.hasErrors()) {
            return "boxForm";
        }


        try {
            double shippingCost = box.calcShippingCost();
            box.setCost(shippingCost);
            Box savedBox = boxService.save(box);
            redirectAttributes.addFlashAttribute("message", "Operation is Done. Tracking Code:" + savedBox.getId());
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            return "redirect:/shipping/";

        } catch (RecordDuplicateException e) {
            FieldError error = new FieldError("box", "name", "Name should not be duplicate");
            bindingResult.addError(error);
            return "boxForm";
        }
    }


    @GetMapping("/search")
    public String searchForm(Model model) {
        model.addAttribute("boxes", boxService.getAll());
        return "searchBox";
    }


    @PostMapping("/search")
    public String search(@RequestParam(name = "name", required = false, defaultValue = "ALL") String name,
                         @RequestParam(name = "country", required = false, defaultValue = "ALL") String country,
                         Model model, RedirectAttributes redirectAttributes) {
        List<Box> boxes = new ArrayList<>();
        System.out.println("name = " + name);
        System.out.println("country = " + country);
        try {

            if (name.equalsIgnoreCase("ALL") && country.equalsIgnoreCase("ALL")) {
                System.out.println("SEARCH ALL");
                boxes = boxService.getAll();
                model.addAttribute("boxes", boxes);
                return "searchBox";
            }

            if (!name.equalsIgnoreCase("ALL") && country.equalsIgnoreCase("ALL")) {
                System.out.println("SEARCH BY NAME");
                boxes = boxService.findByName(name);
                model.addAttribute("boxes", boxes);
                return "searchBox";
            }
            if (name.equalsIgnoreCase("ALL") && !country.equalsIgnoreCase("ALL")) {
                System.out.println("SEARCH BY COUNTRY");
                boxes = boxService.findByCountry(country);
                model.addAttribute("boxes", boxes);

                return "searchBox";
            }

            if (!name.equalsIgnoreCase("ALL") && !country.equalsIgnoreCase("ALL")) {
                System.out.println("SEARCH BY NAME AND COUNTRY");
                boxes = boxService.findByNameAndCountry(name, country);
                model.addAttribute("boxes", boxes);
                return "searchBox";
            }

        } catch (RecordNotFountException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Data Not Found");
            redirectAttributes.addFlashAttribute("alertClass", "alert-warning");
            model.addAttribute("boxes", boxes);
        }

        return "redirect:/shipping/search";

    }


}
