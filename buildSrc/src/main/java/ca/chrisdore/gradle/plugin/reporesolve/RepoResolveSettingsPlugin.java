
package ca.chrisdore.gradle.plugin.reporesolve;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.initialization.Settings;

import java.io.File;

public class RepoResolveSettingsPlugin implements Plugin<Settings>
{
    static final String PROJECT_PATH = ":repoResolve";

    private final RepoResolveExtension m_repoResolveExtension;

    RepoResolveSettingsPlugin( RepoResolveExtension repoResolveExtension )
    {
        m_repoResolveExtension = repoResolveExtension;
    }

    @Override
    public void apply( @SuppressWarnings( "NullableProblems" ) Settings target )
    {
        target.getGradle().settingsEvaluated( settings -> {
            includeProject( settings, PROJECT_PATH, m_repoResolveExtension.getProjectDir() );

            for( Repo repo : m_repoResolveExtension.getRepos() )
            {
                String projectPath = PROJECT_PATH + ":" + repo.getName();
                File projectDir = new File( m_repoResolveExtension.getProjectDir(), repo.getName() );
                includeProject( settings, projectPath, projectDir );

                settings.getGradle().projectsLoaded( gradle -> {
                    Project project = gradle.getRootProject().project( projectPath );
                    configureRepoProject( project, repo );
                } );
            }
        } );
    }

    private void includeProject( Settings settings, String path, File dir )
    {
        //noinspection ResultOfMethodCallIgnored
        dir.mkdirs();

        settings.include( path );
        settings.project( path ).setProjectDir( dir );
    }

    private void configureRepoProject( Project project, Repo repo )
    {
        addMavenRepository( project, repo );

        TestTask t = project.getTasks().create( "test", TestTask.class );
        t.repo = repo;
    }

    private void addMavenRepository( Project project, Repo repo )
    {
        repo.getAction().execute( project.getRepositories() );
    }
}
