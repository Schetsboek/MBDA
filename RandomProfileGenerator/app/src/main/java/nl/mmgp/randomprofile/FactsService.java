package nl.mmgp.randomprofile;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class FactsService extends Service {

    private long repeatMillisInterval;
    private long nextRepeatMillis;

    private final long DELAY = 250;

    private FactsObserver factsObserver;
    private Random random;

    private ArrayList<String> facts;

    private AtomicBoolean working = new AtomicBoolean(true);

    private final Runnable runnable = () -> {
        while(working.get()) {
            long now = System.currentTimeMillis();
            if(nextRepeatMillis <= now && facts.size() > 0){
                // Get random fact
                int randomID = random.nextInt(facts.size());
                this.notifyFactsObserver(randomID);
                // Update next loop
                setNextRepeatMillis(now);
            }
            try {
                sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };


    private void setNextRepeatMillis(long newRepeatMillis) {
        this.nextRepeatMillis = newRepeatMillis + repeatMillisInterval;
    }

    public void setObserver(FactsObserver factsObserver){
        this.factsObserver = factsObserver;
    }

    private void notifyFactsObserver(int randomID) {
        if(factsObserver != null){
            factsObserver.getFact(facts.get(randomID));
        }
    }

    private void setupFacts(){
        facts = new ArrayList<>();
        facts.add(getResources().getString(R.string.rocket_propelled_grenade));
        facts.add(getResources().getString(R.string.role_playing_game));
        facts.add(getResources().getString(R.string.random_profile_generator));
        facts.add(getResources().getString(R.string.bananas));
        facts.add(getResources().getString(R.string.hippos));
        facts.add(getResources().getString(R.string.computer));
        facts.add(getResources().getString(R.string.spice));
        facts.add(getResources().getString(R.string.rene_magritte));
        facts.add(getResources().getString(R.string.pizza_math));
    }

    @Override
    public void onCreate() {
        repeatMillisInterval = 10000;
        random = new Random();
        setupFacts();
        new Thread(runnable).start();
    }

    @Override
    public void onDestroy() {
        working.set(false);
    }

    public class LocalBinder extends Binder {
        public FactsService getService() {
            return FactsService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IBinder mBinder = new LocalBinder();
}