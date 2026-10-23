package com.travel.service;

import com.travel.model.Destination;
import com.travel.model.User;
import com.travel.repository.DestinationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class BucketListService {
    public static final List<String> CATEGORIES = List.of("BEACH", "MOUNTAIN", "CITY", "HISTORICAL", "OTHER");
    private final DestinationRepository repo;

    public BucketListService(DestinationRepository repo) { this.repo = repo; }

    /** List with optional search text, category and status (visited/pending) filters. */
    public List<Destination> list(User user, String search, String category, String status) {
        return repo.findByUser(user).stream()
                .filter(d -> search == null || search.isBlank() || (d.getName() + " " + d.getCity() + " " + d.getCountry())
                        .toLowerCase().contains(search.toLowerCase()))
                .filter(d -> category == null || category.isBlank() || d.getCategory().equalsIgnoreCase(category))
                .filter(d -> status == null || status.isBlank()
                        || (status.equals("visited") && d.isVisited()) || (status.equals("pending") && !d.isVisited()))
                .toList();
    }

    public Destination add(User user, Destination d) {
        d.setUser(user);
        d.setVisited(false);
        return repo.save(d);
    }

    public Destination update(User user, Long id, Destination in) {
        Destination d = findOwned(user, id);
        d.setName(in.getName());
        d.setCity(in.getCity());
        d.setCountry(in.getCountry());
        d.setDescription(in.getDescription());
        d.setCategory(in.getCategory());
        d.setImageUrl(in.getImageUrl());
        d.setRating(in.getRating() == null ? null : Math.max(1, Math.min(5, in.getRating())));
        return repo.save(d);
    }

    public void delete(User user, Long id) { repo.delete(findOwned(user, id)); }

    public Destination setVisited(User user, Long id, boolean visited) {
        Destination d = findOwned(user, id);
        d.setVisited(visited);
        return repo.save(d);
    }

    public Map<String, Object> stats(User user) {
        long visited = repo.countByUserAndVisited(user, true);
        long pending = repo.countByUserAndVisited(user, false);
        long total = visited + pending;
        return Map.of("total", total, "visited", visited, "pending", pending,
                "progressPercent", total == 0 ? 0 : Math.round(visited * 100.0 / total));
    }

    // Makes sure a destination exists AND belongs to the logged-in user
    private Destination findOwned(User user, Long id) {
        Destination d = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Destination not found"));
        if (!d.getUser().getId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This is not your destination");
        return d;
    }
}
