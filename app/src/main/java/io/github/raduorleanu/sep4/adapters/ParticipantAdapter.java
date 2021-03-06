package io.github.raduorleanu.sep4.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.snapshot.DoubleNode;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.github.raduorleanu.sep4.R;
import io.github.raduorleanu.sep4.databaseHandlers.ParticipantDbHandler;
import io.github.raduorleanu.sep4.interfaces.IListAdapter;
import io.github.raduorleanu.sep4.models.User;

import static android.support.constraint.Constraints.TAG;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ViewHolder> {

    private final LayoutInflater minflater;
    private List<User> data;

    public ParticipantAdapter(Context context) {
        minflater = LayoutInflater.from(context);
        this.data = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = minflater.inflate(R.layout.participant_recycle_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.e(TAG, "onBindViewHolder: called");
        if (data != null) {
            final User user = data.get(i);
            viewHolder.name.setText(user.getName());
            new DownloadImageAsync(viewHolder.image).execute(user.getPicture());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void setData(List<User> users) {
        data = users;
        notifyDataSetChanged();
    }


    public void addData(User user) {
        data.add(user);
        notifyItemInserted(data.size() - 1);
    }

    public void removeData(User user) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).get_id().equals(user.get_id())) {
                data.remove(i);
                notifyItemChanged(i);
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final ImageView image;
        private final CardView parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewName);
            image = itemView.findViewById(R.id.imageView2);
            parentLayout = itemView.findViewById(R.id.parrent_layout);

        }
    }

    static class DownloadImageAsync extends AsyncTask<String, Void, Bitmap> {

        WeakReference<ImageView> imageViewWeakReference;

        DownloadImageAsync(ImageView image) {
            imageViewWeakReference = new WeakReference<>(image);
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap res = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                res = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.w("download_error", e.getMessage());
                e.printStackTrace();
            }
            return res;
        }

        protected void onPostExecute(Bitmap result) {
            imageViewWeakReference.get().setImageBitmap(result);
        }
    }
}
