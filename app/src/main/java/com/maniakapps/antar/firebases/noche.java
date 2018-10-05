package com.maniakapps.antar.firebases;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class noche extends Fragment {
    TextView txtcalorias;



    EditText edtcantidad;
    Button btnguardar;
    FloatingActionButton fab;
    View view;
    public noche() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.noche_fragment, container, false);

        edtcantidad = view.findViewById(R.id.edtcantidad);
        btnguardar = view.findViewById(R.id.btnGuardar);
        txtcalorias = view.findViewById(R.id.txtcalorias);


        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert();
            }
        });



        mostrarcalorias();
        // Inflate the layout for this fragment
        return view;

    }

    public void mostrarcalorias(){
        SharedPreferences pref = this.getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String caloriass = pref.getString("calorias",null);
        txtcalorias.setText(caloriass);

    }

    public void agregarCalorias(){

        SharedPreferences pref = this.getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        int calorias = Integer.parseInt(pref.getString("calorias", null));
        calorias = Integer.parseInt(edtcantidad.getText().toString().trim());
        String salAux = ""+ (calorias + calorias);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("calorias", salAux);
        editor.apply();
        editor.commit();
        Toast.makeText(getActivity(),"Se han agregado: " + calorias ,Toast.LENGTH_SHORT).show();
        mostrarcalorias();
    }

    private void showAlert(){
        if(TextUtils.isEmpty(edtcantidad.getText().toString().trim())){
            Toast.makeText(getActivity(),"Ingrese cuantos platillos comera",Toast.LENGTH_SHORT).show();
            return;
        }
        int cuantascomes = Integer.parseInt(edtcantidad.getText().toString().trim());
        new AlertDialog.Builder(getActivity())

                .setTitle("Agregar platillo ")
                .setMessage("'Estás seguro de comer "+cuantascomes+"?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        agregarCalorias();
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
}
