# Tiekantojan perusteet (Toinen harjoitustyö)

## Kysymyspankki

Työn aiheena on kysymyspankki, jossa on kysymyksiä ja niihin liittyviä vastauksia.





Sovellus tarjoamat toiminnallisuudet ovat:
1. Kysymysten lisäämiseen palveluun.
2. Vastausten lisäämiseen palveluun.
3. Kysymysten listaus.
4. Kysymysten katsoaminen. Kysymystä näyttävästä sivusta näkee siihen liittyvät vastaukset.

### Luokkakaavio
![Luokkakaavio](https://yuml.me/diagram/classy/class/[Kysymys|(pk) id:Integer; kurssi:String; aihe:String; kysymys_teksti:String;]1-*[Vastaus|(pk) id:Integer; (fk) kysymys_id:Integer; vastaus_teksti:String; oikein:Boolean;])

## Projektin linkit
[Github repositio](https://github.com/Jasminmo/tikape-runko)
[Heroku webpalvelu](http://limitless-chamber-82225.herokuapp.com)
