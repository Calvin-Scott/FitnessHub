package uk.ac.lincoln.students.calvinscott13458203.fitnesshubv2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AbsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> absData = new ArrayList<>();
    ArrayList <String> videoID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        new AsyncTaskParseJson().execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_fav) {

            Toast.makeText(AbsActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_Settings) {
            Toast.makeText(AbsActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public class AsyncTaskParseJson extends AsyncTask<String, String, String>
    {

        String UrltoPass = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=15&q=Abs+Workout&key=AIzaSyAWUakR7R2T7NY8O43gh03AsCaDjDxG-ZM";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            try {

                HTTPConection yogaParser = new HTTPConection();

                String json = yogaParser.getJSONFromUrl(UrltoPass);


                JSONObject jsonArray = new JSONObject(json);

                JSONArray items = jsonArray.getJSONArray("items");


                for (int i = 0; i < items.length(); i++) {
                    JSONObject entries = items.getJSONObject(i);
                    JSONObject id = entries.getJSONObject("id");
                    JSONObject snippet = entries.getJSONObject("snippet");


                    if (id != null) {
                        videoID.add(id.getString("videoId"));
                    }

                    if (snippet != null) {

                        absData.add(snippet.getString("title"));
                    }
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            ListView list = (ListView)findViewById(R.id.yogaList);
            ArrayAdapter<String> yogaArrayAdapter = new ArrayAdapter<>(AbsActivity.this, android.R.layout.simple_expandable_list_item_1, absData);
            list.setAdapter(yogaArrayAdapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String userPick = String.valueOf(parent.getItemAtPosition(position));
                    String videoIDAtPos = videoID.get(position);

                    Toast.makeText(AbsActivity.this, userPick, Toast.LENGTH_SHORT).show();

                    Intent videoPlayer = new Intent(AbsActivity.this,VideoWebActivity.class);
                    videoPlayer.putExtra("videoID",videoIDAtPos);
                    videoPlayer.putExtra("Title", String.valueOf(parent.getItemAtPosition(position)));
                    startActivity(videoPlayer);
                }
            });

        }
    }
}
