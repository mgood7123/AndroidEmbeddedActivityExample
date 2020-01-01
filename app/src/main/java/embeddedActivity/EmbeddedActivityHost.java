package embeddedActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class EmbeddedActivityHost {
    FragmentActivity fragmentActivity;
    EmbeddedActivityClient client;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public EmbeddedActivityHost(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    public EmbeddedActivityHost(EmbeddedActivityClient client) {
        this.client = client;
    }

    private void cacheSupportFragmentManagerIfNotCached() {
        log.logMethodName();
        if (fragmentManager == null) {
            if (fragmentActivity != null)
                fragmentManager = fragmentActivity.getSupportFragmentManager();
            else if (client != null)
                fragmentManager = client.getChildFragmentManager();
            else
                log.errorAndThrow(
                        "neither a fragment activity nor a embedded activity client was found"
                );
        }
    }

    public final LogUtils log = new LogUtils(
            "EmbeddedActivityHost", "a bug has occurred, this should not happen"
    );

    private void cacheFragmentTransactionIfNotCached() {
        log.logMethodName();
        cacheSupportFragmentManagerIfNotCached();
        if (fragmentTransaction == null)
            fragmentTransaction = fragmentManager.beginTransaction();
    }

    /**
     * invokes {@link #addClient(int, EmbeddedActivityClient)} followed by
     * {@link #buildClients()}
     *
     * <p></p>
     *
     * if adding multiple clients in a row then you should optimized this to use
     * {@link #addClient(int, EmbeddedActivityClient)}
     * followed by
     * {@link #buildClients()} instead
     *
     * <p></p>
     *
     * for example:
     *
     * <p></p>
     *
     *             <p> addAndBuildClient(YourContainer, YourClient);           </p>
     *             <p> addAndBuildClient(YourOtherContainer, YourOtherClient); </p>
     *
     * <p></p>
     *
     * should be optimized to:
     *
     * <p></p>
     *
     *             <p> addClient(YourContainer, YourClient);                   </p>
     *             <p> addClient(YourOtherContainer, YourOtherClient);         </p>
     *             <p> buildClients();                                         </p>
     *
     * @param hostContainerViewById setContentView must be invoked with a XML LAYOUT container that
     *                             contains this id, runtime created containers ARE NOT SUPPORTED
     * <p></p>
     * @param client the activity to invoke, this must extend the EmbeddedActivityClient class
     */
    public void addAndBuildClient(
            final int hostContainerViewById, final EmbeddedActivityClient client
    ) {
        log.logMethodName();
        addClient(hostContainerViewById, client);
        buildClients();
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
     * @see #addAndBuildClient
     */
    public void addClient(
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
     * @see #addAndBuildClient
     */
    public void buildClients() {
        log.logMethodName();
        fragmentTransaction.commitNow();
        fragmentTransaction = null;
    }

    private Fragment findFragmentById(final int hostContainerViewById) {
        log.logMethodName();
        cacheSupportFragmentManagerIfNotCached();
        log.errorAndThrowIfNull(fragmentManager, "fragmentManager cannot be null");
        return log.errorIfNull(fragmentManager.findFragmentById(hostContainerViewById));
    }

    /**
     *
     * finds a client that has been added via
     * {@link #addAndBuildClient(int, EmbeddedActivityClient)} or
     * one or more of {@link #addClient(int, EmbeddedActivityClient)}
     * followed by {@link #buildClients()}
     *
     * <p></p>
     *
     * for example:
     *
     * <p></p>
     *
     *             <p> addClient(R.id.fragment_containerA, new Cube());        </p>
     *             <p> addClient(R.id.fragment_containerB, new Cube());        </p>
     *             <p> buildClients();                                         </p>
     *             <p> // Cube extends {@link EmbeddedActivityClient}                           </p>
     *             <p> Cube x = findClientById(R.id.fragment_containerB);      </p>
     *             <p> // can now control Cube's instance from your host                        </p>
     *             <p> // call Cube.shutdown() as an example                                    </p>
     *             <p> x.shutdown();                                                            </p>
     *
     * @param hostContainerViewById setContentView must be invoked with a XML LAYOUT container that
     *                             contains this id, runtime created containers ARE NOT SUPPORTED
     * @return
     */
    public EmbeddedActivityClient findClientById(final int hostContainerViewById) {
        log.logMethodName();
        Fragment fragmentById = findFragmentById(hostContainerViewById);
        if (fragmentById instanceof EmbeddedActivityClient)
            return (EmbeddedActivityClient) fragmentById;
        log.errorNoStackTrace(
                "error: hostContainerViewById must be class that extends EmbeddedActivityClient"
        );
        return null;
    }

    public void invokeOverview() {
        // TODO
    }
}
