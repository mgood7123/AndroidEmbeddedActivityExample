package embeddedActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class EmbeddedActivityHost extends AppCompatActivity {
    public final LogUtils log = new LogUtils(
            "EmbeddedActivityHost", "a bug has occurred, this should not happen"
    );

    FragmentManager supportFragmentManager;
    FragmentTransaction fragmentTransaction;

    private void cacheSupportFragmentManagerIfNotCached() {
        log.logMethodName();
        if (supportFragmentManager == null) supportFragmentManager = getSupportFragmentManager();
    }

    private void cacheFragmentTransactionIfNotCached() {
        log.logMethodName();
        cacheSupportFragmentManagerIfNotCached();
        if (fragmentTransaction == null) fragmentTransaction = supportFragmentManager.beginTransaction();
    }

    /**
     * invokes {@link #embeddedActivity_addClient(int, EmbeddedActivityClient)} followed by
     * {@link #embeddedActivity_buildClients()}
     *
     * <p></p>
     *
     * if adding multiple clients in a row then you should optimized this to use
     * {@link #embeddedActivity_addClient(int, EmbeddedActivityClient)}
     * followed by
     * {@link #embeddedActivity_buildClients()} instead
     *
     * <p></p>
     *
     * for example:
     *
     * <p></p>
     *
     *             <p> embeddedActivity_addAndBuildClient(YourContainer, YourClient);           </p>
     *             <p> embeddedActivity_addAndBuildClient(YourOtherContainer, YourOtherClient); </p>
     *
     * <p></p>
     *
     * should be optimized to:
     *
     * <p></p>
     *
     *             <p> embeddedActivity_addClient(YourContainer, YourClient);                   </p>
     *             <p> embeddedActivity_addClient(YourOtherContainer, YourOtherClient);         </p>
     *             <p> embeddedActivity_buildClients();                                         </p>
     *
     * @param hostContainerViewById setContentView must be invoked with a XML LAYOUT container that
     *                             contains this id, runtime created containers ARE NOT SUPPORTED
     * <p></p>
     * @param client the activity to invoke, this must extend the EmbeddedActivityClient class
     */
    public void embeddedActivity_addAndBuildClient(
            final int hostContainerViewById, final EmbeddedActivityClient client
    ) {
        log.logMethodName();
        embeddedActivity_addClient(hostContainerViewById, client);
        embeddedActivity_buildClients();
    }

    /**
     * creates a new {@link #fragmentTransaction} if one is not already created and invokes
     * {@link
     *     androidx.fragment.app.FragmentTransaction#add(int, Fragment)
     *     fragmentTransaction.add(int, Fragment)
     * }
     * @param hostContainerViewById setContentView must be invoked with a XML LAYOUT container that
     *                             contains this id, runtime created containers ARE NOT SUPPORTED
     *
     * <p></p>
     *
     * @param client the activity to invoke, this must extend the EmbeddedActivityClient class
     * @see #embeddedActivity_addAndBuildClient
     */
    public void embeddedActivity_addClient(
            final int hostContainerViewById, final EmbeddedActivityClient client
    ) {
        log.logMethodName();
        cacheFragmentTransactionIfNotCached();
        fragmentTransaction.add(hostContainerViewById, client);
    }

    /**
     * invokes
     * {@link
     *     androidx.fragment.app.FragmentTransaction#commitNow()
     *     fragmentTransaction.commitNow()
     * }, and then deletes the {@link #fragmentTransaction}
     * @see #embeddedActivity_addAndBuildClient
     */
    public void embeddedActivity_buildClients() {
        log.logMethodName();
        fragmentTransaction.commitNow();
        fragmentTransaction = null;
    }

    private Fragment embeddedActivity_findFragmentById(final int hostContainerViewById) {
        log.logMethodName();
        cacheSupportFragmentManagerIfNotCached();
        log.errorAndThrowIfNull(supportFragmentManager, "supportFragmentManager cannot be null");
        return log.errorIfNull(supportFragmentManager.findFragmentById(hostContainerViewById));
    }

    /**
     *
     * finds a client that has been added via
     * {@link #embeddedActivity_addAndBuildClient(int, EmbeddedActivityClient)} or
     * one or more of {@link #embeddedActivity_addClient(int, EmbeddedActivityClient)}
     * followed by {@link #embeddedActivity_buildClients()}
     *
     * <p></p>
     *
     * for example:
     *
     * <p></p>
     *
     *             <p> embeddedActivity_addClient(R.id.fragment_containerA, new Cube());        </p>
     *             <p> embeddedActivity_addClient(R.id.fragment_containerB, new Cube());        </p>
     *             <p> embeddedActivity_buildClients();                                         </p>
     *             <p> // Cube extends {@link EmbeddedActivityClient}                           </p>
     *             <p> Cube x = embeddedActivity_findClientById(R.id.fragment_containerB);      </p>
     *             <p> // can now control Cube's instance from your host                        </p>
     *             <p> // call Cube.shutdown() as an example                                    </p>
     *             <p> x.shutdown();                                                            </p>
     *
     * @param hostContainerViewById setContentView must be invoked with a XML LAYOUT container that
     *                             contains this id, runtime created containers ARE NOT SUPPORTED
     * @return
     */
    public EmbeddedActivityClient embeddedActivity_findClientById(final int hostContainerViewById) {
        log.logMethodName();
        Fragment fragmentById = embeddedActivity_findFragmentById(hostContainerViewById);
        if (fragmentById instanceof EmbeddedActivityClient)
            return (EmbeddedActivityClient) fragmentById;
        log.errorNoStackTrace(
                "error: hostContainerViewById must be class that extends EmbeddedActivityClient"
        );
        return null;
    }
}
