package co.eco.semana2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button go, repetir;
    private TextView pregunta, puntaje, conTiempo;
    private EditText respuesta;
    private Pregunta p;
    private int time, score;
    private String arregloPreguntas[];

    private boolean textPressed;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pregunta = findViewById(R.id.pregunta);
        puntaje = findViewById(R.id.puntaje);
        conTiempo = findViewById(R.id.conTiempo);
        go = findViewById(R.id.go);
        repetir = findViewById(R.id.repetir);
        respuesta = findViewById(R.id.respuesta);

        score = 0;
        time = 30;
        textPressed = false;

        repetir.setVisibility(View.GONE);

        conTiempo.setText("" + time);
        puntaje.setText("" + score);

        newQuestion();
        timeCount();

        go.setOnClickListener(
                v -> {
                    answer();
                }
        );

        repetir.setOnClickListener(
                v -> {
                    restart();
                    newQuestion();
                }
        );


        pregunta.setOnTouchListener(

                (view, event) -> {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:

                            textPressed = true;
                            new Thread(

                                    () -> {

                                        for (int i = 0; i<20;i++){

                                            try {
                                                Thread.sleep(75);
                                                if (textPressed==false){
                                                    return;
                                                }

                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        runOnUiThread(

                                                ()->{
                                                    newQuestion();
                                                    respuesta.setText("");
                                                }

                                        );

                                    }



                            ).start();

                            break;

                        case MotionEvent.ACTION_UP:

                            textPressed=false;
                            break;
                    }

                    return true;
                }

        );
    }

    public void restart() {

        time = 30;
        score = 0;
        puntaje.setText("" + score);
        repetir.setVisibility(View.GONE);
        respuesta.setVisibility(View.VISIBLE);
        go.setVisibility(View.VISIBLE);
        conTiempo.setText("" + time);
       timeCount();
        respuesta.setText("");


    }

    public void timeCount() {

        new Thread(

                () -> {
                    while (time > 0) {
                        time = time - 1;
                        runOnUiThread(

                                () -> {
                                    conTiempo.setText("" + time);
                                }

                        );

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    runOnUiThread(

                            () -> {
                                repetir.setVisibility(View.VISIBLE);
                                respuesta.setVisibility(View.GONE);
                                go.setVisibility(View.GONE);
                            }

                    );

                }

        ).start();
    }

    public void newQuestion() {

        p = new Pregunta();
        pregunta.setText(p.getPregunta());
    }

    public void answer() {

        String res = respuesta.getText().toString();
        int resInt = Integer.parseInt(res);
        int resCorrecta = p.getRespuesta();

        if (resInt == resCorrecta) {
            Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show();
            score = score + 5;
            puntaje.setText("" + score);
            respuesta.setText("");
        } else {
            Toast.makeText(this, "¡Incorrecto!", Toast.LENGTH_SHORT).show();
            score = score - 4;
        }
        newQuestion();
    }
}