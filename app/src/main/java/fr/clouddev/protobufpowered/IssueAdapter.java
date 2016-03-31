package fr.clouddev.protobufpowered;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import fr.clouddev.protobufpowered.proto.Github;

import java.util.List;

/**
 * Created by CopyCat on 31/03/2016.
 */
public class IssueAdapter extends BaseAdapter {

    private List<Github.Issue> mIssues;
    private Context mContext;

    public IssueAdapter(Context context, List<Github.Issue> issues) {
        this.mIssues = issues;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mIssues!=null?mIssues.size():0;
    }

    @Override
    public Object getItem(int i) {
        return i<getCount()?mIssues.get(i):null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RelativeLayout mLayout;
        if (view != null) {
            mLayout = (RelativeLayout)view;
        } else {
            mLayout = (RelativeLayout)LayoutInflater.from(mContext).inflate(R.layout.issue_item,viewGroup,false);
        }
        Github.Issue issue = (Github.Issue)getItem(i);
        ((TextView)mLayout.findViewById(R.id.textView)).setText(issue.getTitle());
        ((TextView)mLayout.findViewById(R.id.textView2)).setText(issue.getState());
        ((TextView)mLayout.findViewById(R.id.textView3)).setText(issue.getBody());
        return mLayout;
    }
}
