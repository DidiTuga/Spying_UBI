package pt.ubi.di.pdm.a45842_t2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // METER APLICAÇÂO EM FULLSCREEN
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //TIRAR A BARRA DE TITULO
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        // Inicializar os botoes
        Button x = findViewById(R.id.Btn_Comecar);
        x.setOnClickListener(this);
        Button y = findViewById(R.id.Btn_Ajuda);
        y.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.Btn_Comecar: // Abre uma nova janela
                Intent Jan = new Intent(this, Comecar.class);

                startActivity(Jan);
                break;
            case R.id.Btn_Ajuda: // abre uma pagina web
                Intent Janela = new Intent(this, Ajuda.class);
                startActivity(Janela);
                break;
            default:
                Uteis.MSG(getApplicationContext(), "Esqueceste do on click");
                break;
        }
    }
}