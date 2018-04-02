package services;


import database.entity.Place;
import database.entity.PlaceType;
import database.entity.form.PlaceForm;
import database.repository.PlaceRepository;
import database.repository.PlaceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@org.springframework.stereotype.Service
public class DbService implements Service{

    private PlaceRepository placeRepository;

    private PlaceTypeRepository placeTypeRepository;

    @Autowired
    public DbService(PlaceRepository placeRepository, PlaceTypeRepository placeTypeRepository) {
        this.placeRepository = placeRepository;
        this.placeTypeRepository = placeTypeRepository;
    }

    public void insertPlace(PlaceForm placeForm){
        Place place = new Place();
        place.setLat(placeForm.getLat());
        place.setLongitude(placeForm.getLongitude());
        place.setName(placeForm.getName());
        place.setType(findType(placeForm.getTypeId()));

        placeRepository.save(place);
    }

    public List<Place> findAllPlaces(){
        return (List<Place>) placeRepository.findAll();
    }

    public List<PlaceType> findAllPlaceTypes(){
        return (List<PlaceType>) placeTypeRepository.findAll();
    }

    public PlaceType findType(Integer id) {
        return placeTypeRepository.findOne(id);
    }

}
