package embeddedActivity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.HashMap;

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

    LayoutInflater layoutInflater;

    private void cacheLayoutInflaterIfNotCached() {
        log.logMethodName();
        if (layoutInflater == null) {
            if (fragmentActivity != null)
                layoutInflater = fragmentActivity.getLayoutInflater();
            else if (client != null)
                layoutInflater = client.getLayoutInflater();
            else
                log.errorAndThrow(
                        "neither a fragment activity nor a embedded activity client was found"
                );
        }
    }


    public View inflate(@NonNull @LayoutRes int layoutResID) {
        cacheLayoutInflaterIfNotCached();
        return layoutInflater.inflate(layoutResID, null, false);
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

    public HashMap<Integer, Integer> hostContainerViewByIdMap = new HashMap<>();

    public int bindId(final int hostContainerViewById) {
        log.logMethodName();
        if (!hostContainerViewByIdMap.containsKey(hostContainerViewById)) {
            log.log("no mapped key " + hostContainerViewById + " exists");
            generateId(hostContainerViewById);
        } else {
            log.log("mapped key " + hostContainerViewById + " exists");
        }
        int id = getId(hostContainerViewById);
        if (fragmentActivity != null) {
            fragmentActivity.findViewById(hostContainerViewById).setId(id);
            log.log("binded " + hostContainerViewById + " to id " + id);
        } else if (client != null) {
            client.root.findViewById(hostContainerViewById).setId(id);
            log.log("binded " + hostContainerViewById + " to id " + id);
        } else
            log.errorAndThrow(
                    "neither a fragment activity nor a embedded activity client was found"
            );
        return id;
    }

    Integer generateId(final int hostContainerViewById) {
        log.logMethodName();
        if (!hostContainerViewByIdMap.containsKey(hostContainerViewById)) {
            int id = View.generateViewId();
            hostContainerViewByIdMap.put(hostContainerViewById, id);
            log.log("mapped id " + hostContainerViewById + " to id " + id);
            return id;
        } else {
            log.log("mapped key " + hostContainerViewById + " exists");
            return getId(hostContainerViewById);
        }
    }

    Integer getId(final int hostContainerViewById) {
        log.logMethodName();
        Integer id = hostContainerViewByIdMap.get(hostContainerViewById);
        if (id == null) {
            log.log("no value found for key " + hostContainerViewById);
            id = hostContainerViewById;
        } else {
            log.log("value found for key " + hostContainerViewById);
        }
        log.log("returning id " + id);
        return id;
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
        fragmentTransaction.add(getId(hostContainerViewById), client);
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

    public SOCL getScreenshotOnClickListener(
            @NonNull @LayoutRes int layoutResID, @Nullable ImageView viewToSaveTo
    ) {
        return new SOCL(layoutResID, viewToSaveTo);
    }

    public SOCL getScreenshotOnClickListener(
            @Nullable Resources resources, @Nullable View view, @Nullable ImageView viewToSaveTo
    ) {
        return new SOCL(resources, view, viewToSaveTo);
    }

    public View setupHostFragmentContainer(
            @NonNull @LayoutRes int fragment_container_layout,
            ViewGroup targetView,
            Button overviewButton
    ) {
        View x = inflate(fragment_container_layout);
        overviewButton.setOnClickListener(
                new overviewOnClickListener(x, targetView)
        );
        return x;
    }

    class overviewOnClickListener implements View.OnClickListener {
        boolean parent = false;

        View view;
        ViewGroup targetView;
        ViewGroup viewParent;
        int viewParentIndex;

        public overviewOnClickListener(View view, ViewGroup targetView) {
            this.view = view;
            this.targetView = targetView;
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            log.logMethodName();
            parent = !parent;
            log.log("current targetView is " + targetView.getChildAt(0));
            if (targetView.getChildCount() != 0) {
                log.log("removing");
                targetView.removeViewAt(0);
                log.log("removed");
            }
            if (parent) {
                log.log("new targetView is " + view);
                if (view != null) {
                    viewParent = (ViewGroup) view.getParent();
                    if (viewParent != null) {
                        log.log("view has a parent");
                        log.log("removing");
                        viewParentIndex = viewParent.indexOfChild(view);
                        viewParent.removeViewAt(viewParentIndex);
                        log.log("removed");
                    }
                    targetView.addView(view, 0);
                }
            } else {
                // restore view
                log.log("restoring view");
                viewParent.addView(view, viewParentIndex);
                log.log("restored view");
            }
        }
    }

    class SOCL implements View.OnClickListener {

        View src;
        ImageView dest;
        Resources res;

        @SuppressLint("ResourceType")
        public SOCL(@NonNull @LayoutRes int layoutResID, @Nullable ImageView viewToSaveTo) {
            if (fragmentActivity != null) {
                src = fragmentActivity.findViewById(layoutResID);
                res = fragmentActivity.getResources();
            } else if (client != null) {
                src = client.findViewById(layoutResID);
                res = client.getResources();
            } else
                log.errorAndThrow(
                        "neither a fragment activity nor a embedded activity client was found"
                );
            dest = viewToSaveTo;
        }

        public SOCL(
                @Nullable Resources resources, @Nullable View view, @Nullable ImageView viewToSaveTo
        ) {
            src = view;
            dest = viewToSaveTo;
            res = resources;
        }

        @Override
        public void onClick(View v) {
        }
    }

    public void invokeOverview() {
        // TODO
    }

    String key = getClass().getCanonicalName() + "hostContainerViewByIdMap";

    public void saveBundle(@NonNull final Bundle outState) {
        log.logMethodName();
        outState.putSerializable(key, hostContainerViewByIdMap);
    }

    public void restoreBundle(@Nullable final Bundle savedInstanceState) {
        log.logMethodName();
        if (savedInstanceState != null) {
            hostContainerViewByIdMap =
                    (HashMap<Integer, Integer>) savedInstanceState.getSerializable(key);
        }
    }
}
