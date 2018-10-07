package com.maniakapps.antar.firebases;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class noche extends Fragment {
    EditText cuanto;
    View view;
    Button btn_aceptar;
    TextView txtSaldo, txtNombre;
    Spinner sp;
    Integer opcion;
    @BindView(R.id.spinner1)
    Spinner spinner1;
    @BindView(R.id.listViewUser)
    ListView listViewUser;
    @BindView(R.id.edtcantidad)
    EditText edtcantidad;
    @BindView(R.id.btnGuardarAqui)
    Button btnGuardarAqui;
    @BindView(R.id.txtcalorias)
    TextView txtcalorias;
    @BindView(R.id.btnexp)
    Button btnexp;
    Unbinder unbinder;
Integer calorias;
    public noche() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.noche_fragment, container, false);

        String[] letra = {"Bisteck", "Papas"};
        calorias = 0;
        sp.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, letra));
        nombre();
        saldo();
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                if (item.equals("Bisteck")) {
                    calorias = 390;
                } else opcion = 0;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast t = Toast.makeText(getActivity(), "Debes seleccionar una accion", Toast.LENGTH_LONG);
                t.show();
            }
        });

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    //Textos
    public void nombre() {
        SharedPreferences pref = this.getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String nombre = pref.getString("users", null);
        txtcalorias.setText(nombre);
    }

    public void saldo() {
        SharedPreferences pref = this.getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String saldo = pref.getString("saldo", null);
        txtcalorias.setText(saldo);
    }


    //ModificarSaldos
    public void agregarSaldo() {
        SharedPreferences pref = this.getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        int saldo = Integer.parseInt(pref.getString("saldo", null));
        int saldoAgregado = Integer.parseInt(cuanto.getText().toString().trim());
        String salAux = "" + (saldo + saldoAgregado);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("saldo", salAux);
        editor.apply();
        editor.commit();
        Toast.makeText(getActivity(), "Se han ingresado: " + saldoAgregado + " de saldo", Toast.LENGTH_SHORT).show();
        saldo();
    }

    private void showAlert() {
        String n = cuanto.getText().toString();
        if (TextUtils.isEmpty(cuanto.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Ingrese la cantidad a depositar", Toast.LENGTH_SHORT).show();
            return;
        }
        new AlertDialog.Builder(getActivity())
                .setTitle("Ingresar Saldo")
                .setMessage("¿Estás seguro de ingresar " + n + "?")
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


    public void retirarSaldo() {
        SharedPreferences pref = this.getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        int saldoActual = Integer.parseInt(pref.getString("saldo", null));
        int saldoDisminuido = Integer.parseInt(cuanto.getText().toString().trim());
        if (saldoActual < saldoDisminuido) {
            Toast.makeText(getActivity(), "El saldo a retirar es mayor que el actual", Toast.LENGTH_SHORT).show();
            return;
        }
        String salAux = "" + (saldoActual - saldoDisminuido);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("saldo", salAux);
        editor.apply();
        editor.commit();
        Toast.makeText(getActivity(), "Se han retirado: " + saldoDisminuido + "de saldo", Toast.LENGTH_SHORT).show();
        saldo();
    }

    private void alertaRetirar() {
        String n = cuanto.getText().toString();
        if (TextUtils.isEmpty(cuanto.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Ingrese la cantidad a retirar", Toast.LENGTH_SHORT).show();
            return;
        }
        new AlertDialog.Builder(getActivity())
                .setTitle("Retirar Saldo")
                .setMessage("¿Estás seguro de retirar " + n + "?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        retirarSaldo();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnGuardarAqui, R.id.btnexp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnGuardarAqui:
                Toast.makeText(getContext(),"Hola",Toast.LENGTH_LONG).show();
                break;
            case R.id.btnexp:
                break;
        }
    }
}
