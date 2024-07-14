package com.nlw.planner.trip;

import com.nlw.planner.participant.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private TripRepository tripRepository;

    @GetMapping
    public ResponseEntity<List<Trip>> createTrip() {
        List<Trip> tripList = this.tripRepository.findAll();
        return ResponseEntity.ok(tripList);
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<Optional<Trip>> getTripId(@PathVariable UUID tripId) {
        Optional<Trip> trip = this.tripRepository.findById(tripId);
        return trip.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(trip);
    }

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripCreatePayload payload) {
        Trip newTrip = new Trip(payload);

        this.tripRepository.save(newTrip);
        this.participantService.registerParticipantsToTrip(payload.emails_to_invite(), newTrip.getId());

        return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));
    }
}
