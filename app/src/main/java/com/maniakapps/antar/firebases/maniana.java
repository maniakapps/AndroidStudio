package com.maniakapps.antar.firebases;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class maniana extends Fragment {
    TextView txtcalorias;
    int calorias;
    Spinner spin;
    EditText edtcantidad;
    Button btnguardar;
    Unbinder unbinder;
    View view;


    public maniana() {
        // Required empty public constructor
    }


    public void ingresarCalorias() {
        SharedPreferences pref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        if(TextUtils.isEmpty(edtcantidad.getText().toString().trim()))
            return;
        final int cantidad = Integer.parseInt(edtcantidad.getText().toString().trim());
        if(cantidad==0){
            Toast.makeText(getActivity(), "Ingrese una cantidad valida", Toast.LENGTH_LONG).show();
            return;
        }
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        calorias = 0;
                        break;
                    case 1:
                        calorias = 300*cantidad;
                        break;
                    case 2:
                        calorias = 100*cantidad;
                        break;
                    case 3:
                        calorias = 200*cantidad;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        int caloriasActuales = pref.getInt("caloria",0);
        caloriasActuales+=calorias;
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("caloria",caloriasActuales);
        editor.apply();
        editor.commit();

        Toast.makeText(getActivity(), "Se ha ingresado: " +calorias, Toast.LENGTH_LONG).show();
        mostrarCalorias();
    }

    public void mostrarCalorias(){
        SharedPreferences pref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        String caloria = ""+ pref.getInt("caloria", 0);
        txtcalorias.setText(caloria);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        mostrarCalorias();
    }

    @OnClick({R.id.txtcalorias, R.id.btnGuardar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txtcalorias:
                break;
            case R.id.btnGuardar:
                ingresarCalorias();
                break;
        }
    }

}
