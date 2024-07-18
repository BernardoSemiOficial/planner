package com.nlw.planner.links;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<Link, UUID> {

    public List<Link> findAllByTripId(UUID id);
}
