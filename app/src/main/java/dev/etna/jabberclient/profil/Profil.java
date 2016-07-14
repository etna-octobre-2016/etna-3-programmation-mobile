package dev.etna.jabberclient.profil;

import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jivesoftware.smackx.vcardtemp.provider.VCardProvider;

import java.util.Date;

import dev.etna.jabberclient.xmpp.XMPPService;

/**
 * Created by pakpak on 08/07/2016.
 */
public class Profil {

    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private byte[] avatar;

    private String fullName;
    private String pseudo;
    private String firstName;
    private String name;
    private String birthday;

    private String email;
    private String phoneNumber;
    private String webSite;

    private String street;
    private String city;
    private String postCode;
    private String Country;

    private String bio;

    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public Profil(XMPPService service){

        VCard vCard = service.getVcard();
        ProviderManager.addIQProvider("vCard", "vcard-temp",new VCardProvider());


        setFirstName(vCard.getFirstName());
        setName(vCard.getLastName());
        setPseudo(vCard.getNickName());
        setEmail(vCard.getEmailHome());
        setAvatar(vCard.getAvatar());
        vCard.getPhoneHome("");
        vCard.getJabberId();
        vCard.getOrganization();

        if (INSTANCE == null)
        {
            INSTANCE = this;
        }
    }

    ////////////////////////////////////////////////////////////
    // METHODS
    ////////////////////////////////////////////////////////////

    private static Profil INSTANCE = null;

    /** Point d'accès pour l'instance unique du singleton */
    public static Profil getInstance()
    {
        return INSTANCE;
    }

    ////////////////////////////////////////////////////////////
    // GETTER & SETTER
    ////////////////////////////////////////////////////////////

    public byte[] getAvatar() {
        return avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getWebSite() {
        return webSite;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCountry() {
        return Country;
    }

    public String getBio() {
        return bio;
    }


    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }


    @Override
    public String toString() {
        return "Profil{"+
                ", fullName='" + fullName + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", firstName='" + firstName + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", webSite='" + webSite + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postCode='" + postCode + '\'' +
                ", Country='" + Country + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
