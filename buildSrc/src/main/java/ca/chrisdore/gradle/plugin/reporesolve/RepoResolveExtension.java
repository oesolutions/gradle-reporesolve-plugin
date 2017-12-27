
package ca.chrisdore.gradle.plugin.reporesolve;

import org.gradle.api.Action;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.initialization.Settings;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepoResolveExtension
{
    private final Settings m_settings;
    private final List<Repo> m_repos = new ArrayList<>();

    public String projectDir = "build/repoResolve";

    public RepoResolveExtension( Settings settings )
    {
        m_settings = settings;
    }

    public void repo( String name, String url )
    {
        m_repos.add( new Repo( name, artifactRepositories -> artifactRepositories.maven( mavenArtifactRepository -> {
            mavenArtifactRepository.setName( name );
            mavenArtifactRepository.setUrl( url );
        } ) ) );
    }

    public void repo( String name, Action<RepositoryHandler> action )
    {
        m_repos.add( new Repo( name, action ) );
    }

    public List<Repo> getRepos()
    {
        return Collections.unmodifiableList( m_repos );
    }

    /**
     * Returns a File object representing a path relative to the root project's directory.
     *
     * @param path the path
     * @return a File
     */
    public File file( String path )
    {
        return new File( m_settings.getRootProject().getProjectDir(), path );
    }

    public String getProjectPath()
    {
        return RepoResolveSettingsPlugin.PROJECT_PATH;
    }

    public File getProjectDir()
    {
        return file( projectDir );
    }
}
