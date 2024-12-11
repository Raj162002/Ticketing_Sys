package iit.lk.raj.controller;

import iit.lk.raj.model.Event;
import iit.lk.raj.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
This is the Event Controller class where the API callings for event is defined.
It calls EventService to perform the operations.
*/
@RestController()
@RequestMapping(value = "/event")
@CrossOrigin(origins = "*")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    //This is used to create event
    //It takes the event object as the input (JSON format) and returns the created event object
    @PostMapping(value = "/addEvent")
    public Event addEvent(@RequestBody Event event){
        return eventService.createEvent(event);
    }

    //A Test API to check whether the API is working correctly
    @GetMapping(value = "/test")
    public String test() {
        return "API works correctly from Event Controller";
    }

    //This is used to see the events created with Postman but can be used to get all the event objects from database
    @GetMapping(value = "/getAllEvents")
    public ResponseEntity<List<Event>> getAllEvents(){
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

}
