package pt.ubi.di.pdm.a45842_t2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class timer_game extends AppCompatActivity implements View.OnClickListener {
    TextView text_view;
    EditText edit_text;
    ArrayList<String> jogadores = new ArrayList<>();
    ArrayList<String> roles = new ArrayList<>();
    String local_escolhido, jogador_atual;
    int num_jog, num_espiao, flag = 0;
    ArrayList<String> jogadores_random = new ArrayList<>();
    Button expulsar, baixo, espiao;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // METER APLICAÇÂO EM FULLSCREEN
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //TIRAR A BARRA DE TITULO
        Objects.requireNonNull(getSupportActionBar()).hide();
        // Obter os valores passados pela outra janela
        Intent jan = getIntent();
        jogadores = jan.getStringArrayListExtra("Jogadores");
        local_escolhido = jan.getStringExtra("Local");
        roles = jan.getStringArrayListExtra("Roles");
        // Começar a janela
        setContentView(R.layout.activity_timer_game);
        // Definir variaveis
        text_view = (TextView) findViewById(R.id.Txt_centro);
        edit_text = (EditText) findViewById(R.id.Etxt_adivinhar);
        spinner = (Spinner) findViewById(R.id.SPN_locais);
        // iniciar o spinner com o array de locais
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(timer_game.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.STR_array));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        // Ouvir os buttons
        expulsar = (Button) findViewById(R.id.Btn_expulsar);
        expulsar.setOnClickListener(this);
        baixo = (Button) findViewById(R.id.Btn_baixo);
        baixo.setOnClickListener(this);
        espiao = (Button) findViewById(R.id.Btn_espiao);
        espiao.setOnClickListener(this);
        // Numero de jogadores
        num_jog = jogadores.size();
        // Numero de espiões
        num_espiao = (int) num_jog / 4;
        atualizar_jogador();

    }
    // falta a condição de 2 jogadores o jogo acaba
    // falta a condição quando todos os espiões acabam o jogo acaba

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_baixo:

                if (flag == 1) {
                    // expulsar o jogador que tentou adivinhar o local  quee clicou seguinte
                    int n = jogadores.indexOf(jogador_atual);
                    roles.remove(n);
                    jogadores.remove(jogador_atual);
                    jogadores_random.remove(jogador_atual);
                    num_espiao--;
                    num_jog = jogadores.size();
                    txtaviso("O jogador " + jogador_atual + " foi expulso\n por ter tentado adivinhar o local");

                    acabar_jogo(false);

                    atualizar_jogador();

                } else {
                    // se o jogador não tiver tentado adivinhar o local
                    // o jogador atual passa a ser o seguinte
                    atualizar_jogador();
                }
                break;
            case R.id.Etxt_adivinhar:
                edit_text.setText("");
                break;
            case R.id.Btn_expulsar:
                if (flag == 1) {
                    // expulsar o jogador que tentou adivinhar o local  quee clicou seguinte
                    int n = jogadores.indexOf(jogador_atual);
                    roles.remove(n);
                    jogadores.remove(jogador_atual);
                    jogadores_random.remove(jogador_atual);
                    num_espiao--;
                    num_jog = jogadores.size();
                    txtaviso("O jogador " + jogador_atual + " foi expulso\n por ter tentado adivinhar o local");
                    acabar_jogo(false);

                } else {
                    // Ver se todos querem expulsar o jogador com uma pop up
                    // Se sim, expulsar o jogador
                    // Se não, voltar a jogar
                    String nome = edit_text.getText().toString();
                    if (jogadores.contains(nome) && !nome.equals(jogador_atual)) { // verificar se contem o nome
                        AlertDialog.Builder confirmar = new AlertDialog.Builder(this);
                        confirmar.setTitle(Html.fromHtml("<font color='#F9FCFF'>Expulsar jogador</font>"));
                        confirmar.setMessage(Html.fromHtml("<font color='#F9FCFF'>Todos os jogadores querem expulsar o jogador " + nome + "?</font>"));
                        confirmar.setPositiveButton("Sim", (dialog, which) -> {
                            //Uteis.MSG(getApplicationContext(), num_jog + " " + num_espiao);
                            // expulsar o jogador que foi escolhido para ser expulso e atualizar o numero de jogadores e espiões e roles e jogadores_random
                            int aux = jogadores.indexOf(nome);
                            if (roles.get(aux).equals("Espião")) {
                                num_espiao--;
                            }
                            txtaviso("O jogador " + nome + " foi expulso");
                            roles.remove(aux);
                            jogadores.remove(nome);
                            jogadores_random.remove(nome);
                            num_jog = jogadores.size();
                            acabar_jogo(false);
                            atualizar_jogador();
                            //Uteis.MSG(getApplicationContext(), num_jog + " " + num_espiao);
                        });
                        confirmar.setNegativeButton("Não", (dialog, which) -> {
                            atualizar_jogador();
                        });
                        // mudar a cor de fundo e do botão do alerta
                        AlertDialog alert = confirmar.create();
                        alert.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                alert.getWindow().setBackgroundDrawableResource(R.color.CLR_fundo_aviso);
                                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#dc7612"));
                                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                            }
                        });
                        alert.show();
                    } else if (nome.equals(jogador_atual)) {
                        txtaviso("Não podes expulsar-te a ti mesmo!");
                    } else {
                        txtaviso("Jogador não existe");
                    }
                }
                edit_text.setText("");
                break;
            case R.id.Btn_espiao:
                // adivinhar o local se nao adivinhar esse jogador é expulso
                String local = spinner.getSelectedItem().toString();
                int n = jogadores.indexOf(jogador_atual);
                String role = roles.get(n);
                if (flag == 0 && role.equals("Espião")) {
                    txtaviso("Se não tentares adivinhar o local serás expulso a mesma!");
                    espiao.setText("Adivinhar");
                    flag = 1;
                    spinner.setVisibility(View.VISIBLE);
                } else if (!role.equals("Espião")) {
                    txtaviso("Não és um espião!");
                } else {
                    flag = 0;
                    if (local.equals(local_escolhido)) {
                        acabar_jogo(true);
                    } else {
                        roles.remove(n);
                        jogadores.remove(jogador_atual);
                        jogadores_random.remove(jogador_atual);
                        txtaviso("O jogador " + jogador_atual + " foi expulso\n por ter tentado adivinhar o local");
                        num_espiao--;
                        num_jog = jogadores.size();
                        if (num_espiao == 0) {
                            // Acabar o jogo
                            acabar_jogo(false); //fsfs
                        } else {
                            atualizar_jogador();
                        }
                    }
                }
                edit_text.setText("");
                break;
            default:
                Uteis.MSG(getApplicationContext(), "Esqueceste do on click");
                break;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, Comecar.class);
        startActivity(intent);
        this.finish();
    }

    // funcao para atualizar os jogadores de forma random
    private void atualizar_jogador() {
        if (jogadores_random.size() == num_jog) {
            jogadores_random.clear();
            atualizar_jogador();
        } else {
            Random rand = new Random();
            int n = rand.nextInt(num_jog);
            // Verificar se o jogador ja foi escolhido
            if (jogadores_random.contains(jogadores.get(n))) {
                atualizar_jogador();
            } else {
                jogadores_random.add(jogadores.get(n));
                jogador_atual = jogadores.get(n);
                text_view.setText(MessageFormat.format("{0}\nÉ a sua vez de\n fazer uma pergunta", jogador_atual));
                //text_view.setText("É a vez do jogador\n" + jogador_atual + "\n  fazer a pergunta!");
                spinner.setVisibility(View.INVISIBLE);
                flag = 0;
                espiao.setText(R.string.STR_local);

            }

        }
    }

    private void acabar_jogo(Boolean aux) {
        if (num_espiao == 0) {
            // Acabar o jogo
            AlertDialog.Builder fim = new AlertDialog.Builder(this);
            fim.setTitle(Html.fromHtml("<font color='#F9FCFF'>Inspetores Ganharam!</font>"));
            fim.setMessage(Html.fromHtml("<font color='#F9FCFF'>Todos os espiões foram expulsos!</font>"));
            fim.setPositiveButton("Ok", (dialog1, which1) -> {

                Intent jan = new Intent(timer_game.this, MainActivity.class);
                startActivity(jan);
                this.finish();
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
        } else if (num_jog == 2) {
            // Acabar o jogo
            AlertDialog.Builder fim = new AlertDialog.Builder(this);
            fim.setTitle(Html.fromHtml("<font color='#F9FCFF'>Os espiões ganharam!</font>"));
            fim.setMessage(Html.fromHtml("<font color='#F9FCFF'>Sobraram 2 jogadores, o jogo acabou!</font>"));
            fim.setPositiveButton("Ok", (dialog1, which1) -> {

                Intent jan = new Intent(timer_game.this, MainActivity.class);
                startActivity(jan);
                this.finish();
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
        } else if (aux) {
            // Espiões ganharam
            AlertDialog.Builder fim = new AlertDialog.Builder(this);
            fim.setTitle(Html.fromHtml("<font color='#F9FCFF'>Os espiões ganharam!</font>"));
            fim.setMessage(Html.fromHtml("<font color='#F9FCFF'>O espião " + jogador_atual + " adivinhou o local!</font>"));
            fim.setPositiveButton("Ok", (dialog1, which1) -> {

                Intent jan = new Intent(timer_game.this, MainActivity.class);
                startActivity(jan);
                this.finish();
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

    private void txtaviso(String s) {
        AlertDialog.Builder fim = new AlertDialog.Builder(this);
        fim.setTitle(Html.fromHtml("<font color='#F9FCFF'>AVISO!</font>"));
        fim.setMessage(Html.fromHtml("<font color='#F9FCFF'>" + s + "</font>"));
        fim.setPositiveButton("Ok", (dialog1, which1) -> {
            // fazer nada
        });
        // mudar a cor de fundo e do botão do alerta
        AlertDialog alert = fim.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface dialogInterface) {
                alert.getWindow().setBackgroundDrawableResource(R.color.CLR_fundo_aviso);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#dc7612"));
            }
        });
        alert.show();
    }
}
