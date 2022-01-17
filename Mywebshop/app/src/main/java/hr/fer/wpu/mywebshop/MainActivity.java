package hr.fer.wpu.mywebshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        LinearLayout layout=new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setBackgroundColor(Color.LTGRAY);

        LinearLayout zaglavlje =new LinearLayout(this);
        zaglavlje.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        zaglavlje.setBackgroundColor(Color.BLACK);
        zaglavlje.setPadding(0,30,0,100);

        TextView ime=new TextView(this);
        ime.setText("Torbice by Maja");
        ime.setTextSize(35);
        ime.setTextColor(Color.WHITE);
        ime.setGravity(Gravity.START);
        ime.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,2));

        zaglavlje.addView(ime);

        List<String> ukosarici=new ArrayList<>();

        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            ukosarici= (ArrayList<String>) extra.getSerializable("ukosarici");
        }
        Button kosarica=new Button(this);
        final List<String> finalUkosarici = ukosarici;
        kosarica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,KosaricaActivity.class);
                i.putExtra("ukosarici",(Serializable) finalUkosarici);
                startActivity(i);
            }
        });
        kosarica.setText("Košarica");

        zaglavlje.addView(kosarica);

        layout.addView(zaglavlje);

        List<String> proizvodi=new ArrayList<>();
        proizvodi.add("Ruksak Marko, 150kn");
        proizvodi.add("Torba Na Rame Ivana, 100kn");
        proizvodi.add("Torba Na Rame Maja, 100kn");
        proizvodi.add("Torbica Girlboss, 120kn");

        for(final String p:proizvodi){

            LinearLayout ll=new LinearLayout(this);
            ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ll.setBackgroundColor(Color.GRAY);
            ll.setPadding(0,15,0,15);

            TextView t=new TextView(this);
            t.setText(p);
            t.setTextSize(25);
            t.setTextColor(Color.WHITE);
            t.setGravity(Gravity.START);
            t.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,2));

            ll.addView(t);

            Button kupi=new Button(this);
            kupi.setText("Kupi");
            final List<String> finalUkosarici1 = ukosarici;
            kupi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalUkosarici1.add(p);
                    Toast.makeText(MainActivity.this,"Aritkl je stavljen u košaricu",Toast.LENGTH_SHORT).show();
                }
            });
            ll.addView(kupi);

            layout.addView(ll);
        }

        this.setContentView(layout);
    }
}