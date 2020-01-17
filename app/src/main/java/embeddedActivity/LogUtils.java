package embeddedActivity;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LogUtils {
    private String TAG = "";
    private String ERRORMESSAGE = "An error has occured";

    public LogUtils(String tag) {
        TAG = tag;
    }

    public LogUtils(String tag, String errorMessage) {
        TAG = tag;
        ERRORMESSAGE = errorMessage;
    }

    /**
     * Fails a test with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @see AssertionError
     */
    static public void fail(String message) {
        if (message == null) {
            throw new AssertionError();
        }
        throw new AssertionError(message);
    }

    /**
     * Asserts that a condition is true. If it isn't it throws an
     * {@link AssertionError} with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param condition condition to be checked
     */
    static public void assertTrue(String message, boolean condition) {
        if (!condition) {
            fail(message);
        }
    }

    /**
     * Asserts that an object isn't null. If it is an {@link AssertionError} is
     * thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param object Object to check or <code>null</code>
     */
    static public void assertNotNull(String message, Object object) {
        assertTrue(message, object != null);
    }


    public final void log(String message) {
        Log.d(TAG, message);
    }

    public final Throwable error() {
        return error(ERRORMESSAGE);
    }

    public final AssertionError error(String message) {
        AssertionError t = new AssertionError(message);
        Log.e(TAG, Log.getStackTraceString(t));
        return t;
    }

    public final void errorNoStackTrace() {
        errorNoStackTrace(ERRORMESSAGE);
    }

    public final void errorNoStackTrace(String message) {
        Log.e(TAG, message);
    }

    @Nullable
    @SuppressWarnings("ConstantOnRightSideOfComparison")
    public final <T> T errorIfNull(@Nullable T object) {
        return errorIfNull(object, ERRORMESSAGE);
    }

    @Nullable
    @SuppressWarnings("ConstantOnRightSideOfComparison")
    public final <T> T errorIfNull(@Nullable T object, String message) {
        if (object == null) error(message);
        return object;
    }

    @Nullable
    @SuppressWarnings("ConstantOnRightSideOfComparison")
    public final <T> T errorIfNullNoStackTrace(@Nullable T object) {
        return errorIfNullNoStackTrace(object, ERRORMESSAGE);
    }

    @Nullable
    @SuppressWarnings("ConstantOnRightSideOfComparison")
    public final <T> T errorIfNullNoStackTrace(@Nullable T object, String message) {
        if (object == null) errorNoStackTrace(message);
        return object;
    }

    @NonNull
    @SuppressWarnings("ConstantOnRightSideOfComparison")
    public final <T> T errorAndThrowIfNull(@Nullable T object) {
        return errorAndThrowIfNull(object, ERRORMESSAGE);
    }

    @NonNull
    @SuppressWarnings("ConstantOnRightSideOfComparison")
    public final <T> T errorAndThrowIfNull(@Nullable T object, String message) {
        assertNotNull(message, object);
        return object;
    }

    @SuppressWarnings("ConstantOnRightSideOfComparison")
    public final void errorAndThrow(String message) {
        assertNotNull(message, null);
    }

    public void logMethodName() {
        Log.d(TAG, Thread.currentThread().getStackTrace()[3].getMethodName() + "() called");
    }

    public String getMethodName() {
        return getMethodName(1);
    }

    public String getMethodName(int methodDepthOffset) {
        return Thread.currentThread().getStackTrace()[3+methodDepthOffset].getMethodName();
    }

    public String getParentMethodName() {
        return getParentMethodName(1);
    }

    public String getParentMethodName(int methodDepthOffset) {
        return Thread.currentThread().getStackTrace()[4+methodDepthOffset].getMethodName();
    }
}
