package fr.wildcodeschool.openweathermap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private final static String API_KEY = "c060b97e11244d23d91b9792768e37dd";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Crée une file d'attente pour les requêtes vers l'API
        RequestQueue requestQueue = Volley.newRequestQueue(this);

// TODO : URL de la requête vers l'API
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=Toulouse&appid=" + API_KEY;

// Création de la requête vers l'API, ajout des écouteurs pour les réponses et erreurs possibles
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
// TODO : traiter la réponse
                        try {
                            JSONArray infoList = response.getJSONArray("list");


                            for (int i = 0; i < infoList.length(); i++) {

                                JSONObject weatherPosition = (JSONObject) infoList.get(i);
                                JSONArray weather = weatherPosition.getJSONArray("weather");
                                int len = weather.length();
                                for (int j = 0; j < len; j++) {
                                    JSONObject weatherInfos = (JSONObject) weather.get(j);
                                    String description = weatherInfos.getString("description");
                                    Toast.makeText(MainActivity.this, description, Toast.LENGTH_SHORT).show();

                                    Log.d("TAGMETEO", "description:" + description);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("TAGMETEO", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
// Afficher l'erreur
                        Log.d("VOLLEY_ERROR", "onErrorResponse: " + error.getMessage());
                    }
                }
        );
        // On ajoute la requête à la file d'attente
        requestQueue.add(jsonObjectRequest);
    }
}