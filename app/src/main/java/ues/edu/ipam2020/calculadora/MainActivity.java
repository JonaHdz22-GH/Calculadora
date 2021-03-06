package ues.edu.ipam2020.calculadora;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();//ocultar la barra donde sale el nombre

        pantalla1 =findViewById(R.id.lblPantalla1);
        pantalla2 =findViewById(R.id.lblPantalla2);
    }

    public static String numeroWidget = "";
    public static String numeroWidget2 = "";
    DecimalFormat formato = new DecimalFormat("0.00000");//formato de decimales
    private TextView pantalla1;
    private TextView pantalla2;

    String texto = "", texto2="";
    boolean Sipunto=false,error=false,estadoBotones=true;
    int numero = 0;
    double NumA = 0, NumB = 0;
    char op;

    public void tecla(View v) {
        if (numero <= 11) {
            switch (v.getId()) {
                case R.id.btn0:
                    texto = texto + "0";
                    break;
                case R.id.btn1:
                    texto = texto + "1";
                    break;
                case R.id.btn2:
                    texto = texto + "2";
                    break;
                case R.id.btn3:
                    texto = texto + "3";
                    break;
                case R.id.btn4:
                    texto = texto + "4";
                    break;
                case R.id.btn5:
                    texto = texto + "5";
                    break;
                case R.id.btn6:
                    texto = texto + "6";
                    break;
                case R.id.btn7:
                    texto = texto + "7";
                    break;
                case R.id.btn8:
                    texto = texto + "8";
                    break;
                case R.id.btn9:
                    texto = texto + "9";
                    break;
                case R.id.btnPi:
                    texto = texto + "3.14159";
                    break;
                default:
                    break;
            }
            numero++;
        }else{
            if(Sipunto){
                Toast.makeText(getApplicationContext(),"Longitud máxima",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Longitud máxima",Toast.LENGTH_SHORT).show();
            }
        }
        ActualizarNumero(1);
        pantalla1.setText(texto);
    }

    public void borrar(View v) {//3 tipos de borrado

        switch (v.getId()) {
            case R.id.ibtnBorrar://de 1 en 1
                if(!texto.equals("")){
                    if (((numero > 0) || Sipunto) && ((texto.charAt(texto.length() - 1)) == '.')){

                        Sipunto = false;
                        texto = texto.substring(0, texto.length() - 1);
                        numero = texto.length();
                    } else {
                        texto = texto.substring(0, texto.length() - 1);
                        numero = numero - 1;
                    }
                }

                break;
            case R.id.btnC: //borra numero de pantalla1
                texto = "";
                numero = 0;
                Sipunto=false;
                break;
            case R.id.btnCE: //borrado completo
                texto = "";
                texto2 = "";
                numero = 0;
                NumA = 0;
                NumB = 0;
                Sipunto=false;

                ActualizarNumero(2);
                pantalla2.setText("");
                break;
        }
        ActualizarNumero(1);
        pantalla1.setText(texto);

    }

    public void punto(View v) {//boton punto validado
        if (!Sipunto) {
            if (numero == 0) {
                texto = texto + "0.";
                Sipunto = true;
            } else {
                texto = texto + ".";
                Sipunto = true;
            }
            numero = 0;
        }
        ActualizarNumero(1);
        pantalla1.setText(texto);
    }

    public void operacion(View v){
        if(texto.equals("")){//validacion si esta vacio
            if(!pantalla2.getText().equals("")){//cambia el tipo de operación si la pantalla2 tiene datos y la pantalla1 no
                texto2 = texto2.substring(0, texto2.length() - 1);
                switch (v.getId()) {
                    case R.id.btnEntre:
                        texto2 = texto2 + "/";
                        break;
                    case R.id.btnPor:
                        texto2 = texto2 + "*";
                        break;
                    case R.id.btnMas:
                        texto2 = texto2 + "+";
                        break;
                    case R.id.btnMenos:
                        texto2 = texto2 + "-";
                        break;
                }

                ActualizarNumero(2);
                pantalla2.setText(texto2);
            }

        }else{//si no esta vacia la pantalla1, continua normal
            if(pantalla2.getText().equals("")){
                //primera vez haciendo la operacion
                switch (v.getId()) {
                    case R.id.btnEntre:
                        texto2 = texto + "/";
                        break;
                    case R.id.btnPor:
                        texto2 = texto + "*";
                        break;
                    case R.id.btnMas:
                        texto2 = texto + "+";
                        break;
                    case R.id.btnMenos:
                        texto2 = texto + "-";
                        break;
                }
                NumA = Double.parseDouble(texto);

            }else{
                //lee el signo y hace la operacion
                op = pantalla2.getText().charAt(pantalla2.getText().length()-1);
                NumB = Double.parseDouble(texto);
                switch (op){
                    case '/':
                        if(NumB==0){
                            error=true;
                            Toast.makeText(getApplicationContext(),"No se puede dividir entre 0",Toast.LENGTH_SHORT).show();
                            texto2="";
                            NumA=0;
                            NumB=0;
                            Sipunto=false;
                        }else{
                            NumA=NumA/NumB;
                        }
                        break;
                    case '*':
                        NumA=NumA*NumB;
                        break;
                    case '+':
                        NumA=NumA+NumB;
                        break;
                    case '-':
                        NumA=NumA-NumB;
                        break;
                }
                if(!error){
                    texto2 = "" + NumA;
                    switch (v.getId()) {
                        case R.id.btnEntre:
                            texto2 = texto2 + "/";
                            break;
                        case R.id.btnPor:
                            texto2 = texto2 + "*";
                            break;
                        case R.id.btnMas:
                            texto2 = texto2 + "+";
                            break;
                        case R.id.btnMenos:
                            texto2 = texto2 + "-";
                            break;
                    }
                }
            }
            texto = "";
            ActualizarNumero(1);
            ActualizarNumero(2);
            error=false;
            numero=0;
            Sipunto=false;
            pantalla1.setText(texto);
            pantalla2.setText(texto2);
        }
    }

    public void Igual(View v){
        if((!texto.equals(""))&&(!texto2.equals(""))){
            op = pantalla2.getText().charAt(pantalla2.getText().length()-1);
            NumB = Double.parseDouble(texto);
            switch (op){
                case '/':
                    if(NumB==0){
                        error=true;
                        Toast.makeText(getApplicationContext(),"No se puede dividir entre 0",Toast.LENGTH_SHORT).show();
                        texto="";
                        texto2="";
                        NumA=0;
                        NumB=0;
                        Sipunto=false;
                    }else{
                        NumA=NumA/NumB;
                    }
                    break;
                case '*':
                    NumA=NumA*NumB;
                    break;
                case '+':
                    NumA=NumA+NumB;
                    break;
                case '-':
                    NumA=NumA-NumB;
                    break;
            }
            if(!error){
                validar(NumA);
                texto2 = "";
                texto = "" + NumA;
            }
            ActualizarNumero(1);
            ActualizarNumero(2);
            error=false;
            pantalla1.setText(texto);
            pantalla2.setText(texto2);
            for (int a=0; a<texto.length(); a++){
                op = texto.charAt(a);
                if(op=='.'){
                    Sipunto=true;
                }
            }
        }

    }


    public void cuadrado(View v){
        NumA = Double.parseDouble(texto);
        NumA = Math.pow(NumA, 2);

        ActualizarNumero(1);
        ActualizarNumero(2);
        error=false;
        pantalla1.setText(""+formato.format(NumA));
        pantalla2.setText("("+texto+")²="+formato.format(NumA));
    }

    public void raiz(View v){
        NumA = Double.parseDouble(texto);

        if(NumA<0){
            NumA = NumA*-1;
            NumA = Math.sqrt(NumA);
            ActualizarNumero(1);
            ActualizarNumero(2);
            error=false;
            pantalla1.setText("");
            pantalla2.setText("√("+texto+")="+formato.format(NumA)+"i");


            texto = "";
            texto2 = "";
            numero = 0;
            NumA = 0;
            NumB = 0;
            Sipunto=false;
            ActualizarNumero(1);
            ActualizarNumero(2);
        }else{
            NumA = Math.sqrt(NumA);
            ActualizarNumero(1);
            ActualizarNumero(2);
            error=false;
            pantalla1.setText(""+formato.format(NumA));
            pantalla2.setText("√"+texto+"="+formato.format(NumA));
        }
    }

    public void Signo(View v){
        if(!texto.equals("")){
            texto= "" + (Double.parseDouble(texto)*(-1));
            pantalla1.setText(texto);
            ActualizarNumero(1);
        }
    }

    public void validar (double num){
        double numlimit1=999999999999.999999999999;
        double numlimit2=0.999999999999;
        double decimal;
        decimal= num - (int)num;
        if(num>numlimit1||decimal>numlimit2){
            numero=12;
        }
    }

    public void ActualizarNumero (int opcion){
        switch (opcion){
            case 1:
                numeroWidget=texto;
                break;
            case 2:
                numeroWidget2=texto2;
                break;
        }
    }

    public void bloqueo(View v){

        if(estadoBotones){
            //operaciones
            View suma = findViewById(R.id.btnMas);
            suma.setEnabled(false);

            View resta = findViewById(R.id.btnMenos);
            resta.setEnabled(false);
            View multiplicacion = findViewById(R.id.btnPor);
            multiplicacion.setEnabled(false);
            View division = findViewById(R.id.btnEntre);
            division.setEnabled(false);

            //teclado numerico
            View cero = findViewById(R.id.btn0);
            cero.setEnabled(false);
            View uno = findViewById(R.id.btn1);
            uno.setEnabled(false);
            View dos = findViewById(R.id.btn2);
            dos.setEnabled(false);
            View tres = findViewById(R.id.btn3);
            tres.setEnabled(false);
            View cuatro = findViewById(R.id.btn4);
            cuatro.setEnabled(false);
            View cinco = findViewById(R.id.btn5);
            cinco.setEnabled(false);
            View seis = findViewById(R.id.btn6);
            seis.setEnabled(false);
            View siete = findViewById(R.id.btn7);
            siete.setEnabled(false);
            View ocho = findViewById(R.id.btn8);
            ocho.setEnabled(false);
            View nueve = findViewById(R.id.btn9);
            nueve.setEnabled(false);

            //demas operaciones
            View ce = findViewById(R.id.btnCE);
            ce.setEnabled(false);
            View c = findViewById(R.id.btnC);
            c.setEnabled(false);
            View borrador = findViewById(R.id.ibtnBorrar);
            borrador.setEnabled(false);
            View raiz = findViewById(R.id.btnRaiz);
            raiz.setEnabled(false);
            View cuadrado = findViewById(R.id.btnCuadrado);
            cuadrado.setEnabled(false);
            View pi = findViewById(R.id.btnPi);
            pi.setEnabled(false);
            View punto = findViewById(R.id.btnPunto);
            punto.setEnabled(false);
            View igual = findViewById(R.id.btnIgual);
            igual.setEnabled(false);
            View masMenos = findViewById(R.id.btnSigno);
            masMenos.setEnabled(false);

            Toast.makeText(getApplicationContext(),"Bloqueando teclado",Toast.LENGTH_SHORT).show();
            estadoBotones=false;
        }else {

            //operaciones
            View suma = findViewById(R.id.btnMas);
            suma.setEnabled(true);
            View resta = findViewById(R.id.btnMenos);
            resta.setEnabled(true);
            View multiplicacion = findViewById(R.id.btnPor);
            multiplicacion.setEnabled(true);
            View division = findViewById(R.id.btnEntre);
            division.setEnabled(true);

            //teclado numerico
            View cero = findViewById(R.id.btn0);
            cero.setEnabled(true);
            View uno = findViewById(R.id.btn1);
            uno.setEnabled(true);
            View dos = findViewById(R.id.btn2);
            dos.setEnabled(true);
            View tres = findViewById(R.id.btn3);
            tres.setEnabled(true);
            View cuatro = findViewById(R.id.btn4);
            cuatro.setEnabled(true);
            View cinco = findViewById(R.id.btn5);
            cinco.setEnabled(true);
            View seis = findViewById(R.id.btn6);
            seis.setEnabled(true);
            View siete = findViewById(R.id.btn7);
            siete.setEnabled(true);
            View ocho = findViewById(R.id.btn8);
            ocho.setEnabled(true);
            View nueve = findViewById(R.id.btn9);
            nueve.setEnabled(true);

            //demas operaciones
            View ce = findViewById(R.id.btnCE);
            ce.setEnabled(true);
            View c = findViewById(R.id.btnC);
            c.setEnabled(true);
            View borrador = findViewById(R.id.ibtnBorrar);
            borrador.setEnabled(true);
            View raiz = findViewById(R.id.btnRaiz);
            raiz.setEnabled(true);
            View cuadrado = findViewById(R.id.btnCuadrado);
            cuadrado.setEnabled(true);
            View pi = findViewById(R.id.btnPi);
            pi.setEnabled(true);
            View punto = findViewById(R.id.btnPunto);
            punto.setEnabled(true);
            View igual = findViewById(R.id.btnIgual);
            igual.setEnabled(true);
            View masMenos = findViewById(R.id.btnSigno);
            masMenos.setEnabled(true);

            Toast.makeText(getApplicationContext(),"Desbloqueando teclado",Toast.LENGTH_SHORT).show();
            estadoBotones=true;
        }

    }
}
