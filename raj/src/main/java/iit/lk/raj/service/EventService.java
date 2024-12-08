package iit.lk.raj.service;


import iit.lk.raj.model.Event;
import iit.lk.raj.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public Event getEventById(Long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isPresent()) {
            return event.get();
        } else {
            throw new NoSuchElementException("Event not found with ID: " + eventId);
        }
    }

}
