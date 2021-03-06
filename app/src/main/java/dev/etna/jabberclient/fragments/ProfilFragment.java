package dev.etna.jabberclient.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import dev.etna.jabberclient.R;
import dev.etna.jabberclient.model.Profil;
import dev.etna.jabberclient.xmpp.XMPPService;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment implements View.OnClickListener
{

    private View myFragmentView;

    private ImageView imgView_avatar;
    private EditText editPseudo;
    private EditText editPrenom;
    private EditText editNom;
    private EditText editDateNaiss;
    private EditText editEmail;
    private EditText editPhoneNumber;
    private EditText editSiteWeb;
    private EditText editBio;
    private Button buttonSave;
    private Button buttonUnlock; /** unlock editable text for update profil */

    private OnFragmentInteractionListener mListener;

    public ProfilFragment() {
        // Required empty public constructor
    }

    public static ProfilFragment newInstance() {
        ProfilFragment fragment = new ProfilFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("### ","onCreateView 1");
        myFragmentView = inflater.inflate(R.layout.fragment_profil, container, false);

        Profil prof = XMPPService.getInstance().getMyProfil();

            imgView_avatar = (ImageView) myFragmentView.findViewById(R.id.imageView_avatarProfil);
            byte[] imgProfile = prof.getAvatar();
            if (imgProfile!=null) {
                Bitmap imgBitmap = BitmapFactory.decodeByteArray(imgProfile, 0, imgProfile.length);
                imgView_avatar.setImageBitmap(imgBitmap);
            }
            editPseudo = (EditText) myFragmentView.findViewById(R.id.editText_pseudo);
            editPseudo.setText(prof.getPseudo());

            editPrenom = (EditText) myFragmentView.findViewById(R.id.editText_prenom);
            editPrenom.setText(prof.getFirstName());

            editNom = (EditText) myFragmentView.findViewById(R.id.editText_nom);
            editNom.setText(prof.getName());

            editDateNaiss = (EditText) myFragmentView.findViewById(R.id.editText_datenaiss);
            editDateNaiss.setText(prof.getBirthday());

            editEmail = (EditText) myFragmentView.findViewById(R.id.editText_email);
            editEmail.setText(prof.getEmail());

            editPhoneNumber = (EditText) myFragmentView.findViewById(R.id.editText_telephone);
            editPhoneNumber.setText(prof.getPhoneNumber());

            editSiteWeb = (EditText) myFragmentView.findViewById(R.id.editText_siteweb);
            editSiteWeb.setText(prof.getWebSite());

            editBio = (EditText) myFragmentView.findViewById(R.id.editText_bio);
            editBio.setText(prof.getBio());

        /** LOCK THE EDITABLE INPUT */
        doLock();

        buttonSave = (Button) myFragmentView.findViewById(R.id.button_save);
        buttonSave.setVisibility(View.GONE);
        buttonSave.setOnClickListener(this);

        buttonUnlock = (Button) myFragmentView.findViewById(R.id.button_unlock);
        buttonUnlock.setOnClickListener(this);

        // Inflate the layout for this fragment
        return myFragmentView;
    }

    private void doLock(){
        editPseudo.setEnabled(false);
        editPrenom.setEnabled(false);
        editNom.setEnabled(false);
        editDateNaiss.setEnabled(false);
        editEmail.setEnabled(false);
        editPhoneNumber.setEnabled(false);
        editSiteWeb.setEnabled(false);
        editBio.setEnabled(false);
    }

    private void unLock(){
        editPseudo.setEnabled(true);
        editPrenom.setEnabled(true);
        editNom.setEnabled(true);
        editDateNaiss.setEnabled(true);
        editEmail.setEnabled(true);
        editPhoneNumber.setEnabled(true);
        editSiteWeb.setEnabled(true);
        editBio.setEnabled(true);
        buttonSave.setVisibility(View.VISIBLE);
    }

    public void saveDataToProfil(){
        XMPPService.getInstance().getMyProfil().setDataProfil(this);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.button_unlock:{
                unLock();
                buttonUnlock.setVisibility(View.INVISIBLE);

                break;
            }
            case R.id.button_save:{
                saveDataToProfil();

                /** refresh fragment */
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();

                buttonUnlock.setVisibility(View.VISIBLE);

                Toast.makeText(getContext(), "Profil updated successfully", Toast.LENGTH_SHORT).show();

            break;
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    ////////////////////////////////////////////////////////////
    // GETTER & SETTER
    ////////////////////////////////////////////////////////////

    public EditText getEditPseudo() {
        return editPseudo;
    }

    public ImageView getImgView_avatar() {
        return imgView_avatar;
    }

    public View getMyFragmentView() {
        return myFragmentView;
    }

    public EditText getEditPrenom() {
        return editPrenom;
    }

    public EditText getEditNom() {
        return editNom;
    }

    public EditText getEditDateNaiss() {
        return editDateNaiss;
    }

    public EditText getEditEmail() {
        return editEmail;
    }

    public EditText getEditPhoneNumber() {
        return editPhoneNumber;
    }

    public EditText getEditSiteWeb() {
        return editSiteWeb;
    }

    public EditText getEditBio() {
        return editBio;
    }

    public Button getButtonSave() {
        return buttonSave;
    }
}
