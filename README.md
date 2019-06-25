# REST API CLASSES
### Utilisation de types de bases.
### Si TAB(appLike | empLike) vides, propositions randoms.

OFFER(string:type,string:img,string:libelle, int:nbLike,APPLICANT[]:appLike)

USER(int:id,string:nom,string:prenom,int:age)

	- EMPLOYER(OFFER[]:offer)
	
    - APPLICANT(string:img,string:libelle,int:nbLike,EMPLOYER[]:empLike)

## ID, DataBase, LiteSQL
# Ajout futur :

	+ Adresse : Rue, Ville, Code Postal => User
	+ Diplome[] : Name, School, Year => Applicant
	+ Experience[] : Domain, Compagny, Durée(Length en semaine) => Applicant
	+ Domain => Offer
	+ Birthdate => User
	+ Mail+Password => User

>GET Login+MDP

