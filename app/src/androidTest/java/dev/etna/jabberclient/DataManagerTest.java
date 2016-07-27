package dev.etna.jabberclient;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.rule.ActivityTestRule;

import org.jivesoftware.smack.packet.Message;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import dev.etna.jabberclient.manager.DataManager;
import dev.etna.jabberclient.model.Contact;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Cedric Olivier on 27/07/2016.
 */
public class DataManagerTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(
            MainActivity.class,
            true,
            false);

    private Contact me;
    private Contact her;
    private final String[] MESS = {"Yo!", "Wesh bien ou quoi ?", "Vas y laisse moi tranquil!"};
    private final String login_1    = "gato@jabber.hot-chilli.eu";
    private final String login_2    = "sexyKaira@jabber.hot-chilli.eu";

    @Test
    public void testInsert() throws Exception {
        DataManager dm;
        Cursor cursor;
        String messText;
        int i;

        activityRule.launchActivity(null);
        dm = new DataManager(activityRule.getActivity());
        dm.open();
        dm.removeAllMessage();

        for (Message m : getMessageList())
            dm.saveMessage(m);

        cursor = dm.getMessageListByContact(her);
        i = 0;
        cursor.moveToFirst();
        do {
            messText = cursor.getString(3);
            assertTrue("Message should be the same ( '"+ messText + "' != '" + MESS[i] + "')", messText.equals(MESS[i++]));
        } while (cursor.moveToNext());
    }

    private void initContact() {
        me = new Contact();
        me.setLogin(login_1);
        her = new Contact();
        her.setLogin(login_2);
    }

    private ArrayList<Message> getMessageList() {
        ArrayList<Message> list = new ArrayList<Message>();
        Message message;

        initContact();
        message = new Message();
        message.setFrom(me.getLogin());
        message.setTo(her.getLogin());
        message.setBody(MESS[0]);
        list.add(message);

        message = new Message();
        message.setFrom(her.getLogin());
        message.setTo(me.getLogin());
        message.setBody(MESS[1]);
        list.add(message);

        message = new Message();
        message.setFrom(me.getLogin());
        message.setTo(her.getLogin());
        message.setBody(MESS[2]);
        list.add(message);
        return list;
    }
}
