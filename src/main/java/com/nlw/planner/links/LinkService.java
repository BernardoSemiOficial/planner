package com.nlw.planner.links;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    public List<LinkBase> getAllLinksByTripId(UUID tripId) {
        List<Link> links = this.linkRepository.findAllByTripId(tripId);
        return links.stream().map(link -> new LinkBase(link.getId(), link.getTitle(), link.getUrl())).toList();
    }
}
