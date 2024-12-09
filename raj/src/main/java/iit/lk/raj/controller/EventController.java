package iit.lk.raj.controller;

import iit.lk.raj.model.Event;
import iit.lk.raj.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "/event")
@CrossOrigin(origins = "*")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping(value = "/addEvent")
    public Event addEvent(@RequestBody Event event){
        return eventService.createEvent(event);
    }
    @GetMapping(value = "/test")
    public String test() {
        return "API works correctly from Event Controller";
    }
    @GetMapping(value = "/getAllEvents")
    public ResponseEntity<List<Event>> getAllEvents(){
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

}
