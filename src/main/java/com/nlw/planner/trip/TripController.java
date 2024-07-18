package com.nlw.planner.trip;

import com.nlw.planner.activity.ActivityBase;
import com.nlw.planner.activity.ActivityCreatePaylod;
import com.nlw.planner.activity.ActivityCreateResponse;
import com.nlw.planner.activity.ActivityService;
import com.nlw.planner.link.LinkBase;
import com.nlw.planner.link.LinkCreatePayload;
import com.nlw.planner.link.LinkService;
import com.nlw.planner.participant.ParticipantBase;
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
    private ActivityService activityService;

    @Autowired
    private LinkService linkService;

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
        List<ParticipantBase> participantsDB = this.participantService.registerParticipantsToTrip(payload.emails_to_invite(), newTrip);

        return ResponseEntity.ok(new TripCreateResponse(newTrip.getId(), participantsDB));
    }

    @PutMapping("/{tripId}")
    public ResponseEntity<Trip> editTrip(@PathVariable UUID tripId, @RequestBody TripEditPayload payload) {
        Optional<Trip> tripDB = this.tripRepository.findById(tripId);
        Trip tripUpdate = new Trip(payload);
        if(tripDB.isPresent()) {
            Trip trip = tripDB.get();
            trip.setDestination(tripUpdate.getDestination());
            trip.setStartsAt(tripUpdate.getStartsAt());
            trip.setEndsAt(tripUpdate.getEndsAt());
            this.tripRepository.save(trip);
            return ResponseEntity.ok(trip);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{tripId}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID tripId) {
        Optional<Trip> trip = this.tripRepository.findById(tripId);
        if(trip.isPresent()) {
            Trip tripDB = trip.get();
            tripDB.setIsConfirmed(true);
            this.tripRepository.save(tripDB);
            this.participantService.triggerConfirmationEmailToParticipants(tripDB.getId());
            return ResponseEntity.ok(tripDB);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{tripId}/invites")
    public ResponseEntity<Object> inviteParticipants(@PathVariable UUID tripId, @RequestBody TripInviteParticipantsPayload payload) {
        Optional<Trip> trip = this.tripRepository.findById(tripId);
        if(trip.isPresent()) {
            List<ParticipantBase> participantsDB = this.participantService.registerParticipantsToTrip(payload.emails_to_invite(), trip.get());
            if(trip.get().getIsConfirmed()) {
                this.participantService.triggerConfirmationEmailToParticipants(participantsDB);
            }
            return ResponseEntity.ok(participantsDB);
        }
        return ResponseEntity.status(400).body("Trip not found");
    }

    @GetMapping("/{tripId}/participants")
    public ResponseEntity<List<ParticipantBase>> getAllParticipantsByTripId(@PathVariable UUID tripId) {
        List<ParticipantBase> participants = this.participantService.getAllParticipants(tripId);
        return ResponseEntity.ok(participants);
    }

    @PostMapping("/{tripId}/activities")
    public ResponseEntity<ActivityCreateResponse> createActivity(@PathVariable UUID tripId, @RequestBody ActivityCreatePaylod payload) {
        Optional<Trip> trip = this.tripRepository.findById(tripId);
        if(trip.isPresent()) {
            ActivityCreateResponse activity = this.activityService.createActivity(payload, trip.get());
            return ResponseEntity.ok(activity);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{tripId}/activities")
    public ResponseEntity<List<ActivityBase>> getAllActivitiesByTripId(@PathVariable UUID tripId) {
        List<ActivityBase> activities = this.activityService.getAllActivityByTripId(tripId);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/{tripId}/links")
    public ResponseEntity<List<LinkBase>> getAllLinksByTripId(@PathVariable UUID tripId) {
        List<LinkBase> links = this.linkService.getAllLinksByTripId(tripId);
        return ResponseEntity.ok(links);
    }

    @PostMapping("/{tripId}/links")
    public ResponseEntity<LinkBase> createLink(@PathVariable UUID tripId, @RequestBody LinkCreatePayload payload) {
       Optional<Trip> trip = this.tripRepository.findById(tripId);
       if(trip.isPresent()) {
          LinkBase link = this.linkService.createLink(payload, trip.get());
          return ResponseEntity.ok(link);
       }
       return ResponseEntity.notFound().build();
    }
}
