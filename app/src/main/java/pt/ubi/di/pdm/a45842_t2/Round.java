package pt.ubi.di.pdm.a45842_t2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Round extends AppCompatActivity implements View.OnClickListener {
    TextView topo, role, mapa;
    Button next, soueu;
    ImageView img;
    int num_jog, calhas;
    int index = 0;
    ArrayList<String> jogadores = new ArrayList<>();
    ArrayList<String> roles = new ArrayList<>();



    String[] locais = {"Faculdade de Ciencias da Saúde", "Faculdade de Engenharias", "Faculdade de Ciencias Sociais e Humanas", "Faculdade de Ciencias", "Cantina", "Biblioteca"};
    String local_escolhido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // METER APLICAÇÂO EM FULLSCREEN
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //TIRAR A BARRA DE TITULO
        Objects.requireNonNull(getSupportActionBar()).hide();
        // Obter valores passados da outra janela ( nomes dos jogadores e numero dos jogadores)
        Intent jan = getIntent();
        jogadores = jan.getStringArrayListExtra("Jogadores");
        num_jog = jan.getIntExtra("NumJog", 0);
        // começar janela
        setContentView(R.layout.activity_round);
        // definir variaveis
        img = (ImageView) findViewById(R.id.imagem);
        topo = (TextView) findViewById(R.id.Txt_topo);
        topo.setText("É a vez do jogador:\n" + jogadores.get(index));
        role = (TextView) findViewById(R.id.Txt_role);
        mapa = (TextView) findViewById(R.id.Txt_mapa);
        // Ouvir os buttons
        next = (Button) findViewById(R.id.Btn_next);
        next.setOnClickListener(this);
        soueu = (Button) findViewById(R.id.Btn_soueu);
        soueu.setOnClickListener(this);
        // random no espião e no local
        // cada 4 jogadrees tem um espião
        int quantos_espioes = (int)num_jog / 4;
        //ciclo for para escolher os espiões
        for (int i = 0; i < num_jog; i++) {
            roles.add(i, "Inspetor");
        }
        for (int i = 0; i < quantos_espioes; i++) {
            Random rand = new Random();
            int n = rand.nextInt(num_jog);
            if (roles.get(n) == "Espião") {
                i--;
            } else {
                roles.set(n, "Espião");
            }
        }
        Random rand = new Random();
        calhas = rand.nextInt(num_jog);
        local_escolhido = locais[calhas];

        atualizar();
        // TIMER for the round


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, Comecar.class);
        startActivity(intent);
        this.finish();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Btn_next:
                if ((index + 1) < num_jog) {
                    index++;
                    topo.setText("É a vez do jogador:\n" + jogadores.get(index));
                    atualizar();
                    if (index == (num_jog - 1)) {
                        next.setText("SIGA COMEÇAR");
                    }

                } else { // começar o jogo
                    Intent Jan = new Intent(this, timer_game.class);
                    Jan.putExtra("NumJog", num_jog);

                    Jan.putExtra("Jogadores", jogadores);
                    Jan.putExtra("Roles", roles);
                    Jan.putExtra("Local", local_escolhido);


                    startActivity(Jan);

                }
                break;
            case R.id.Btn_soueu:
                role.setVisibility(View.VISIBLE);
                mapa.setVisibility(View.VISIBLE);
                img.setVisibility(View.VISIBLE);
                break;
            default:
                Uteis.MSG(getApplicationContext(), "Esqueceste do on click");
                break;
        }
    }

    private void atualizar() {
        role.setVisibility(View.INVISIBLE);
        mapa.setVisibility(View.INVISIBLE);
        img.setVisibility(View.INVISIBLE);
        role.setText(roles.get(index));
        if (roles.get(index).equals("Espião"))  {
            mapa.setText("Não tens local");
            img.setImageResource(R.drawable.espiao);
        } else {
            mapa.setText(local_escolhido);
            switch (local_escolhido) {
                case "Faculdade de Ciencias da Saúde":
                    img.setImageResource(R.drawable.ubi_ciencias_saude);
                    break;
                case "Faculdade de Engenharias":
                    img.setImageResource(R.drawable.ubi_engenharias);
                    break;
                case "Faculdade de Ciencias Sociais e Humanas":
                    img.setImageResource(R.drawable.ubi_ciencias_sociais_e_humanas);
                    break;
                case "Faculdade de Ciencias":
                    img.setImageResource(R.drawable.polo2);
                    break;
                case "Cantina":
                    img.setImageResource(R.drawable.cantina_ubi);
                    break;
                case "Biblioteca":
                    img.setImageResource(R.drawable.ubi_biblioteca);
                    break;
                default:
                    Uteis.MSG(getApplicationContext(), "Esqueceste do switch");
                    break;
            }
        }

    }
}
