package fr.clouddev.protobufpowered.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import fr.clouddev.protobuf.converter.AnyProtoConverter;
import fr.clouddev.protobufpowered.R;
import fr.clouddev.protobufpowered.preferences.Params;
import fr.clouddev.protobufpowered.preferences.ProtoToPrefConverter;
import fr.clouddev.protobufpowered.rest.ConfigService;
import retrofit.RestAdapter;

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
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("http://10.0.2.2:8080")
                .setConverter(new AnyProtoConverter())
                .build();
        ConfigService service = adapter.create(ConfigService.class);
        prefs = service.getPreferences();
        ProtoToPrefConverter.convert(prefs,sp.edit()).commit();

        return null;
    }
}
