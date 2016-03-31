package fr.clouddev.protobufpowered.tasks;

import android.content.Context;
import android.os.AsyncTask;
import fr.clouddev.protobuf.converter.AnyProtoConverter;
import fr.clouddev.protobufpowered.proto.Github;
import fr.clouddev.protobufpowered.rest.GithubService;
import retrofit.RestAdapter;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by CopyCat on 31/03/2016.
 */
public class FetchIssuesTask extends AsyncTask<Void,Void,List<Github.Issue>> {

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

        //TODO load from webservice if necessary
        RestAdapter adapter = new RestAdapter.Builder()
                .setConverter(new AnyProtoConverter())
                .setEndpoint("https://api.github.com")
                .build();

        GithubService githubService = adapter.create(GithubService.class);

        List<Github.Issue> issues = githubService.fetchIssues("google","protobuf");
        Type type = issues.getClass();

        //TODO load from local

        return issues;
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
