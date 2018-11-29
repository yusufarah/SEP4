package io.github.raduorleanu.sep4.util;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.github.raduorleanu.sep4.models.Event;
import io.github.raduorleanu.sep4.models.User;

public class AddDataToFireBase {

    private FirebaseDatabase database = FirebaseProvider.getDb();
    private int n;
    private int c;
    private FakeUser f;

    public AddDataToFireBase(int numberOfEvents, int ceilNumberOfUsersAndCommentsPerEvent, Context context) {
        n = numberOfEvents;
        c = ceilNumberOfUsersAndCommentsPerEvent;
        f = new FakeUser(context);
        addData();
    }

    private void addData() {
        Map<String, Event> eventMap = new HashMap<>();

        List<Event> events = f.getEvents(n);

        for(Event e : events) {
            eventMap.put(e.get_id(), e);
        }
        database.getReference("events").setValue(eventMap);
        Log.w("db-write", "maybe");

        addRandomComments(events);
        addRandomAttendees(events);
    }

    private void addRandomComments(List<Event> events) {
        Map<String, List<String>> m = new HashMap<>();
        Random r = new Random();
        for(Event e: events) {
            m.put(e.get_id(), f.getSomeComments(r.nextInt(c)));
        }
        database.getReference("comments").setValue(m);
    }

    private void addRandomAttendees(List<Event> events) {
        Map<String, List<User>> m = new HashMap<>();
        Random r = new Random();
        for(Event e: events) {
            m.put(e.get_id(), f.getUsers(r.nextInt(c)));
        }
        database.getReference("attendees").setValue(m);
    }

}
