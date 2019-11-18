package com.project;/*
package com.project;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabProject;
import org.junit.Test;

import java.util.List;

@Slf4j
public class TestGitLabApiDemo{

    public static final String PrivateKey = "fZxRY1_Les_3EiNeXEU4";
    public static final String GitLabUrl = "https://git.mbyundian.com/";

    @Test
    public void findProject(){

        GitlabAPI gitlab = GitlabAPI.connect(GitLabUrl,PrivateKey);
        List<GitlabProject> groupProjects = gitlab.getAllProjects();
        for(GitlabProject project : groupProjects){
            log.info("{}", JSONObject.toJSONString(project));
        }
    }

}
*/
