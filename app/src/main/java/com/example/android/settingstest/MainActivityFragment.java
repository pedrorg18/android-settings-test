package com.example.android.settingstest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {

    private static final String KEY_PREF_SYNC_CONN_TYPE = "pref_syncConnectionType";

    TextView textViewSelectedPref;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Button buttonPreferences = (Button) rootView.findViewById(R.id.button_preferences);
        buttonPreferences.setOnClickListener(this);

        textViewSelectedPref = (TextView) rootView.findViewById(R.id.textViewSelectedPref);


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        //Al entrar en la activity mostramos las preferences soleccionadas
        //Obtenemos shared preferences
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //Key de la preferencia tipo de syncr.
        String prefSyncTypesKey = getResources().getString(R.string.pref_syncConnectionType_key);
        //Valor boleano de la preferencia sync activa
        boolean syncConnActive = sharedPref.getBoolean(getResources().getString(R.string.pref_sync_key), false);

        if(!syncConnActive){
            textViewSelectedPref.setText("Sincronización descativada");
        }else{
            //Valor seleccionado de de la preferencia tipo de syncr.
            String syncTypesInternalValue = sharedPref.getString(prefSyncTypesKey,
                    getResources().getString(R.string.pref_syncConnectionTypes_default));
            //A partir del valor seleccionado "interno" o "programatico" obtenemos el label
            String[] texts = getResources().getStringArray(R.array.pref_syncConnectionTypes_entries);
            String[] values = getResources().getStringArray(R.array.pref_syncConnectionTypes_values);
            int index=0;
            for(int i=0;i<values.length;i++){
                if(values[i].equals(syncTypesInternalValue)){
                    index=i;
                }
            }
            textViewSelectedPref.setText(texts[index]);


        }

    }

    public void onClick(View v){
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        getActivity().startActivity(intent);
    }
}
