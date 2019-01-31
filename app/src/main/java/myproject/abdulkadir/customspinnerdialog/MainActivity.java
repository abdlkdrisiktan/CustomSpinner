package myproject.abdulkadir.customspinnerdialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import myproject.abdulkadir.customspinnerdialog.spinner.BaseSearchDialogCompat;
import myproject.abdulkadir.customspinnerdialog.spinner.SearchDialogCompat;
import myproject.abdulkadir.customspinnerdialog.spinner.listeners.SearchResultListener;
import myproject.abdulkadir.customspinnerdialog.spinner.listeners.Searchable;

public class MainActivity extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.spinner);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SearchDialogCompat<>(MainActivity.this, "Araaaa...",
                                         "Hadi ara bakalım ", null,createSampleData() ,
                                         new SearchResultListener<SpinnerData>() {
                                                    @Override
                                                    public void onSelected(
                                                        BaseSearchDialogCompat dialog,
                                                        SpinnerData item, int position) {
                                                        Toast.makeText(MainActivity.this, item.getString(),
                                                                       Toast.LENGTH_SHORT
                                                                      ).show();
                                                        dialog.dismiss();
                                                    }
                                                },getApplicationContext().getResources().getColor(R.color.colorAccent)
                ).show();
            }
        });
    }

    private ArrayList<SpinnerData> createSampleData() {
        ArrayList<SpinnerData> items = new ArrayList<>();
        items.add(new SpinnerData("First item"));
        items.add(new SpinnerData("Second item"));
        items.add(new SpinnerData("Third item"));
        items.add(new SpinnerData("The ultimate item"));
        items.add(new SpinnerData("Last item"));
        items.add(new SpinnerData("Lorem ipsum"));
        items.add(new SpinnerData("Dolor sit"));
        items.add(new SpinnerData("Some random word"));
        items.add(new SpinnerData("guess who's back"));
        return items;
    }
}
