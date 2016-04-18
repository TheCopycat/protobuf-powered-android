package fr.clouddev.protobuf.server;

import fr.clouddev.protobuf.preferences.Params;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by CopyCat on 18/04/2016.
 */
@RestController
public class ConfigController {

    @RequestMapping(method = RequestMethod.GET, path = "/config")
    public Params.Preferences getPreferences() {
        return Params.Preferences.newBuilder()
                .setInterval(20000L)
                .setNbIssues(15)
                .setRepo("protobuf")
                .setUser("google")
                .build();
    }
}
