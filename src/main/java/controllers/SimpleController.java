package controllers;

import database.entity.Place;
import database.entity.PlaceType;
import database.entity.form.PlaceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import services.DbService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class SimpleController {

    private final HttpSession session;

    private final DbService databaseService;

    /*@Autowired
    private UserFormValidator userValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }*/

    @Autowired
    public SimpleController(HttpSession session, DbService databaseService) {
        this.session = session;
        this.databaseService = databaseService;
    }

    @RequestMapping("/")
    public String index(Model model){
        return "index";
    }

    @RequestMapping(value = "/add-place",method = RequestMethod.GET)
    public String addPlace(Model model){
        model.addAttribute("place",new PlaceForm());

        List<PlaceType> placeTypes = databaseService.findAllPlaceTypes();

        model.addAttribute("types", placeTypes);

        return "add_place";
    }

    @RequestMapping(value = "/add-place",method = RequestMethod.POST)
    public String enter(@ModelAttribute("place") @Valid PlaceForm place,
                        BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()) {
            printErrors(bindingResult);

            return "add_place";
        } else {

            databaseService.insertPlace(place);

        }
        return "redirect:/";
    }

    @RequestMapping(value = "/show-places",method = RequestMethod.GET)
    public String registration(Model model) {

        List<Place> places = databaseService.findAllPlaces();

        model.addAttribute("places",places);

        return "show_place";
    }




    private void printErrors(BindingResult bindingResult){
        for(ObjectError error:bindingResult.getAllErrors()) {
            System.out.println("error : " + error.toString());
        }
    }

}
