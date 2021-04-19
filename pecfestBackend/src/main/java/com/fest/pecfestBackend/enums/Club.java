package com.fest.pecfestBackend.enums;

public enum Club {
	Treasure_Hunt("Treasure Hunt"),
	CAD_Competitions("CAD Competitions"),
	Gaming("Gaming"),
	Electronics_and_Circuits("Electronics and Circuits"),
	Webinars("Webinars"),
	Miscellaneous("Miscellaneous"),
	Coding("Coding"),
	Quiz("Quiz"),
    Case_Study_Competition("Case Study Competition"),
    RHYTHM_AND_SHOES("RHYTHM & SHOES"),
    GAMEOTHONS("GAME'O'THONS"),
    BRAIN_BOOSTERS("BRAIN BOOSTERS"),
    VERBAL_WARS("VERBAL WARS"),
    ALL_THE_WORLD_S_A_STAGE("ALL THE WORLD'S A STAGE"),
    ARTIST_S_PARADISE("ARTIST'S PARADISE"),
    SCC("Student Counselling Cell"),
    EIC("Entrepreneurship and Incubation Cell"),
    Rotaract_Club("Rotaract Club"),
    CIM("Communication,Information and Media Cell"),
    PDC("Projection & Design Club"),
    Music_Club("Music Club"),
    EEB("English Editorial Board"),
    HEB("Hindi Editorial Board"),
    PEB("Punjabi Editorial Board"),
    SAASC("Speakers Association and Study Circle"),
    Dramatics("Dramatics"),
    APC("Art & Photography Club"),
    NSS("NSS"),
    NCC("NCC"),
    WEC("Women Empowerment Cell"),
    IIM("Indian Institute of Metals"),
    IGS("IGS"),
    SESI("Solar Energy Society of India"),
    ROBOTICS("Robotics Society"),
    SAE("Society of Automotive Engineers"),
    IEEE("Institute of Electronics and Electrical Engineers"),
    SME("Society of Manufacturing Engineers"),
    ASPS("Astronomy and Space Physics Society"),
    ASCE("American Society of Civil Engineers"),
    ASME("American Society of Mechanical Engineers"),
    ATS("Aerospace Technical Society"),
    ACM_CSS("Computer Science Society"),
    IETE("Institution of Electronics and Telecommunication Engineers"),
    SPORTS("SPORTS"),
    ALL("ALL"),
    EMPTY("EMPTY");
    private final String clubName;

    Club(String name) {
        this.clubName = name;
    }
    public String getClubName() {
        return this.clubName;
    }
    @Override
    public String toString() {
        return this.clubName;
    }
    public static Club fromString(String clubName){
        for(Club club:Club.values())
            if(club.clubName.equalsIgnoreCase(clubName))
                return club;
            return null;
    }
}
