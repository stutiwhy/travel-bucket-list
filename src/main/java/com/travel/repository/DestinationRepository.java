package com.travel.repository;

import com.travel.model.Destination;
import com.travel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findByUser(User user);
    long countByUserAndVisited(User user, boolean visited);
}
