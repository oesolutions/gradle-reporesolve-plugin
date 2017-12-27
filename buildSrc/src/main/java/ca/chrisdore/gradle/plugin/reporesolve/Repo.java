
package ca.chrisdore.gradle.plugin.reporesolve;

import org.gradle.api.Action;
import org.gradle.api.artifacts.dsl.RepositoryHandler;

public final class Repo
{
    private final String m_name;
    private final Action<RepositoryHandler> m_action;

    Repo( String name, Action<RepositoryHandler> action )
    {
        m_name = name;
        m_action = action;
    }

    String getName()
    {
        return m_name;
    }

    Action<RepositoryHandler> getAction()
    {
        return m_action;
    }
}
