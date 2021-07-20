package org.tensorflow.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.demo.tracking.MultiBoxTracker;

import java.io.IOException;
import java.io.InputStream;

public class PlantInformation extends AppCompatActivity {
    static String getJsonFromAssets(Context context,String filename) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(filename);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_information);
        de.hdodenhof.circleimageview.CircleImageView plantImg = findViewById(R.id.plantImg);
        TextView plantNameTextView = findViewById(R.id.plantName);
        TextView plantTypeTextView = findViewById(R.id.plantType);
        TextView plantScientificNameTextView = findViewById(R.id.plantScientificName);
        TextView plantDescriptionTextView = findViewById(R.id.plantDescription);
        TextView plantMedicalIndicationsTextView = findViewById(R.id.plantMedicalIndications);
        TextView plantApplicationAndUsesTextView = findViewById(R.id.plantApplicationAndUses);
        TextView plantPartsUsedTextView = findViewById(R.id.plantPartsUsed);
        TextView plantPreparationTextView = findViewById(R.id.plantPreparation);

        String jsonFileString = PlantInformation.getJsonFromAssets(getApplicationContext(), "plants.json");
        try {
            String plantLabel = MultiBoxTracker.kuhaName;
            JSONObject mainObject = new JSONObject(jsonFileString);
            JSONObject plantData = mainObject.getJSONObject(plantLabel);
            String plantName = plantData.getString("NAME_OF_PLANT");
            String plantType = plantData.getString("TYPE_OF_PLANT");
            String plantScientificName = plantData.getString("SCIENTIFIC_NAME");
            String plantDescription = plantData.getString("DESCRIPTION");
            String plantMedicalIndications = plantData.getString("MEDICAL_INDICATIONS");
            String plantApplicationsAndUses = plantData.getString("APPLICATIONS_AND_USES");
            String plantPartsUsed = plantData.getString("PARTS_USED");
            String plantPreparation = plantData.getString("PLANT_PREPARATION");

            if(plantName != null) {

                switch (plantName){
                    case "OREGANO":
                        plantImg.setImageResource(R.drawable.oregano);
                        break;
                    case "BASTON DE SAN JOSE":
                        plantImg.setImageResource(R.drawable.baston);
                        break;
                    case "INSULIN":
                        plantImg.setImageResource(R.drawable.insulin);
                        break;
                    case "POTHOS PLANT":
                        plantImg.setImageResource(R.drawable.pothos);
                        break;
                    case "SNAKE PLANT":
                        plantImg.setImageResource(R.drawable.snake);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"Failed to Load Reseource",Toast.LENGTH_SHORT).show();
                        break;
                }
                plantNameTextView.setText(plantName);
                plantTypeTextView.setText(plantType);
                plantScientificNameTextView.setText(plantScientificName);
                plantDescriptionTextView.setText(plantDescription);
                plantMedicalIndicationsTextView.setText(plantMedicalIndications);
                plantApplicationAndUsesTextView.setText(plantApplicationsAndUses);
                plantPartsUsedTextView.setText(plantPartsUsed);
                plantPreparationTextView.setText(plantPreparation);
            }

            Log.i("data", String.valueOf(plantData));
            Log.i("data", plantName);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), (CharSequence) "Failed to Load Resource",Toast.LENGTH_LONG).show();
        }

        final FloatingActionButton fab = (FloatingActionButton) this.findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiBoxTracker.kuhaName = "";
                MultiBoxTracker.getConfidenceScore = 0;
                startActivity(new Intent(PlantInformation.this, DetectorActivity.class));
            }
        });

    }
}