package iit.lk.raj.controller;

import iit.lk.raj.model.Event;
import iit.lk.raj.service.EventService;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "/event")
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

}
