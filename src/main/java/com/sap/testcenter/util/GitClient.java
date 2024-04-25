package com.sap.testcenter.util;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.SshTransport;

import com.jcraft.jsch.Session;
import com.sap.testcenter.model.GitInfo;

public class GitClient {
    public static final String remoteRepoServer = "git.wdf.sap.corp:29418";
    public static final String localRepoRoot = "C:/tcgit/";
    public static final String user = "i068096";
    
    private Git git;
    private String localRepoPath = "";
    private String remoteRepoPath = "";
    private String branch = "";
    
    public GitClient() {
        this.remoteRepoPath = this.setRemoteRepoPath("/anwfin/analytics-ui.git");
        this.localRepoPath = this.setLocalRepoPath("analyticsui");
        this.branch = "ft-designer";
    }
    
    public GitClient(String remotePath, String localRepoName, String branch) {
        this.remoteRepoPath = this.setRemoteRepoPath(remotePath);
        this.localRepoPath = this.setLocalRepoPath(localRepoName);
        this.branch = branch;
    }
    
    public GitClient(GitInfo gitInfo) {
        this(gitInfo.getRemoteUrl(), gitInfo.getLocalPath(), gitInfo.getBranch());
    }

    private String setRemoteRepoPath(String remotePath) {
        return "ssh://" + user + "@" + remoteRepoServer + remotePath;
    }
    
    private String setLocalRepoGitConfig() {
        return this.localRepoPath + ".git";
    }
    
    private String setLocalRepoPath(String localRepoName) {
        return localRepoRoot + localRepoName + "/";
    }
    
    public String getLocalRepoPath() {
        return this.localRepoPath;
    }
    
    public boolean cloneRepo() {
        boolean result = false;
        try {
            git = Git.cloneRepository()
                    .setURI(remoteRepoPath)
                    .setBranch(branch)
                    .setTransportConfigCallback(transport -> {
                        ((SshTransport)transport).setSshSessionFactory(new JschConfigSessionFactory() {
                            @Override
                            protected void configure(OpenSshConfig.Host host, Session session) {
                                session.setConfig("StrictHostKeyChecking","no");
                            }
                        });
                    })
                    .setDirectory(new File(localRepoPath)).call();
            System.out.print("Amy: clone done.");
            result = true;
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean isLocalRepoExisted() {
        return new File(this.setLocalRepoGitConfig()).exists();
    }
    
    public void fetchRepo() {
        if (!isLocalRepoExisted()) {
            cloneRepo();
            return;
        }
        try {
            git = Git.open(new File(this.setLocalRepoGitConfig()));
            git.fetch().call();
            System.out.print("Amy: fetch done.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }
    
//    public static void main(String[] args) {
//        GitClient client = new GitClient();
//        client.fetchRepo();
//    };
}
