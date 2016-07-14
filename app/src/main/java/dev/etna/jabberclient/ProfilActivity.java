package dev.etna.jabberclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import dev.etna.jabberclient.profil.Profil;

public class ProfilActivity extends AppCompatActivity {

    EditText editPseudo;
    EditText editPrenom;
    EditText editNom;
    EditText editDateNaiss;
    EditText editEmail;
    EditText editPhoneNumber;
    EditText editSiteWeb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        editPseudo = (EditText) findViewById(R.id.editText_pseudo);
        editPseudo.setText(Profil.getInstance().getPseudo());

        editPrenom = (EditText) findViewById(R.id.editText_prenom);
        editPrenom.setText(Profil.getInstance().getFirstName());

        editNom = (EditText) findViewById(R.id.editText_nom);
        editNom.setText(Profil.getInstance().getName());

        editDateNaiss = (EditText) findViewById(R.id.editText_datenaiss);
        editDateNaiss.setText(Profil.getInstance().getBirthday());

        editEmail = (EditText) findViewById(R.id.editText_email);
        editEmail.setText(Profil.getInstance().getEmail());

        editPhoneNumber = (EditText) findViewById(R.id.editText_telephone);
        editPhoneNumber.setText(Profil.getInstance().getPhoneNumber());

        editSiteWeb = (EditText) findViewById(R.id.editText_siteweb);
        editSiteWeb.setText(Profil.getInstance().getWebSite());
    }
}
