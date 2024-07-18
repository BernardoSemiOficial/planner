package com.nlw.planner.activity;

import com.nlw.planner.trip.Trip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity()
@Table(name = "activities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(name = "is_finished", nullable = false)
    private Boolean isFinished;

    @Column(name = "occurs_at", nullable = false)
    private LocalDateTime occursAt;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    public Activity(ActivityCreatePaylod activity, Trip trip) {
        this.title = activity.title();
        this.occursAt = LocalDateTime.parse(activity.occursAt(), DateTimeFormatter.ISO_DATE_TIME);
        this.isFinished = false;
        this.trip = trip;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", is_finished=" + isFinished +
                ", occurs_at=" + occursAt +
                ", trip=" + trip +
                '}';
    }
}
