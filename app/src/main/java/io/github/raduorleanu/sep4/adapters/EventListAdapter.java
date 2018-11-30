package io.github.raduorleanu.sep4.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.raduorleanu.sep4.R;
import io.github.raduorleanu.sep4.models.Event;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {

    private final LayoutInflater mInflater;
    private List<Event> data;

    public EventListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.data = new ArrayList<>();
//        this.data.add(new Event(new User("Momo"), "karaoke"));
//        this.data.add(new Event(new User("Mina"), "cooking"));
    }

    public void setData(List<Event> events) {
        data = events;
        notifyDataSetChanged();
    }

    public void removeData(Event event) {
        for(int i = 0; i < data.size(); i++) {
            if(data.get(i).get_id().equals(event.get_id())) {
                data.remove(i);
                notifyItemChanged(i);
            }
        }
    }

    public void addData(Event event) {
        Log.w("Adapter", "adding to " + data.size() + event.toString());
        data.add(event);
        notifyItemInserted(data.size() - 1);
    }

    @NonNull
    @Override
    public EventListAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.event_recycle_item, viewGroup, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventListAdapter.EventViewHolder eventViewHolder, int i) {
        if (data != null) {
            final Event event = data.get(i);
            eventViewHolder.eventDescription.setText(event.getDescription());
            eventViewHolder.eventAddress.setText(event.getLocation());
            eventViewHolder.eventUserName.setText(event.getHost().getName());
            eventViewHolder.eventItem.setOnClickListener(new ItemClicked(event, eventViewHolder));
            eventViewHolder.viewCommentsButton.setOnClickListener(new CommentsButtonClicked(event, eventViewHolder));
        } else {
            eventViewHolder.eventDescription.setText("No event");
        }
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    class ItemClicked implements View.OnClickListener {

        // the event that was clicked containing all the info
        private Event clickedEvent;

        // the view that was clicked, you can add components by id in the EventViewHolder class
        // and change how they behave or look
        private EventListAdapter.EventViewHolder eventViewHolder;

        public ItemClicked(Event event, EventListAdapter.EventViewHolder viewHolder) {
            clickedEvent = event;
            eventViewHolder = viewHolder;
        }

        @Override
        public void onClick(View view) {
            Log.w("event_description -> ", clickedEvent.getDescription());
            Log.w("view_event_username -> ", eventViewHolder.eventUserName.getText().toString());
            eventViewHolder.eventItem.setBackgroundColor(Color.GREEN);
        }
    }

    class CommentsButtonClicked implements View.OnClickListener {

        // the event that was clicked containing all the info
        private Event clickedEvent;

        // the view that was clicked, you can add components by id in the EventViewHolder class
        // and change how they behave or look
        private EventListAdapter.EventViewHolder eventViewHolder;

        public CommentsButtonClicked(Event event, EventListAdapter.EventViewHolder viewHolder) {
            clickedEvent = event;
            eventViewHolder = viewHolder;
        }

        @Override
        public void onClick(View view) {
            Log.w("event_description -> ", clickedEvent.getDescription());
            Log.w("view_event_username -> ", eventViewHolder.eventUserName.getText().toString());
            eventViewHolder.eventItem.setBackgroundColor(Color.GREEN);
        }
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        private final TextView eventDescription;
        private final TextView eventAddress;
        private final TextView eventUserName;
        private final ConstraintLayout eventItem;
        private final Button viewCommentsButton;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventDescription = itemView.findViewById(R.id.event_description);
            eventAddress = itemView.findViewById(R.id.event_address);
            eventUserName = itemView.findViewById(R.id.event_user_name);
            eventItem = itemView.findViewById(R.id.event_item_view);
            viewCommentsButton = itemView.findViewById(R.id.view_comments_button);
        }
    }
}
