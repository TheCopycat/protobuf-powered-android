package fr.clouddev.protobufpowered;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import fr.clouddev.protobufpowered.proto.Github;
import fr.clouddev.protobufpowered.tasks.FetchIssuesTask;

import java.util.List;

public class ListIssuesActivity extends ListActivity implements FetchIssuesTask.IssueListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchIssuesTask(this,false,this).execute();
    }

    @Override
    public void setIssues(List<Github.Issue> issues) {
         setListAdapter(new IssueAdapter(this,issues));
    }
}
