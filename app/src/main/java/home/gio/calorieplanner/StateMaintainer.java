package home.gio.calorieplanner;


import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class StateMaintainer {
    protected final String TAG = getClass().getSimpleName();
    private final String mStateMaintainerTag;
    private final WeakReference<FragmentManager> mFragmentManager;
    private StateMngFragment mStateMaintainerFrag;

    public StateMaintainer(FragmentManager FragmentManager, String mStateMaintainerTag) {
        this.mStateMaintainerTag = mStateMaintainerTag;
        this.mFragmentManager = new WeakReference<>(FragmentManager);
    }

    public boolean firstTimeIn() {
        try {
            mStateMaintainerFrag = (StateMngFragment) mFragmentManager.get().findFragmentByTag(mStateMaintainerTag);
            if (mStateMaintainerFrag == null) {
                Log.d(TAG, "Creating a new RetainedFragment" + mStateMaintainerTag);
                mStateMaintainerFrag = new StateMngFragment();
                mFragmentManager.get().beginTransaction().add(mStateMaintainerFrag, mStateMaintainerTag).commit();
                return true;
            } else {
                Log.d(TAG, "Returns a existent retained fragment " + mStateMaintainerTag);
                return false;
            }
        } catch (NullPointerException e) {

            Log.w(TAG, "error firstTimeIn()");
            return false;
        }
    }

    public void put(String key, Object obj) {
        mStateMaintainerFrag.put(key, obj);
    }

    public void put(Object obj) {
        put(obj.getClass().getName(), obj);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return mStateMaintainerFrag.get(key);
    }

    public boolean hasKey(String key) {
        return mStateMaintainerFrag.get(key) != null;
    }

    public static class StateMngFragment extends Fragment {
        private HashMap<String, Object> mData = new HashMap<>();

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        public void put(String key, Object obj) {
            mData.put(key, obj);
        }

        public void put(Object object) {
            put(object.getClass().getName(), object);
        }

        @SuppressWarnings("unchecked")
        public <T> T get(String key) {
            return (T) mData.get(key);
        }
    }
}
