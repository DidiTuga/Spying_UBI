package pt.ubi.di.pdm.a45842_t2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class Comecar extends AppCompatActivity implements View.OnClickListener {
    TextView txtCima;
    Button m;
    TextView [] E = new TextView[12];
    ArrayList <String> nomes = new ArrayList<>();
    int num_jog = 0;
    EditText nome_jogador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comecar);
        // METER APLICAÇÂO EM FULLSCREEN
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //TIRAR A BARRA DE TITULO
        Objects.requireNonNull(getSupportActionBar()).hide();
        // ------------------------
        Button n = findViewById(R.id.Btn_adicionar);
        n.setOnClickListener(this);
         m = findViewById(R.id.Btn_comJogo);
        m.setOnClickListener(this);
        nome_jogador = (EditText) findViewById(R.id.ETxt_nome);
        nome_jogador.setOnClickListener(this);
        //--------------------------------
        txtCima = (TextView) findViewById(R.id.Txt_superior);
        // TABELA ONDE METER O NOME DE UTILIZADORES
        E[0] = (TextView) findViewById(R.id.Txt_nome1);
        E[1] = (TextView) findViewById(R.id.Txt_nome2);
        E[2] = (TextView) findViewById(R.id.Txt_nome3);
        E[3] = (TextView) findViewById(R.id.Txt_nome4);
        E[4] = (TextView) findViewById(R.id.Txt_nome5);
        E[5] = (TextView) findViewById(R.id.Txt_nome6);
        E[6] = (TextView) findViewById(R.id.Txt_nome7);
        E[7] = (TextView) findViewById(R.id.Txt_nome8);
        E[8] = (TextView) findViewById(R.id.Txt_nome9);
        E[9] = (TextView) findViewById(R.id.Txt_nome10);
        E[10] = (TextView) findViewById(R.id.Txt_nome11);
        E[11] = (TextView) findViewById(R.id.Txt_nome12);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.Btn_comJogo: // Abre uma nova janela
                if (num_jog < 4 ){
                    txtaviso("São no minimo 4 jogadores!");
                }else {
                    Intent Jan = new Intent(this, Round.class);
                    Jan.putExtra("NumJog", num_jog);

                    Jan.putExtra("Jogadores",nomes);
                    startActivity(Jan);
                }

                break;
            case R.id.Btn_adicionar: // verifica se ainda nao existe esse utilizador e se nao for vazio adiciona-o

                String nome = nome_jogador.getText().toString();

                Boolean aux = false;
                if (nome.equals("")){ // nome vazio
                    txtaviso("Tem de colocar um nome!");
                }else{
                    if (num_jog != 0) { // verificar se há nomes iguais
                        for (String s : nomes) {
                            if (s.equals(nome)) {
                                aux = true;
                            }
                        }
                    }
                    if(aux){ // se houver nao adicionar e mandar uma mensagem
                        txtaviso("Tem de colocar um nome diferente!");
                    } else if(num_jog == 12){ // se atingir o numero maximo mandar
                        txtaviso("Número de jogadores máximo atingido!");
                    }else{ // adicionar caso nada se verifique
                        nomes.add(nome);
                        E[num_jog].setText(nome);
                        num_jog++;
                        txtCima.setText("Número de jogadores: "+num_jog+"/12");
                    }
                }
                nome_jogador.setText("");
                break;
            case R.id.ETxt_nome: // se clicar na caixa para retirar o texto
                nome_jogador.setText("");
                break;
            default:
                Uteis.MSG(getApplicationContext(), "Esqueceste do on click");
                break;
        }
    }
    private void txtaviso(String s) {
        AlertDialog.Builder fim = new AlertDialog.Builder(this);
        fim.setTitle(Html.fromHtml("<font color='#F9FCFF'>AVISO!</font>"));
        fim.setMessage(Html.fromHtml("<font color='#F9FCFF'>"+s+"</font>"));
        fim.setPositiveButton("Ok", (dialog1, which1) -> {
            // fazer nada
        });
        // mudar a cor de fundo e do botão do alerta
        AlertDialog alert = fim.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
                                    @Override
                                    public void onShow(DialogInterface dialogInterface) {
                                        alert.getWindow().setBackgroundDrawableResource(R.color.CLR_fundo_aviso);
                                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#dc7612"));
                                    }
                                });
                alert.show();
    }
}