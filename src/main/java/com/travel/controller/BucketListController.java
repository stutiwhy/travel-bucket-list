package com.travel.controller;

import com.travel.model.Destination;
import com.travel.service.AuthService;
import com.travel.service.BucketListService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BucketListController {
    private final BucketListService service;
    private final AuthService auth;

    public BucketListController(BucketListService service, AuthService auth) {
        this.service = service;
        this.auth = auth;
    }

    // ---- Bucket list (CRUD) ----
    @GetMapping("/bucket-list")
    public List<Destination> all(@RequestHeader(value = "Authorization", required = false) String h,
                                 @RequestParam(required = false) String search,
                                 @RequestParam(required = false) String category,
                                 @RequestParam(required = false) String status) {
        return service.list(auth.getUser(h), search, category, status);
    }

    @GetMapping("/bucket-list/visited")
    public List<Destination> visited(@RequestHeader(value = "Authorization", required = false) String h) {
        return service.list(auth.getUser(h), null, null, "visited");
    }

    @GetMapping("/bucket-list/pending")
    public List<Destination> pending(@RequestHeader(value = "Authorization", required = false) String h) {
        return service.list(auth.getUser(h), null, null, "pending");
    }

    @PostMapping("/bucket-list")
    public Destination add(@RequestHeader(value = "Authorization", required = false) String h,
                           @Valid @RequestBody Destination d) {
        return service.add(auth.getUser(h), d);
    }

    @PutMapping("/bucket-list/{id}")
    public Destination update(@RequestHeader(value = "Authorization", required = false) String h,
                              @PathVariable Long id, @Valid @RequestBody Destination d) {
        return service.update(auth.getUser(h), id, d);
    }

    @DeleteMapping("/bucket-list/{id}")
    public Map<String, String> delete(@RequestHeader(value = "Authorization", required = false) String h,
                                      @PathVariable Long id) {
        service.delete(auth.getUser(h), id);
        return Map.of("message", "Deleted");
    }

    // ---- Visit status ----
    @PutMapping("/bucket-list/{id}/visited")
    public Destination markVisited(@RequestHeader(value = "Authorization", required = false) String h,
                                   @PathVariable Long id) {
        return service.setVisited(auth.getUser(h), id, true);
    }

    @PutMapping("/bucket-list/{id}/pending")
    public Destination markPending(@RequestHeader(value = "Authorization", required = false) String h,
                                   @PathVariable Long id) {
        return service.setVisited(auth.getUser(h), id, false);
    }

    // ---- Categories & statistics ----
    @GetMapping("/categories")
    public List<String> categories() { return BucketListService.CATEGORIES; }

    @GetMapping("/stats")
    public Map<String, Object> stats(@RequestHeader(value = "Authorization", required = false) String h) {
        return service.stats(auth.getUser(h));
    }
}
