package com.nlw.planner.trip;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Table(name = "trips")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String destination;

    @Column(name = "starts_at", nullable = false)
    private LocalDateTime startsAt;

    @Column(name = "ends_at", nullable = false)
    private LocalDateTime endsAt;

    @Column(name = "is_confirmed", nullable = false)
    private Boolean isConfirmed;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(name = "owner_email", nullable = false)
    private String ownerEmail;

    public Trip(TripCreatePayload trip) {
        this.destination = trip.destination();
        this.ownerName = trip.owner_name();
        this.ownerEmail = trip.owner_email();
        this.startsAt = LocalDateTime.parse(trip.starts_at(), DateTimeFormatter.ISO_DATE_TIME);
        this.endsAt = LocalDateTime.parse(trip.ends_at(), DateTimeFormatter.ISO_DATE_TIME);
        this.isConfirmed = false;
    }

    public Trip(TripEditPayload trip) {
        this.destination = trip.destination();
        this.startsAt = LocalDateTime.parse(trip.starts_at(), DateTimeFormatter.ISO_DATE_TIME);
        this.endsAt = LocalDateTime.parse(trip.ends_at(), DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", startsAt=" + startsAt +
                ", endsAt=" + endsAt +
                ", isConfirmed=" + isConfirmed +
                ", ownerName='" + ownerName + '\'' +
                ", ownerEmail='" + ownerEmail + '\'' +
                '}';
    }
}
