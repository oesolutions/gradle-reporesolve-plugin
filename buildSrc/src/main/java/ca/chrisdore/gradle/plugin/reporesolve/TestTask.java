
package ca.chrisdore.gradle.plugin.reporesolve;

import org.gradle.api.DefaultTask;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

@SuppressWarnings( "WeakerAccess" )
public class TestTask extends DefaultTask
{
    @Input
    public Repo repo;

    @TaskAction
    public void doTest()
    {
        getLogger().lifecycle( repo.getName() );
        getProject().getRepositories().forEach( repo -> {
            System.out.println( repo.getClass().getName() );
            MavenArtifactRepository r = (MavenArtifactRepository)repo;
            System.out.println( r.getUrl() );
        } );
    }
}
