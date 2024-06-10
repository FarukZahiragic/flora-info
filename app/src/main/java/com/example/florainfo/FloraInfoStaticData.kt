package com.example.florainfo

var biljke = listOf(
    Biljka(
        naziv = "Bosiljak (Ocimum basilicum)",
        porodica = "Lamiaceae (usnate)",
        medicinskoUpozorenje = "Može iritati kožu osjetljivu na sunce. Preporučuje se oprezna upotreba pri korištenju ulja bosiljka.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE),
        profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
        jela = listOf("Salata od paradajza", "Punjene tikvice"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA)
    ),
    Biljka(
        naziv = "Nana (Mentha spicata)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PROTIVBOLOVA),
        profilOkusa = ProfilOkusaBiljke.MENTA,
        jela = listOf("Jogurt sa voćem", "Gulaš"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
        zemljisniTipovi = listOf(Zemljiste.GLINENO, Zemljiste.CRNICA)
    ),
    Biljka(
        naziv = "Kamilica (Matricaria chamomilla)",
        porodica = "Asteraceae (glavočike)",
        medicinskoUpozorenje = "Može uzrokovati alergijske reakcije kod osjetljivih osoba.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Čaj od kamilice"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)
    ),
    Biljka(
        naziv = "Ružmarin (Rosmarinus officinalis)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Treba ga koristiti umjereno i konsultovati se sa ljekarom pri dugotrajnoj upotrebi ili upotrebi u većim količinama.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.REGULACIJAPRITISKA),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Pečeno pile", "Grah","Gulaš"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
        zemljisniTipovi = listOf(Zemljiste.SLJUNKOVITO, Zemljiste.KRECNJACKO)
    ),
    Biljka(
        naziv = "Lavanda (Lavandula angustifolia)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine. Također, treba izbjegavati kontakt lavanda ulja sa očima.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PODRSKAIMUNITETU),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Jogurt sa voćem"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)
    ),
    Biljka(
        naziv = "Kadulja (Salvia officinalis)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Ne preporučuje se osobama sklonima epileptičnim napadima i  osobama s visokim tlakom.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO),
        profilOkusa = ProfilOkusaBiljke.MENTA,
        jela = listOf("Pečeni krompir", "Njoke"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUHA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.CRNICA)
    ),
    Biljka(
        naziv = "Bijeli sljez (Althaea officinalis  )",
        porodica = "Malvaceae (slezovke)",
        medicinskoUpozorenje = "Ne preporučuje se trudnicama i dojiljama.",
        medicinskeKoristi = listOf(MedicinskaKorist.PODRSKAIMUNITETU, MedicinskaKorist.PROTUUPALNO),
        profilOkusa = ProfilOkusaBiljke.SLATKI,
        jela = listOf("Marshmallow"),
        klimatskiTipovi = listOf(KlimatskiTip.PLANINSKA, KlimatskiTip.SREDOZEMNA),
        zemljisniTipovi = listOf(Zemljiste.ILOVACA, Zemljiste.GLINENO)
    ),
    Biljka(
        naziv = "Kantarion (Hypericum perforatum)",
        porodica = "Hypericaceae (kantarionovke)",
        medicinskoUpozorenje = "Može uzrokovati povećanu osjetljivost na sunčevu svjetlost",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO),
        profilOkusa = ProfilOkusaBiljke.GORKO,
        jela = listOf("Čaj od kantariona"),
        klimatskiTipovi = listOf(KlimatskiTip.PLANINSKA, KlimatskiTip.UMJERENA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.GLINENO)
    ),
    Biljka(
        naziv = "Neven (Calendula officinalis)",
        porodica = "Asteraceae (glavočike)",
        medicinskoUpozorenje = "Konzumiranje sirovog nevena može uzrokovati iritaciju stomaka.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.REGULACIJAPROBAVE),
        profilOkusa = ProfilOkusaBiljke.CITRUSNI,
        jela = listOf("Čaj od nevena"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
        zemljisniTipovi = listOf(Zemljiste.CRNICA, Zemljiste.PJESKOVITO)
    ),
    Biljka(
        naziv = "Lovor (Laurus nobilis)",
        porodica = "Lauraceae (lovorovke)",
        medicinskoUpozorenje = "Može uticati na nivo šećera u krvi.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE, MedicinskaKorist.REGULACIJAPRITISKA),
        profilOkusa = ProfilOkusaBiljke.GORKO,
        jela = listOf("Čaj od lovorovog lista"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = listOf(Zemljiste.CRNICA, Zemljiste.PJESKOVITO)
    )
)
