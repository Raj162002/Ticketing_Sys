package iit.lk.raj.service;


import iit.lk.raj.model.Event;
import iit.lk.raj.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }
    public String deleteEvent(Long id){
        try{
            if(!eventRepository.existsById(id)){
                return "Event not found with ID: " + id;
            }
            eventRepository.deleteById(id);
            return "Successfully deleted event with ID: " + id;
        }
        catch (Exception e){
            return e.toString();
        }
    }
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    

}
