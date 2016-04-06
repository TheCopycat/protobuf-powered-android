package fr.clouddev.protobufpowered.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import fr.clouddev.protobuf.converter.AnyProtoConverter;
import fr.clouddev.protobufpowered.proto.Github;
import fr.clouddev.protobufpowered.rest.GithubService;
import retrofit.RestAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by CopyCat on 31/03/2016.
 */
public class FetchIssuesTask extends AsyncTask<Void,Boolean,List<Github.Issue>> {

    public static final String TAG = FetchIssuesTask.class.getSimpleName();
    public static final String LAST_SYNC = "lastSync";
    public static final String ISSUES = "issues";
    private Context mContext;
    private boolean mOnline;
    private IssueListener mListener;


    public FetchIssuesTask(Context context, boolean online, IssueListener listener) {
        this.mContext = context;
        this.mOnline = online;
        this.mListener = listener;
    }

    @Override
    protected List<Github.Issue> doInBackground(Void... voids) {
        List<Github.Issue> issueList = null;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences sp = mContext.getSharedPreferences(ISSUES,Context.MODE_PRIVATE);
        Long lastSync = sp.getLong(LAST_SYNC,0L);
        Long interval = prefs.getLong("interval",10000L);
        if (mOnline || new Date().getTime() - lastSync > interval) {
            publishProgress(true);
            //TODO load from webservice if necessary
            RestAdapter adapter = new RestAdapter.Builder()
                    .setConverter(new AnyProtoConverter())
                    .setEndpoint("https://api.github.com")
                    .build();

            GithubService githubService = adapter.create(GithubService.class);

            issueList= githubService.fetchIssues("google", "protobuf");

            Github.Issues issues = Github.Issues.newBuilder().addAllIssues(issueList).build();
            String issuesBase64 = Base64.encodeToString(issues.toByteArray(),Base64.DEFAULT);
            Log.i(TAG,"dista base64="+issuesBase64);
            sp.edit().putString(ISSUES,issuesBase64)
                    .putLong(LAST_SYNC,new Date().getTime()).commit();
            try {
                issueList = Github.Issues.parseFrom(Base64.decode(issuesBase64,Base64.DEFAULT)).getIssuesList();
                Log.i(TAG,"Successfully loaded from base64");
            } catch (Exception e) {
                Log.w(TAG,"could not parse local protobuf",e);
                //issueList = new ArrayList<>();
            }

        } else {
            publishProgress(false);
            String localIssuesBase64 = sp.getString(ISSUES,Github.Issues.newBuilder().build().toByteString().toStringUtf8());
            Log.d(TAG,"local base64="+localIssuesBase64);
            try {
                issueList = Github.Issues.parseFrom(Base64.decode(localIssuesBase64,Base64.DEFAULT)).getIssuesList();
                Log.i(TAG,"Successfully loaded from local storage");
            } catch (InvalidProtocolBufferException e) {
                Log.w(TAG,"could not parse local protobuf",e);
                issueList = new ArrayList<>();
            }
        }
        //TODO load from local

        return issueList;
    }

    @Override
    protected void onProgressUpdate(Boolean... values) {
        if (values[0]) { //ie online
            Toast.makeText(mContext,"Loading online",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext,"Loading from storage", Toast.LENGTH_SHORT).show();
        }


        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<Github.Issue> issues) {
        super.onPostExecute(issues);
        mListener.setIssues(issues);
    }

    public interface IssueListener {
        void setIssues(List<Github.Issue> issues);
    }
}
