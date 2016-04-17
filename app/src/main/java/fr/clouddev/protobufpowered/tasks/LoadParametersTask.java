package fr.clouddev.protobufpowered.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import fr.clouddev.protobufpowered.R;
import fr.clouddev.protobufpowered.preferences.Params;
import fr.clouddev.protobufpowered.preferences.ProtoToPrefConverter;

/**
 * Created by CopyCat on 14/04/2016.
 */
public class LoadParametersTask extends AsyncTask<Void,Void,Void> {

    private static final String PARAMETER_FILE = "params";

    private Context mContext;

    public LoadParametersTask(Context aContext) {
        this.mContext = aContext;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //TODO load parameters
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        Params.Preferences prefs = Params.Preferences.newBuilder()
                .setInterval(5000L)
                .setNbIssues(5)
                .setRepo("mmmmmma")
                .setUser("atrolla")
                .build();
        ProtoToPrefConverter.convert(prefs,sp.edit()).commit();

        return null;
    }
}
