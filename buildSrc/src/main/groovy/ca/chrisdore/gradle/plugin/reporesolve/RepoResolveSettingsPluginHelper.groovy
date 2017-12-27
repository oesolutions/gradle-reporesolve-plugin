
package ca.chrisdore.gradle.plugin.reporesolve

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

class RepoResolveSettingsPluginHelper implements Plugin<Settings>
{
    @Override
    void apply( Settings settings )
    {
        /*
         * Even though Settings is not statically ExtensionAware, it is at runtime. Therefore, this portion of the
         * plugin is implemented in Groovy to avoid static compiler issues.
         * This should be considered internal behaviour, so future Gradle versions may break it.
         */
        RepoResolveExtension repoResolveExt = settings.extensions.create( 'repoResolve', RepoResolveExtension,
                settings )
        new RepoResolveSettingsPlugin( repoResolveExt ).apply( settings )
    }
}
