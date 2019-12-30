package embeddedActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EmbeddedActivityClient extends Fragment {

    // ------------ INTERNAL ------------

    // used for internal logging

    private final LogUtils log =
            new LogUtils(
            "EmbeddedActivityClientFragment",
            "a bug has occurred, this should not happen"
            );

    FrameLayout root;

    // since onCreate is called after onAttach and BEFORE onCreateView, the root view must exist
    // before onCreate is called since it is the entry point of an Activity

    @Override
    public void onAttach(@NonNull final Context context) {
        super.onAttach(context);
        log.logMethodName();
        log.log("root is " + root);
        root = new FrameLayout(getContext());
        log.log("root is " + root);
    }

    @Nullable
    @Override
    public final View onCreateView(
            @NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState
    ) {
        super.onCreateView(inflater, container, savedInstanceState);
        log.logMethodName();
        // root should not be null here
        log.log("root is " + root);
        log.errorAndThrowIfNull(root, "error: root was null, this should never happen");
        return root;
    }

    // ------------ ACTIVITY COMPATIBILITY ------------

    public void setContentView(@NonNull @LayoutRes int layoutResID) {
        log.logMethodName();
        setContentView(getLayoutInflater().inflate(layoutResID, null, false));
    }

    public void setContentView(View newRoot) {
        log.logMethodName();
        log.log("root is " + root);
        if (root.getChildCount() != 0) {
            log.log("old root is " + root.getChildAt(0));
            root.removeViewAt(0);
        }
        log.log("new root is " + newRoot);
        if (newRoot != null) root.addView(newRoot, 0);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log.logMethodName();
    }

    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        log.logMethodName();
        onCreate(savedInstanceState);
    }

    public View findViewById(int id) {
        log.logMethodName();
        log.log("root is " + root);
        return root.findViewById(id);
    }
}