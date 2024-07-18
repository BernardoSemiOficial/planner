package com.nlw.planner.activity;

import com.nlw.planner.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public ActivityCreateResponse createActivity(ActivityCreatePaylod payload, Trip trip) {
        Activity activity = new Activity(payload, trip);
        this.activityRepository.save(activity);
        return new ActivityCreateResponse(activity.getId(), activity.getTitle(), activity.getIsFinished(), activity.getOccursAt());
    }

    public List<ActivityBase> getAllActivityByTripId(UUID tripId) {
        List<Activity> activities = this.activityRepository.findAllByTripId(tripId);
        return activities.stream().map(activity -> new ActivityBase(activity.getId(), activity.getTitle(), activity.getIsFinished(), activity.getOccursAt())).toList();
    }
}
