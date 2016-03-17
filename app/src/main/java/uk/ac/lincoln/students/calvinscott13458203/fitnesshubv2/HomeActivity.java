package uk.ac.lincoln.students.calvinscott13458203.fitnesshubv2;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;
import android.net.Uri;


public class HomeActivity extends AppCompatActivity {

    private List<HomeData> homeDataList;

    // recyclerView, adapter and layoutManager defined
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;
    String fileData = "HomeData";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addData();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // Stops content from changing the layout size of the recycler view
        recyclerView.setHasFixedSize(true);

        rLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rLayoutManager);

        // creates adapter for the recyclerView
        rAdapter = new RecyclerAdapter(homeDataList);
        recyclerView.setAdapter(rAdapter);
    }
    private void addData()
    {
        homeDataList = new ArrayList<>();
        homeDataList.add(new HomeData("Yoga", R.drawable.yoga));
        homeDataList.add(new HomeData("Cardio", R.drawable.cardio));
        homeDataList.add(new HomeData("Abs", R.drawable.abs));
        homeDataList.add(new HomeData("Stamina", R.drawable.stamina));
        homeDataList.add(new HomeData("Beginner", R.drawable.j));
        homeDataList.add(new HomeData("Extreme", R.drawable.noexcuses));

        FileOutputStream outputStream;
        File file = getFileStreamPath(fileData);

// first check if file exists, if not create it
        if(file == null || !file.exists())
        {
            try {
                outputStream = openFileOutput(fileData, MODE_PRIVATE);
                outputStream.write(homeDataList.size());
                outputStream.write("\r\n".getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
// if file already exists then append location data to it
        else if(file.exists())
        {
            try {
                outputStream = openFileOutput(fileData, Context.MODE_APPEND);
                outputStream.write(homeDataList.size());
                outputStream.write("\r\n".getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Adapter to display recycler view.
     */
    public static class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
    {
        List<HomeData> homeDataList;

        public static class  ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            CardView homeCard;
            TextView title;
            ImageView image;

            private Context context;


            /**
             * ViewHolder to create the new view of the recyclerview using the homeCard and setting that to clickable
             */
            public ViewHolder (View v)
            {
                super(v);
                homeCard = (CardView)v.findViewById(R.id.card_view);
                title = (TextView) v.findViewById(R.id.card_title);
                image = (ImageView)v.findViewById(R.id.card_image);
                homeCard.setClickable(true);
                v.setOnClickListener(this);
                context = v.getContext();

            }

            /**
             * On click using a switch to start different intents based on the
             * location of the card that is clicked by the user based on the array of data
             *
             */
            @Override
            public void onClick(View v) {

                switch (getPosition())
                {
                    case  0:
                        Intent intent = new Intent(context, YogaActivity.class);
                        context.startActivity(intent);
                        break;

                    case 1:
                        intent = new Intent(context,CardioActivity.class);
                        context.startActivity(intent);
                        break;

                    case  2:
                       intent = new Intent(context, AbsActivity.class);
                        context.startActivity(intent);
                        break;

                    case 3:
                        intent = new Intent(context,StaminaActivity.class);
                        context.startActivity(intent);
                        break;

                    case  4:
                        intent = new Intent(context, BeginnerActivity.class);
                        context.startActivity(intent);
                        break;

                    case 5:
                        intent = new Intent(context,ExtremeActivity.class);
                        context.startActivity(intent);
                        break;

                }

            }
        }

        public RecyclerAdapter(List<HomeData> homeDataList)
        {
            this.homeDataList = homeDataList;
        }

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_home_layout,parent,false);

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

            /**
            * binds the view holder with different titles and images passed from the list array
             * and creates a card for each
             */
        @Override
        public void onBindViewHolder(ViewHolder holder,int position)
        {
            holder.title.setText(homeDataList.get(position).title);
            holder.image.setImageResource(homeDataList.get(position).photo);
        }

        @Override
        public  int getItemCount()
        {
            return homeDataList.size();
        }

    }
}

