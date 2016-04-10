package rest;

import fr.clouddev.protobufpowered.proto.Github;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class IssuesTest {
    @Test
    public void issuesToString() throws Exception {
        List<Github.Issue> issues = new ArrayList<>();
        Github.Issue issue = Github.Issue.newBuilder().setId("toto").setNumber(41).setBody("This is a body")
                .setState("Open")
                .setTitle("Issue Toto").build();
        issues.add(issue);
        issue = Github.Issue.newBuilder().setId("titi").setNumber(32)
                .setState("Closed")
                .setTitle("Issue titi").build();
        issues.add(issue);

        System.out.println(Github.Issues.newBuilder().addAllIssues(issues).toString());
    }
}