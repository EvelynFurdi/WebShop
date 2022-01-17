package hr.fer.wpu.mywebshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KosaricaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LinearLayout layout=new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setBackgroundColor(Color.LTGRAY);

        LinearLayout zaglavlje =new LinearLayout(this);
        zaglavlje.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        zaglavlje.setBackgroundColor(Color.BLACK);

        TextView ime=new TextView(this);
        ime.setText("Pregled košarice");
        ime.setTextSize(35);
        ime.setTextColor(Color.WHITE);
        ime.setGravity(Gravity.START);
        ime.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,2));

        zaglavlje.addView(ime);

        layout.addView(zaglavlje);

        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            final List<String> ukosarici= (ArrayList<String>) extra.getSerializable("ukosarici");

            if(ukosarici.size()==0){
                TextView empty=new TextView(this);
                empty.setText("Košarica je prazna");
                empty.setTextSize(25);
                empty.setGravity(Gravity.CENTER);
                layout.addView(empty);
            }
            else{
                Map<String,Integer> kolicine=new HashMap<>();

                for(String proizvod:ukosarici){
                    if(!kolicine.containsKey(proizvod)){
                        kolicine.put(proizvod,1);
                    }
                    else{
                        Integer staraVrijednost=kolicine.get(proizvod);
                        kolicine.put(proizvod,staraVrijednost+1);
                    }
                }

                for(final String p:kolicine.keySet()){
                    LinearLayout ll=new LinearLayout(this);
                    ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    ll.setBackgroundColor(Color.GRAY);
                    ll.setPadding(0,15,0,15);

                    TextView t=new TextView(this);
                    t.setText(p);
                    t.setTextSize(25);
                    t.setTextColor(Color.WHITE);
                    t.setGravity(Gravity.START);
                    t.setLayoutParams(new LinearLayout.LayoutParams(650, ViewGroup.LayoutParams.WRAP_CONTENT));
                    ll.addView(t);

                    TextView kolicina =new TextView(this);
                    kolicina.setText("x"+kolicine.get(p).toString());
                    kolicina.setTextSize(25);
                    kolicina.setTextColor(Color.WHITE);
                    kolicina.setGravity(Gravity.CENTER_HORIZONTAL);
                    kolicina.setLayoutParams(new LinearLayout.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT));

                    ll.addView(kolicina);
                    Button makni=new Button(this);
                    makni.setText("Makni");
                    makni.setLayoutParams(new LinearLayout.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT));
                    makni.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ukosarici.remove(p);
                            Intent refresh=new Intent(KosaricaActivity.this,KosaricaActivity.class);
                            refresh.putExtra("ukosarici",(Serializable)ukosarici);
                            startActivity(refresh);
                        }
                    });

                    ll.addView(makni);
                    layout.addView(ll);
                }
                
            }
            LinearLayout gumbi=new LinearLayout(this);
            gumbi.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            Button povratak=new Button(this);
            povratak.setText("<-Natrag na artikle");
            povratak.setGravity(Gravity.LEFT);
            povratak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(KosaricaActivity.this,MainActivity.class);
                    i.putExtra("ukosarici",(Serializable)ukosarici);
                    startActivity(i);
                }
            });

            Button gotovo=new Button(this);
            if(ukosarici.size()==0)
            	gotovo.setEnabled(false);
            gotovo.setText("Završi s kupnjom!");
            gotovo.setGravity(Gravity.RIGHT);
            gotovo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ukosarici.clear();
                    Toast.makeText(KosaricaActivity.this,"Kupnja obavljena!",Toast.LENGTH_SHORT).show();
                    Intent refresh=new Intent(KosaricaActivity.this,KosaricaActivity.class);
                    refresh.putExtra("ukosarici",(Serializable)ukosarici);
                    startActivity(refresh);
                }
            });

            gumbi.addView(povratak);
            gumbi.addView(gotovo);

            layout.addView(gumbi);

        }
        this.setContentView(layout);
    }
}