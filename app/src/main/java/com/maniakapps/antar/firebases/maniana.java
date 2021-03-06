package com.maniakapps.antar.firebases;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class maniana extends Fragment {
    EditText cuanto;
    Button btn_dietas;
    View view;
    Button btn_aceptar;
    TextView txtSaldo;
    Spinner sp;
    Integer opcion;
    Integer calorias;
    public maniana() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.maniana_fragment, container, false);
        txtSaldo = view.findViewById(R.id.saldo);
        cuanto = view.findViewById(R.id.cantidad_perro);
        sp = view.findViewById(R.id.accion);
        btn_aceptar = view.findViewById(R.id.btn_Aceptar);
        btn_dietas = view.findViewById(R.id.btnVerDietas);

        btn_dietas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),DietasUsuario.class));
            }
        });

        String[] array_dia = {"huevos con tocino","cereal", "Pan y cafe", "batido de fruas", "barras energeticas"};
        opcion = 0;
        sp.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, array_dia));
        saldo();
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                if(item.equals("huevos con tocino")){
                    calorias = 600;
                    opcion = 1;
                }
                else if(item.equals("cereal")){
                    calorias = 400;
                    opcion = 1;
                }
                else if(item.equals("Pan y cafe")){
                    calorias = 600;
                    opcion = 1;
                }
                else if(item.equals("batido de fruas")){
                    calorias = 200;
                    opcion = 1;
                }
                else if(item.equals("barras energeticas")){
                    calorias = 600;
                    opcion = 1;
                }
                else opcion=0;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast t = Toast.makeText(getActivity(),"Debes seleccionar una accion", Toast.LENGTH_LONG);
                t.show();
            }
        });
        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (opcion==1) showAlert();

            }
        });
        return view;
    }
    //Textos

    public void saldo(){
        SharedPreferences pref = this.getActivity().getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        String saldo = pref.getString("saldo",null);
        txtSaldo.setText(saldo);
    }
    //ModificarSaldos
    public void agregarSaldo(){
        SharedPreferences pref = this.getActivity().getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        int saldo = Integer.parseInt(pref.getString("saldo", null));
        int saldoAgregado = Integer.parseInt(cuanto.getText().toString().trim())*calorias;
        String salAux = ""+ (saldo + saldoAgregado);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("saldo", salAux);
        editor.apply();
        editor.commit();
        Toast.makeText(getActivity(),"Se han ingresado: " + saldoAgregado + " de saldo",Toast.LENGTH_SHORT).show();
        saldo();
    }
    private void showAlert(){
        Integer n = Integer.parseInt(cuanto.getText().toString())*calorias;
        if(TextUtils.isEmpty(cuanto.getText().toString().trim())){
            Toast.makeText(getActivity(),"Ingrese la cantidad a depositar",Toast.LENGTH_SHORT).show();
            return;
        }
        new AlertDialog.Builder(getActivity())
                .setTitle("Ingresar Saldo")
                .setMessage("¿Estás seguro de ingresar " + n+"?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        agregarSaldo();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    public void onBackPressed() {
        startActivity(new Intent(getContext(), Menu_Activity.class));
    }


}
