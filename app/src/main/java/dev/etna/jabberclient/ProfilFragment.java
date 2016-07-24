package dev.etna.jabberclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import dev.etna.jabberclient.profil.Profil;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View myFragmentView;

    ImageView imgView_avatar;
    EditText editPseudo;
    EditText editPrenom;
    EditText editNom;
    EditText editDateNaiss;
    EditText editEmail;
    EditText editPhoneNumber;
    EditText editSiteWeb;
    EditText editBio;

    private OnFragmentInteractionListener mListener;

    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        myFragmentView = inflater.inflate(R.layout.activity_profil, container, false);

        imgView_avatar = (ImageView) myFragmentView.findViewById(R.id.imageView_avatarProfil);
        byte[] imgProfile = Profil.getInstance().getAvatar();
        Bitmap imgBitmap = BitmapFactory.decodeByteArray(imgProfile , 0, imgProfile.length);
        imgView_avatar.setImageBitmap(imgBitmap);

        editPseudo = (EditText) myFragmentView.findViewById(R.id.editText_pseudo);
        editPseudo.setText(Profil.getInstance().getPseudo());

        editPrenom = (EditText) myFragmentView.findViewById(R.id.editText_prenom);
        editPrenom.setText(Profil.getInstance().getFirstName());

        editNom = (EditText) myFragmentView.findViewById(R.id.editText_nom);
        editNom.setText(Profil.getInstance().getName());

        editDateNaiss = (EditText) myFragmentView.findViewById(R.id.editText_datenaiss);
        editDateNaiss.setText(Profil.getInstance().getBirthday());

        editEmail = (EditText) myFragmentView.findViewById(R.id.editText_email);
        editEmail.setText(Profil.getInstance().getEmail());

        editPhoneNumber = (EditText) myFragmentView.findViewById(R.id.editText_telephone);
        editPhoneNumber.setText(Profil.getInstance().getPhoneNumber());

        editSiteWeb = (EditText) myFragmentView.findViewById(R.id.editText_siteweb);
        editSiteWeb.setText(Profil.getInstance().getWebSite());

        editBio = (EditText) myFragmentView.findViewById(R.id.editText_bio);
        editBio.setText(Profil.getInstance().getBio());

        // Inflate the layout for this fragment
        return myFragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
}
